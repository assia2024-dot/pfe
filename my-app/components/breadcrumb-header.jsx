"use client"

import { usePathname } from "next/navigation"
import Link from "next/link"
import { SidebarTrigger ,useSidebar  } from "@/components/ui/sidebar"
import { ChevronRight, Home } from "lucide-react"
import { ModeToggle } from "@/components/mode-toggle"

const items = [
  {
    title: "Tableau de bord",
    items: [{ title: "Vue d'ensemble", url: "/dashboard" }],
  },
  {
    title: "Utilisateurs & Rôles",
    items: [{ title: "Gestion des utilisateurs", url: "/users" }],
  },
  {
    title: "Missions",
    items: [
      { title: "Gestion des missions", url: "/missions" },
      { title: "Gestion des elements d'audit", url: "/missions/audit" },
      { title: "Suivi des missions", url: "/missions/suivi" },
    ],
  },
  {
    title: "Entités",
    items: [
      { title: "Gestion des Régions", url: "/entites/regions" },
      { title: "Gestion des Magasins", url: "/entites/magasins" },
    ],
  },
  {
    title: "Audits externes (Invités)",
    items: [
      { title: "Générer un lien d'audit", url: "/audits/lien" },
      { title: "Historique des liens envoyés", url: "/audits/historique" },
    ],
  },
]

export function BreadcrumbHeader() {
  const pathname = usePathname()
  const { state } = useSidebar()  // ← add this

  let parent = null
  let current = null

  for (const item of items) {
    const match = item.items?.find((sub) => sub.url === pathname)
    if (match) {
      parent = item.title
      current = match.title
      break
    }
  }

  return (
    <header className="flex items-center gap-3 border-b px-4 py-3 bg-background">
{state === "collapsed" && (
  <SidebarTrigger className="text-foreground hover:bg-accent" />
)}
{state === "collapsed" && <div className="w-px h-5 bg-border" />}
      <nav className="flex items-center gap-1 text-sm text-muted-foreground">
        <Link href="/" className="hover:text-foreground transition-colors">
          <Home className="w-4 h-4" />
        </Link>
        {parent && (
          <>
            <ChevronRight className="w-4 h-4" />
            <span>{parent}</span>
          </>
        )}
        {current && (
          <>
            <ChevronRight className="w-4 h-4" />
            <span className="text-foreground font-medium">{current}</span>
          </>
        )}
      </nav>
      <div className="ml-auto">
  <ModeToggle />
</div>
    </header>
  )
}