"use client"

import { Moon, Sun } from "lucide-react"
import { useTheme } from "next-themes"
import { Button } from "@/components/ui/button"
import { useEffect, useState } from "react"

export function ModeToggle() {
  const { theme, setTheme } = useTheme()
  const [mounted, setMounted] = useState(false)

  useEffect(() => setMounted(true), [])

  // Render same icon on server and first client render to avoid mismatch
  if (!mounted) return (
    <Button variant="ghost" size="icon" disabled>
      <Moon className="h-4 w-4 text-foreground" />
    </Button>
  )

  return (
    <Button
      variant="ghost"
      size="icon"
      onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
    >
      {theme === "dark"
        ? <Sun className="h-4 w-4 text-foreground" />
        : <Moon className="h-4 w-4 text-foreground" />
      }
    </Button>
  )
}