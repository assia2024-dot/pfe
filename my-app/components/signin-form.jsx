"use client"

import { useState, useEffect } from "react"
import { useForm } from "@tanstack/react-form"
import { useRouter, useSearchParams } from "next/navigation"
import { Eye, EyeOff, Lock, Mail, ShieldCheck } from "lucide-react"
import { z } from "zod"

import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  Form,
  FormControl,
  FormField,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import {
  InputGroup,
  InputGroupAddon,
  InputGroupButton,
  InputGroupInput,
} from "@/components/ui/input-group"
import { Spinner } from "@/components/ui/spinner"

const signinSchema = z.object({
  email: z.string().email("Please enter a valid email"),
  password: z.string().min(8, "Password must be at least 8 characters"),
})

export function SigninForm() {
  const [showPassword, setShowPassword] = useState(false)
  const [resetSuccess, setResetSuccess] = useState(false)
  const router = useRouter()
  const searchParams = useSearchParams()

  useEffect(() => {
    if (searchParams.get("reset") === "success") {
      setResetSuccess(true)
      setTimeout(() => setResetSuccess(false), 5000)
    }
  }, [searchParams])

  const form = useForm({
    defaultValues: {
      email: "",
      password: "",
    },
    validators: {
      onChange: signinSchema,
    },
    onSubmit: async ({ value }) => {
      // Store email in sessionStorage to use in 2FA page
      sessionStorage.setItem("userEmail", value.email)
      // Redirect to 2FA verification page
      router.push("/verify")
    },
  })

  return (
    <div
      className="bg-muted/50 flex min-h-screen w-full flex-col items-center justify-center p-4">
      {/* Logo */}
      <div className="mb-8">
        <a href="/" className="flex items-center gap-2">
          <img src="/logo2.png" alt="Audit ElectroPlanet" className="size-25" />
        </a>
      </div>
      {/* Card */}
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl">Bonjour,</CardTitle>
          <CardDescription>
            Veuillez saisir vos identifiants pour accéder à votre espace
          </CardDescription>
        </CardHeader>
        <CardContent>
          {/* Success Message */}
          {resetSuccess && (
            <div className="mb-4 rounded-lg bg-green-50 p-4 text-sm text-green-800 border border-green-200 flex items-center gap-2">
              <ShieldCheck className="h-6 w-6 flex-shrink-0" />
              <span>Votre mot de passe a été réinitialisé avec succès. Veuillez vous connecter avec votre nouveau mot de passe.</span>
            </div>
          )}

          <Form
            onSubmit={(e) => {
              e.preventDefault()
              form.handleSubmit()
            }}>
            <form.Field name="email">
              {(field) => (
                <FormField
                  name={field.name}
                  errors={field.state.meta.errors}
                  isTouched={field.state.meta.isTouched}>
                  <FormLabel>Adresse e-mail</FormLabel>
                  <FormControl>
                    <InputGroup>
                      <InputGroupAddon>
                        <Mail className="size-4" />
                      </InputGroupAddon>
                      <InputGroupInput
                        type="email"
                        placeholder="you@example.com"
                        value={field.state.value}
                        onBlur={field.handleBlur}
                        onChange={(e) => field.handleChange(e.target.value)} />
                    </InputGroup>
                  </FormControl>
                  <FormMessage />
                </FormField>
              )}
            </form.Field>

            <form.Field name="password">
              {(field) => (
                <FormField
                  name={field.name}
                  errors={field.state.meta.errors}
                  isTouched={field.state.meta.isTouched}>
                  <div className="flex items-center justify-between">
                    <FormLabel>Mot de passe</FormLabel>
                    <a
                      href="/forgot-password"
                      className="text-muted-foreground text-sm underline-offset-4 hover:underline">
                      Mot de passe oublié ?
                    </a>
                  </div>
                  <FormControl>
                    <InputGroup>
                      <InputGroupAddon>
                        <Lock className="size-4" />
                      </InputGroupAddon>
                      <InputGroupInput
                        type={showPassword ? "text" : "password"}
                        placeholder="••••••••"
                        value={field.state.value}
                        onBlur={field.handleBlur}
                        onChange={(e) => field.handleChange(e.target.value)} />
                      <InputGroupAddon align="inline-end">
                        <InputGroupButton
                          size="icon-xs"
                          onClick={() => setShowPassword(!showPassword)}
                          aria-label={
                            showPassword ? "Hide password" : "Show password"
                          }>
                          {showPassword ? (
                            <EyeOff className="size-4" />
                          ) : (
                            <Eye className="size-4" />
                          )}
                        </InputGroupButton>
                      </InputGroupAddon>
                    </InputGroup>
                  </FormControl>
                  <FormMessage />
                </FormField>
              )}
            </form.Field>

            <form.Subscribe selector={(s) => [s.canSubmit, s.isSubmitting]}>
              {([canSubmit, isSubmitting]) => (
                <Button
                  type="submit"
                  className="mt-6 w-full"
                  disabled={!canSubmit || isSubmitting}>
                  {isSubmitting ? (
                    <>
                      <Spinner />
                      Connexion...
                    </>
                  ) : (
                    "Connexion"
                  )}
                </Button>
              )}
            </form.Subscribe>


          </Form>
        </CardContent>
      </Card>
      {/* Footer */}
      <p className="text-muted-foreground mt-8 text-center text-xs">
        © 2025 Audit ElectroPlanet. Tous droits réservés.
      </p>
    </div>
  );
}