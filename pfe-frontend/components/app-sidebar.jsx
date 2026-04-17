"use client"

import * as React from "react"

import { NavMain } from "@/components/nav-main"
import { NavUser } from "@/components/nav-user"
import { TeamSwitcher } from "@/components/team-switcher"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar"
import { Building2 , HatGlasses , Form, LayoutDashboard , ListTodo , BookOpenIcon, UserRoundCog , FrameIcon, PieChartIcon, Map  } from "lucide-react"

// This is sample data.
const data = {
  user: {
    name: "DAHIR Assia",
    email: "dahirassia@gmail.com",
    avatar: "/avatars/shadcn.jpg",
  },
  teams: [
    {
      name: "ElectroPlanete",
      logo: (<Building2  />),
      role: "Administrateur"
    }
  ],
  navMain: [
    {
      title: "Tableau de bord",
      url: "#",
      icon: (
        <LayoutDashboard  />
      ),
      isActive: true,
      items: [
        {
          title: "Vue d’ensemble",
          url: "#",
        }
      ],
    },
    {
      title: "Utilisateurs & Rôles",
      url: "#",
      icon: (
        <UserRoundCog  />
      ),
      items: [
        {
          title: "Gestion des utilisateurs",
          url: "#",
        }
      ],
    },
    {
      title: "Missions",
      url: "#",
      icon: (
        <ListTodo />
      ),
      items: [
        {
          title: "Gestion des missions",
          url: "#",
        },
        {
          title: "Gestion des elements d'audit",
          url: "#",
        },
        {
          title: "Suivi des missions",
          url: "#",
        }
      ],
    },
    {
      title: "Entités",
      url: "#",
      icon: (
        <Map  />
      ),
      items: [
        {
          title: "Gestion des Régions",
          url: "#",
        },
        {
          title: "Gestion des Magasins",
          url: "#",
        }
      ],
    },
    {
      title: "Audits externes (Invités)",
      url: "#",
      icon: (
        <HatGlasses  />
      ),
      items: [
        {
          title: "Générer un lien d’audit",
          url: "#",
        },
        {
          title: "Historique des liens envoyés",
          url: "#",
        }
      ],
    },
  ]
}

export function AppSidebar({
  ...props
}) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
