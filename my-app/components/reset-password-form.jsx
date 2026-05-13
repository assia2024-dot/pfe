"use client"

import { useState, useEffect } from "react"
import { useForm } from "@tanstack/react-form"
import { useRouter } from "next/navigation"
import { Lock, Eye, EyeOff, CheckCircle } from "lucide-react"
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

const resetPasswordSchema = z.object({
  password: z.string().min(8, "Le mot de passe doit contenir au moins 8 caractères"),
  confirmPassword: z.string().min(8, "Le mot de passe doit contenir au moins 8 caractères"),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Les mots de passe ne correspondent pas",
  path: ["confirmPassword"],
})

export function ResetPasswordForm() {
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const [isResetting, setIsResetting] = useState(false)
  const router = useRouter()

  useEffect(() => {
    // Check if OTP was verified
    const otpVerified = sessionStorage.getItem("otpVerified")
    const resetEmail = sessionStorage.getItem("resetEmail")
    
    if (!otpVerified || !resetEmail) {
      router.push("/forgot-password")
    }
  }, [router])

  const form = useForm({
    defaultValues: {
      password: "",
      confirmPassword: "",
    },
    validators: {
      onChange: resetPasswordSchema,
    },
    onSubmit: async ({ value }) => {
      setIsResetting(true)
      // Simulate password reset API call
      await new Promise((resolve) => setTimeout(resolve, 1500))
      
      // Clear session storage
      sessionStorage.removeItem("resetEmail")
      sessionStorage.removeItem("otpVerified")
      
      // Redirect to login page
      router.push("/login?reset=success")
      setIsResetting(false)
    },
  })

  return (
    <div className="bg-muted/50 flex min-h-screen w-full flex-col items-center justify-center p-4">
      <div className="mb-8">
        <a href="/" className="flex items-center gap-2">
          <img src="/logo2.png" alt="Audit ElectroPlanet" className="size-25" />
        </a>
      </div>
      
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl">Nouveau mot de passe</CardTitle>
          <CardDescription>
            Veuillez choisir un nouveau mot de passe sécurisé
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Form
            onSubmit={(e) => {
              e.preventDefault()
              form.handleSubmit()
            }}>
            <form.Field name="password">
              {(field) => (
                <FormField
                  name={field.name}
                  errors={field.state.meta.errors}
                  isTouched={field.state.meta.isTouched}>
                  <FormLabel>Nouveau mot de passe</FormLabel>
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

            <form.Field name="confirmPassword">
              {(field) => (
                <FormField
                  name={field.name}
                  errors={field.state.meta.errors}
                  isTouched={field.state.meta.isTouched}>
                  <FormLabel>Confirmer le mot de passe</FormLabel>
                  <FormControl>
                    <InputGroup>
                      <InputGroupAddon>
                        <Lock className="size-4" />
                      </InputGroupAddon>
                      <InputGroupInput
                        type={showConfirmPassword ? "text" : "password"}
                        placeholder="••••••••"
                        value={field.state.value}
                        onBlur={field.handleBlur}
                        onChange={(e) => field.handleChange(e.target.value)} />
                      <InputGroupAddon align="inline-end">
                        <InputGroupButton
                          size="icon-xs"
                          onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                          aria-label={
                            showConfirmPassword ? "Hide password" : "Show password"
                          }>
                          {showConfirmPassword ? (
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
              {([canSubmit]) => (
                <Button
                  type="submit"
                  className="mt-6 w-full"
                  disabled={!canSubmit || isResetting}>
                  {isResetting ? (
                    <>
                      <Spinner />
                      Réinitialisation...
                    </>
                  ) : (
                    "Réinitialiser le mot de passe"
                  )}
                </Button>
              )}
            </form.Subscribe>
          </Form>
        </CardContent>
      </Card>
      
      <p className="text-muted-foreground mt-8 text-center text-xs">
        © 2025 Audit ElectroPlanet. Tous droits réservés.
      </p>
    </div>
  )
}