import { Providers } from "@/components/providers"
import { AppSidebar } from "@/components/app-sidebar"
import { BreadcrumbHeader } from "@/components/breadcrumb-header"
import { SidebarInset } from "@/components/ui/sidebar"  // ← add this
import { Geist, Geist_Mono } from "next/font/google"
import "./globals.css"

const geistSans = Geist({ variable: "--font-geist-sans", subsets: ["latin"] })
const geistMono = Geist_Mono({ variable: "--font-geist-mono", subsets: ["latin"] })

export const metadata = {
  title: "ElectroPlanet",
  description: "Gestion des audits qualité",
}

export default function RootLayout({ children }) {
  return (
    <html lang="fr" className={`${geistSans.variable} ${geistMono.variable}`} suppressHydrationWarning>
      <body>
        <Providers>
          <AppSidebar />
          <SidebarInset>
            <main className="flex flex-col flex-1 w-full">
              <BreadcrumbHeader />
              <div className="p-4">
                {children}
              </div>
            </main>
          </SidebarInset>
        </Providers>
      </body>
    </html>
  )
}