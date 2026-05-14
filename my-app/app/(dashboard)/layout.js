import { Providers } from "@/components/providers"
import { AppSidebar } from "@/components/app-sidebar"
import { BreadcrumbHeader } from "@/components/breadcrumb-header"
import { SidebarInset } from "@/components/ui/sidebar"
import { AuthGuard } from "@/components/AuthGuard"

export default function DashboardLayout({ children }) {
  return (
    <AuthGuard>
      <Providers>
        <AppSidebar />
        <SidebarInset>
          <main className="flex flex-col h-screen w-full overflow-hidden">
            <BreadcrumbHeader />
            <div className="p-4 overflow-auto flex-1">
              {children}
            </div>
          </main>
        </SidebarInset>
      </Providers>
    </AuthGuard>
  )
}