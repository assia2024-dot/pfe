"use client"

import { useRouter } from "next/navigation"
import { Mail, ArrowLeft, Send } from "lucide-react"

import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export function EmailAccessIssue() {
  const router = useRouter()

  return (
    <div className="bg-muted/50 flex min-h-screen w-full flex-col items-center justify-center p-4">
      {/* Logo */}
      <div className="mb-8">
        <a href="/" className="flex items-center gap-2">
          <img src="/logo2.png" alt="Audit ElectroPlanet" className="size-25" />
        </a>
      </div>

      <Card className="mx-auto max-w-md w-full">
        <CardHeader className="text-center">
          <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-full bg-destructive/10">
            <Mail className="h-6 w-6 text-destructive" />
          </div>
          <CardTitle className="text-2xl">Accès à l'email impossible</CardTitle>
          <CardDescription>
            Vous n'avez plus accès à votre adresse email ?
          </CardDescription>
        </CardHeader>
        
        <CardContent className="space-y-4">
          <div className="rounded-lg bg-muted p-4 text-center">
            <p className="text-sm text-muted-foreground mb-2">
              Veuillez contacter notre support à l'adresse suivante :
            </p>
            <a 
              href="mailto:support@electroplanet.com" 
              className="text-primary font-medium hover:underline"
            >
              electroplanetaudit@gmail.com
            </a>
          </div>
          
          <div className="space-y-2">
            <p className="text-sm text-muted-foreground">
              Notre équipe vous assistera à retrouver l'accès à votre compte après vérification de votre identité.
            </p>
            <p className="text-sm text-muted-foreground">
              Merci de fournir les informations suivantes dans votre email :
            </p>
            <ul className="text-sm text-muted-foreground list-disc list-inside space-y-1 ml-2">
              <li>Votre nom complet</li>
              <li>Votre numéro de téléphone</li>
              <li>Une copie de votre pièce d'identité (recommandé)</li>
            </ul>
          </div>
        </CardContent>
        
        <CardFooter className="flex flex-col gap-3">
          <Button 
            onClick={() => router.push("/login")} 
            className="w-full"
          >
            <ArrowLeft className="mr-2 h-4 w-4" />
            Retour à la page de connexion
          </Button>
          
          <Button 
            onClick={() => window.location.href = "mailto:electroplanetaudit@gmail.com"} 
            variant="outline"
            className="w-full"
          >
            <Send className="mr-2 h-4 w-4" />
            Envoyer un email
          </Button>
        </CardFooter>
      </Card>
      
      {/* Footer */}
      <p className="text-muted-foreground mt-8 text-center text-xs">
        © 2025 Audit ElectroPlanet. Tous droits réservés.
      </p>
    </div>
  )
}