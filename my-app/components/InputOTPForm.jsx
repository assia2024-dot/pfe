"use client"
import { authService } from "@/services/authService"
import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Link from "next/link"
import { RefreshCwIcon, HelpCircle } from "lucide-react"

import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  Field,
  FieldDescription,
  FieldLabel,
} from "@/components/ui/field"
import {
  InputOTP,
  InputOTPGroup,
  InputOTPSeparator,
  InputOTPSlot,
} from "@/components/ui/input-otp"
import { Spinner } from "@/components/ui/spinner"

export function InputOTPForm() {
  const [otp, setOtp] = useState("")
  const [isVerifying, setIsVerifying] = useState(false)
  const [email, setEmail] = useState("")
  const router = useRouter()
  const [error, setError] = useState("")
  const [resendSuccess, setResendSuccess] = useState(false)
  const [resendCooldown, setResendCooldown] = useState(0)


  useEffect(() => {
    const userEmail = sessionStorage.getItem("userEmail")
    if (userEmail) {
      setEmail(userEmail)
    } else {
      router.push("/login")
    }
  }, [router])

  const handleVerify = async () => {
    if (otp.length !== 6) return

    setIsVerifying(true)
    try {
      const response = await authService.verify2fa(email, otp)
      const data = response.data

      // Store token
      localStorage.setItem("token", data.token)

      // Store user info
      localStorage.setItem("user", JSON.stringify({
        userId: data.userId,
        userCode: data.userCode,
        nom: data.nom,
        prenom: data.prenom,
        email: data.email,
        role: data.role,
      }))

      sessionStorage.removeItem("userEmail")
      router.push("/")
    } catch (error) {
      const message = error.response?.data?.message || "Code incorrect ou expiré"
      setError(message)
    } finally {
      setIsVerifying(false)
    }
  }



  const handleResendCode = async () => {
    try {
      await authService.resendLoginCode(email)
      setError("")
      setResendSuccess(true)
      setResendCooldown(60)
      setTimeout(() => setResendSuccess(false), 4000)

      // Start countdown
      const interval = setInterval(() => {
        setResendCooldown((prev) => {
          if (prev <= 1) {
            clearInterval(interval)
            return 0
          }
          return prev - 1
        })
      }, 1000)
    } catch (error) {
      setError("Veuillez patienter 60 secondes avant de demander un nouveau code.")
    }
  }

  const handleContactSupport = () => {
    window.location.href = "mailto:electroplanetaudit@gmail.com"
  }



  return (
    <div className="bg-muted/50 flex min-h-screen w-full flex-col items-center justify-center p-4">
      <div className="mb-8">
        <a href="/" className="flex items-center gap-2">
          <img src="/logo2.png" alt="Audit ElectroPlanet" className="size-25" />
        </a>
      </div>

      <Card className="mx-auto max-w-md w-full">
        <CardHeader>
          <CardTitle>Vérifiez votre connexion</CardTitle>
          <CardDescription>
            Entrez le code de vérification envoyé à votre adresse email:{" "}
            <span className="font-medium">{email || "votre email"}</span>.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Field>
            {resendSuccess && (
              <div className="mb-4 rounded-lg bg-green-50 p-4 text-sm text-green-800 border border-green-200">
                Code renvoyé avec succès. Vérifiez votre email.
              </div>
            )}
            {error && (
              <div className="mb-4 rounded-lg bg-red-50 p-4 text-sm text-red-800 border border-red-200">
                {error}
              </div>
            )}
            <div className="flex items-center justify-between">
              <FieldLabel htmlFor="otp-verification">
                Code de vérification
              </FieldLabel>
              <Button
                variant="outline"
                size="xs"
                onClick={handleResendCode}
                disabled={resendCooldown > 0}
              >
                <RefreshCwIcon className="mr-2 h-3 w-3" />
                {resendCooldown > 0 ? `Renvoyer (${resendCooldown}s)` : "Renvoyer le code"}
              </Button>
            </div>
            <InputOTP
              maxLength={6}
              id="otp-verification"
              required
              value={otp}
              onChange={(value) => setOtp(value)}
            >
              <InputOTPGroup className="*:data-[slot=input-otp-slot]:h-12 *:data-[slot=input-otp-slot]:w-11 *:data-[slot=input-otp-slot]:text-xl">
                <InputOTPSlot index={0} />
                <InputOTPSlot index={1} />
                <InputOTPSlot index={2} />
              </InputOTPGroup>
              <InputOTPSeparator className="mx-2" />
              <InputOTPGroup className="*:data-[slot=input-otp-slot]:h-12 *:data-[slot=input-otp-slot]:w-11 *:data-[slot=input-otp-slot]:text-xl">
                <InputOTPSlot index={3} />
                <InputOTPSlot index={4} />
                <InputOTPSlot index={5} />
              </InputOTPGroup>
            </InputOTP>
            <FieldDescription>
              <Link
                href="/email-access-issue"
                className="text-primary hover:underline"
              >
                Je n'ai plus accès à cette adresse email.
              </Link>
            </FieldDescription>
          </Field>
        </CardContent>
        <CardFooter className="flex flex-col gap-4">
          <Button
            type="submit"
            className="w-full"
            onClick={handleVerify}
            disabled={otp.length !== 6 || isVerifying}
          >
            {isVerifying ? (
              <>
                <Spinner />
                Vérification...
              </>
            ) : (
              "Vérifier"
            )}
          </Button>

          {/* Support Contact Line */}
          <div className="text-center">
            <p className="text-sm text-muted-foreground">
              Vous rencontrez des difficultés pour vous connecter?{" "}
              <button
                onClick={handleContactSupport}
                className="text-primary font-medium underline-offset-4 hover:underline inline-flex items-center gap-1"
              >
                <HelpCircle className="h-3 w-3" />
                Contacter le support
              </button>
            </p>
          </div>
        </CardFooter>
      </Card>

      <p className="text-muted-foreground mt-8 text-center text-xs">
        © 2025 Audit ElectroPlanet. Tous droits réservés.
      </p>
    </div>
  )
}