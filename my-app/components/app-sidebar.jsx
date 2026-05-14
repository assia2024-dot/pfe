"use client"
import { authService } from "@/services/authService"
import { useState, useEffect } from "react";
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "@/components/ui/collapsible"
import {
  ChevronRight, Building2, LayoutDashboard, UserRoundCog,
  ListTodo, Map, HatGlasses, ChevronsUpDownIcon, UserRound,
  UserRoundPen, BadgeQuestionMark, LogOutIcon
} from "lucide-react"

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarMenuSub,
  SidebarMenuSubButton,
  SidebarMenuSubItem,
  SidebarTrigger,
  useSidebar,
} from "@/components/ui/sidebar"
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
} from "@/components/ui/avatar"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { usePathname, useRouter } from "next/navigation"
import Link from "next/link"


const items = [
  {
    title: "Tableau de bord",
    url: "#",
    icon: <LayoutDashboard />,
    isActive: true,
    items: [
      { title: "Vue d'ensemble", url: "/dashboard" }
    ],
  },
  {
    title: "Utilisateurs & Rôles",
    url: "#",
    icon: <UserRoundCog />,
    isActive: true,
    items: [
      { title: "Gestion des utilisateurs", url: "/users" }
    ],
  },
  {
    title: "Missions",
    url: "#",
    icon: <ListTodo />,
    isActive: true,
    items: [
      { title: "Gestion des missions", url: "/missions" },
      { title: "Gestion des elements d'audit", url: "/elementAudit" },
      { title: "Suivi des missions", url: "/missions/suivi" },
    ],
  },
  {
    title: "Entités",
    url: "#",
    icon: <Map />,
    isActive: true,
    items: [
      { title: "Gestion des Magasins", url: "/magasin" },
    ],
  },
  {
    title: "Audits externes (Invités)",
    url: "#",
    icon: <HatGlasses />,
    isActive: true,
    items: [
      { title: "Générer un lien d'audit", url: "/audits/lien" },
      { title: "Historique des liens envoyés", url: "/audits/historique" },
    ],
  },
]

function getInitials(name) {
  return name
    .split(" ")
    .map((word) => word[0])
    .join("")
    .toUpperCase()
    .slice(0, 2)
}





function SidebarUserFooter() {
  const { isMobile } = useSidebar()
  const [showLogoutDialog, setShowLogoutDialog] = useState(false)
  const router = useRouter()
  const [user, setUser] = useState({ nom: "", prenom: "", email: "", role: "" })


  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem("user") || "{}")
    setUser(stored)
  }, [])
  const displayName = `${user.nom || ""} ${user.prenom || ""}`.trim() || "Utilisateur"

  const handleLogout = async () => {
    try {
      await authService.logout()
    } catch {
      // Even if it fails, we still logout locally
    } finally {
      localStorage.removeItem("token")
      localStorage.removeItem("user")
      setShowLogoutDialog(false)
      router.push("/login")
    }
  }
  return (
    <>
      <SidebarMenu>
        <SidebarMenuItem>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <SidebarMenuButton
                size="lg"
                className="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground"
              >
                <Avatar className="h-8 w-8 rounded-lg">
                  <AvatarImage src={user.avatar} alt={displayName} />
                  <AvatarFallback className="rounded-lg">{getInitials(displayName)}</AvatarFallback>
                </Avatar>
                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-medium text-base">{displayName.toUpperCase()}</span>
                  <span className="truncate text-sm">{(user.role || "").toLowerCase()}</span>
                </div>
                <ChevronsUpDownIcon className="ml-auto size-4" />
              </SidebarMenuButton>
            </DropdownMenuTrigger>
            <DropdownMenuContent
              className="w-(--radix-dropdown-menu-trigger-width) min-w-56 rounded-lg"
              side={isMobile ? "bottom" : "right"}
              align="end"
              sideOffset={4}
            >
              <DropdownMenuLabel className="p-0 font-normal">
                <div className="flex items-center gap-2 px-1 py-1.5 text-left text-sm">
                  <Avatar className="h-8 w-8 rounded-lg">
                    <AvatarImage src={user.avatar} alt={displayName} />
                    <AvatarFallback className="rounded-lg">{getInitials(displayName)}</AvatarFallback>
                  </Avatar>
                  <div className="grid flex-1 text-left text-sm leading-tight">
                    <span className="truncate font-medium">{displayName.toUpperCase()}</span>
                    <span className="truncate text-xs">{(user.email || "").toLowerCase()}</span>
                  </div>
                </div>
              </DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuGroup>
                <DropdownMenuItem>
                  <UserRound />
                  Profil
                </DropdownMenuItem>
                <DropdownMenuItem>
                  <UserRoundPen />
                  Modifier le profil
                </DropdownMenuItem>
                <DropdownMenuItem>
                  <BadgeQuestionMark />
                  Aide
                </DropdownMenuItem>
              </DropdownMenuGroup>
              <DropdownMenuSeparator />
              <DropdownMenuItem
                variant="destructive"
                onSelect={() => setShowLogoutDialog(true)} // 👈 key line
              >
                <LogOutIcon />
                Déconnexion
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </SidebarMenuItem>
      </SidebarMenu>

      <AlertDialog open={showLogoutDialog} onOpenChange={setShowLogoutDialog}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Déconnexion</AlertDialogTitle>
            <AlertDialogDescription>
              Êtes-vous sûr de vouloir vous déconnecter ?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Annuler</AlertDialogCancel>
            <AlertDialogAction onClick={handleLogout}>
              Déconnexion
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  )
}

function SidebarBrand() {
  const { state } = useSidebar()

  return (
    <SidebarHeader className="p-3">
      <div className="flex items-center gap-3">
        <div className="flex-shrink-0">
          <img src="/logo2.png" alt="ElectroPlanet" className="w-8 h-8 rounded-md" />
        </div>
        {state === "expanded" && (
          <>
            <span className="font-semibold flex-1">ElectroPlanet</span>
            <SidebarTrigger />
          </>
        )}
      </div>
    </SidebarHeader>
  )
}

export function AppSidebar() {
  const pathname = usePathname()
  const { state } = useSidebar()

  return (
    <Sidebar collapsible="icon">
      <SidebarBrand />

      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Platform</SidebarGroupLabel>
          <SidebarMenu>
            {items.map((item) => {
              const isParentActive = item.items?.some((sub) => sub.url === pathname)

              return (
                <Collapsible
                  key={item.title}
                  asChild
                  defaultOpen={item.isActive || isParentActive}
                  className="group/collapsible"
                >
                  <SidebarMenuItem>
                    <CollapsibleTrigger asChild>
                      <SidebarMenuButton
                        tooltip={item.title}
                        isActive={isParentActive}
                        asChild={state === "collapsed"}
                      >
                        {state === "collapsed" ? (
                          <Link href={item.items[0].url}>
                            {item.icon}
                            <span>{item.title}</span>
                            <ChevronRight className="ml-auto transition-transform duration-200 group-data-[state=open]/collapsible:rotate-90" />
                          </Link>
                        ) : (
                          <>
                            {item.icon}
                            <span>{item.title}</span>
                            <ChevronRight className="ml-auto transition-transform duration-200 group-data-[state=open]/collapsible:rotate-90" />
                          </>
                        )}
                      </SidebarMenuButton>
                    </CollapsibleTrigger>
                    <CollapsibleContent>
                      <SidebarMenuSub>
                        {item.items?.map((subItem) => (
                          <SidebarMenuSubItem key={subItem.title}>
                            <SidebarMenuSubButton
                              href={subItem.url}
                              isActive={pathname === subItem.url}
                            >
                              {subItem.title}
                            </SidebarMenuSubButton>
                          </SidebarMenuSubItem>
                        ))}
                      </SidebarMenuSub>
                    </CollapsibleContent>
                  </SidebarMenuItem>
                </Collapsible>
              )
            })}
          </SidebarMenu>
        </SidebarGroup>
      </SidebarContent>

      <SidebarFooter>
        <SidebarUserFooter />
      </SidebarFooter>
    </Sidebar>
  )
}