"use client"

import { useState } from "react"
import { useForm } from "@tanstack/react-form"
import { useRouter } from "next/navigation"
import { Mail, ArrowLeft } from "lucide-react"
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
  InputGroupInput,
} from "@/components/ui/input-group"
import { Spinner } from "@/components/ui/spinner"

const forgotPasswordSchema = z.object({
  email: z.string().email("Please enter a valid email"),
})

export function ForgotPasswordForm() {
  const router = useRouter()

  const form = useForm({
    defaultValues: {
      email: "",
    },
    validators: {
      onChange: forgotPasswordSchema,
    },
    onSubmit: async ({ value }) => {
      // Store email for later use
      sessionStorage.setItem("resetEmail", value.email)
      // Simulate API call to send reset code
      await new Promise((resolve) => setTimeout(resolve, 1500))
      // Redirect to OTP verification page
      router.push("/reset-password-verify")
    },
  })

  return (
    <div className="bg-muted/50 flex min-h-screen w-full flex-col items-center justify-center p-4">
      {/* Logo */}
      <div className="mb-8">
        <a href="/" className="flex items-center gap-2">
          <img src="/logo2.png" alt="Audit ElectroPlanet" className="size-25" />
        </a>
      </div>
      
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl">Mot de passe oublié ?</CardTitle>
          <CardDescription>
            Entrez votre adresse email pour recevoir un code de vérification
          </CardDescription>
        </CardHeader>
        <CardContent>
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

            <form.Subscribe selector={(s) => [s.canSubmit, s.isSubmitting]}>
              {([canSubmit, isSubmitting]) => (
                <>
                  <Button
                    type="submit"
                    className="mt-6 w-full"
                    disabled={!canSubmit || isSubmitting}>
                    {isSubmitting ? (
                      <>
                        <Spinner />
                        Envoi en cours...
                      </>
                    ) : (
                      "Envoyer le code"
                    )}
                  </Button>
                  
                  <Button
                    type="button"
                    variant="outline"
                    className="mt-3 w-full"
                    onClick={() => router.push("/login")}>
                    <ArrowLeft className="mr-2 h-4 w-4" />
                    Retour à la connexion
                  </Button>
                </>
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