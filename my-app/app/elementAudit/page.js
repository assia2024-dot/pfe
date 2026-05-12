"use client"
import { useState, useEffect } from "react"
import { Input } from "@/components/ui/input"
import { ElementsAuditTable } from "@/components/element-table"
import { AddNewElement } from "@/components/elementForms/add-new-element"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Button } from "@/components/ui/button"
import { ArrowUpDown, X } from "lucide-react"

const ElementAudit = () => {
    const [search, setSearch] = useState("")
    const [debouncedSearch, setDebouncedSearch] = useState("")
    const [statut, setStatut] = useState("all")
    const [sort, setSort] = useState("id")
    const [order, setOrder] = useState("asc")

    useEffect(() => {
        const timer = setTimeout(() => setDebouncedSearch(search), 500)
        return () => clearTimeout(timer)
    }, [search])

    function handleReset() {
        setSearch("")
        setDebouncedSearch("")
        setStatut("all")
        setSort("id")
        setOrder("asc")
    }

    const hasFilters = search || statut !== "all" || sort !== "id" || order !== "asc"

    return (
        <div className="flex flex-col gap-4">
            <p className="text-sm text-muted-foreground">Liste des éléments d'audit.</p>
            <div className="flex flex-wrap items-center gap-2">
                <Input
                    placeholder="Rechercher un élément..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    className="w-48"
                />
                <Select value={statut} onValueChange={setStatut}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Statut" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="all">Tous</SelectItem>
                        <SelectItem value="Actif">Actif</SelectItem>
                        <SelectItem value="Inactif">Inactif</SelectItem>
                    </SelectContent>
                </Select>
                <Select value={sort} onValueChange={setSort}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Trier par" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="id">ID</SelectItem>
                        <SelectItem value="nom">Nom</SelectItem>
                        <SelectItem value="statut">Statut</SelectItem>
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
                    <AddNewElement />
                </div>
            </div>
            <div className="overflow-x-auto rounded-md border">
                <ElementsAuditTable
                    search={debouncedSearch}
                    statut={statut}
                    sort={sort}
                    order={order}
                />
            </div>
        </div>
    )
}

export default ElementAudit;