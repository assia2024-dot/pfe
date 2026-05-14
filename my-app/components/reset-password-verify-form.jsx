"use client"

import { authService } from "@/services/authService"
import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import { RefreshCwIcon, ArrowLeft, HelpCircle } from "lucide-react"

import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
    CardFooter
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

export function ResetPasswordVerifyForm() {
    const [otp, setOtp] = useState("")
    const [isVerifying, setIsVerifying] = useState(false)
    const [email, setEmail] = useState("")
    const [error, setError] = useState("")
    const [resendSuccess, setResendSuccess] = useState(false)
    const [resendCooldown, setResendCooldown] = useState(0)
    const router = useRouter()

    useEffect(() => {
        const resetEmail = sessionStorage.getItem("resetEmail")
        if (resetEmail) {
            setEmail(resetEmail)
        } else {
            router.push("/forgot-password")
        }
    }, [router])

    const handleVerify = async () => {
        if (otp.length !== 6) return
        setIsVerifying(true)
        try {
            await authService.verifyResetCode(email, otp)
            // Store verified code to use in reset-password page
            sessionStorage.setItem("resetCode", otp)
            router.push("/reset-password")
        } catch (err) {
            setError(err.response?.data?.message || "Code incorrect ou expiré.")
        } finally {
            setIsVerifying(false)
        }
    }

    const handleResendCode = async () => {
        try {
            await authService.resendResetCode(email)
            setError("")
            setResendSuccess(true)
            setResendCooldown(60)
            setTimeout(() => setResendSuccess(false), 4000)

            const interval = setInterval(() => {
                setResendCooldown((prev) => {
                    if (prev <= 1) {
                        clearInterval(interval)
                        return 0
                    }
                    return prev - 1
                })
            }, 1000)
        } catch {
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
                    <CardTitle>Vérification du code</CardTitle>
                    <CardDescription>
                        Entrez le code de vérification envoyé à votre adresse email:{" "}
                        <span className="font-medium">{email || "votre email"}</span>.
                    </CardDescription>
                </CardHeader>
                <CardContent>
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
                    <Field>
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
                            <Button
                                variant="link"
                                className="px-0 text-sm"
                                onClick={() => router.push("/forgot-password")}
                            >
                                <ArrowLeft className="mr-1 h-3 w-3" />
                                Modifier l'adresse email
                            </Button>
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
                            "Vérifier le code"
                        )}
                    </Button>
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