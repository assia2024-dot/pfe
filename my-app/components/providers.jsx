"use client"

import { SidebarProvider } from "@/components/ui/sidebar"
import { TooltipProvider } from "@/components/ui/tooltip"
import { ThemeProvider } from "next-themes"

export function Providers({ children }) {
  return (
    <ThemeProvider 
      attribute="class" 
      defaultTheme="dark"
      disableTransitionOnChange
    >
      <TooltipProvider>
        <SidebarProvider>
          {children}
        </SidebarProvider>
      </TooltipProvider>
    </ThemeProvider>
  )
}