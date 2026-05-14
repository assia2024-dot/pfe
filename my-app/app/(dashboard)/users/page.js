"use client"
import { useState, useEffect } from "react"
import { UserTable } from "@/components/user-table"
import { AddNewUser } from "@/components/UserForms/add-new-user"
import { Input } from "@/components/ui/input"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Button } from "@/components/ui/button"
import { ArrowUpDown, X } from "lucide-react"

const Users = () => {
    const [search, setSearch] = useState("")
    const [debouncedSearch, setDebouncedSearch] = useState("")
    const [role, setRole] = useState("all")
    const [sort, setSort] = useState("id")
    const [order, setOrder] = useState("asc")

    useEffect(() => {
        const timer = setTimeout(() => setDebouncedSearch(search), 500)
        return () => clearTimeout(timer)
    }, [search])

    function handleReset() {
        setSearch("")
        setDebouncedSearch("")
        setRole("all")
        setSort("id")
        setOrder("asc")
    }

    const hasFilters = search || role !== "all" || sort !== "id" || order !== "asc"

    return (
        <div className="flex flex-col gap-4">
            <p className="text-sm text-muted-foreground">Liste des utilisateurs et leurs rôles.</p>
            <div className="flex flex-wrap items-center gap-2">
                <Input
                    placeholder="Rechercher un utilisateur..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    className="w-48"
                />
                <Select value={role} onValueChange={setRole}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Rôle" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="all">Tous</SelectItem>
                        <SelectItem value="ADMIN">Admin</SelectItem>
                        <SelectItem value="AUDITEUR">Auditeur</SelectItem>
                    </SelectContent>
                </Select>
                <Select value={sort} onValueChange={setSort}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Trier par" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="id">ID</SelectItem>
                        <SelectItem value="prenom">Prénom</SelectItem>
                        <SelectItem value="nom">Nom</SelectItem>
                        <SelectItem value="email">Email</SelectItem>
                        <SelectItem value="role">Rôle</SelectItem>
                    </SelectContent>
                </Select>
                <Button
                    variant="outline"
                    size="icon"
                    onClick={() => setOrder(order === "asc" ? "desc" : "asc")}
                >
                    <ArrowUpDown className="h-4 w-4" />
                </Button>
                {hasFilters && (
                    <Button variant="ghost" size="sm" onClick={handleReset}>
                        <X className="h-4 w-4 mr-1" />
                        Réinitialiser
                    </Button>
                )}
                <div className="ml-auto">
                    <AddNewUser />
                </div>
            </div>
            <div className="overflow-x-auto rounded-md">
                <UserTable
                    search={debouncedSearch}
                    role={role}
                    sort={sort}
                    order={order}
                />
            </div>
        </div>
    )
}

export default Users;