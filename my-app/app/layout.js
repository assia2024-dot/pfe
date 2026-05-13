/*import { Providers } from "@/components/providers"
import { AppSidebar } from "@/components/app-sidebar"
import { BreadcrumbHeader } from "@/components/breadcrumb-header"
import { SidebarInset } from "@/components/ui/sidebar"
import { Geist, Geist_Mono } from "next/font/google"
import "./globals.css"

const geistSans = Geist({ variable: "--font-geist-sans", subsets: ["latin"] })
const geistMono = Geist_Mono({ variable: "--font-geist-mono", subsets: ["latin"] })

export const metadata = {
  title: "ElectroPlanet Audit",
  description: "Gestion des audits qualité",
}

export default function RootLayout({ children }) {
  return (
    <html lang="fr" className={`${geistSans.variable} ${geistMono.variable}`} suppressHydrationWarning>
      <body>
        <Providers>
          <AppSidebar />
          <SidebarInset className="min-w-0 overflow-hidden">
            <main className="flex flex-col h-svh w-full">
              <BreadcrumbHeader />
              <div className="p-4 overflow-auto flex-1 min-w-0">
                {children}
              </div>
            </main>
          </SidebarInset>
        </Providers>
      </body>
    </html>
  )
}*/

import { Providers } from "@/components/providers"
import { Geist, Geist_Mono } from "next/font/google"
import "./globals.css"

const geistSans = Geist({ variable: "--font-geist-sans", subsets: ["latin"] })
const geistMono = Geist_Mono({ variable: "--font-geist-mono", subsets: ["latin"] })

export const metadata = {
  title: "ElectroPlanet Audit",
  description: "Gestion des audits qualité",
}

export default function RootLayout({ children }) {
  return (
    <html lang="fr" className={`${geistSans.variable} ${geistMono.variable}`} suppressHydrationWarning>
      <body>
        <Providers>
          {children}
        </Providers>
      </body>
    </html>
  )
}