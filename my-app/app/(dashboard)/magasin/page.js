"use client"
import { useState, useEffect } from "react"
import { AddNewMagasin } from "@/components/magasinForms/add-new-magasin";
import { MagasinsTable } from "@/components/magasin-table"
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

const Magasin = () => {
    const [search, setSearch] = useState("")
    const [debouncedSearch, setDebouncedSearch] = useState("")
    const [actif, setActif] = useState("all")
    const [sort, setSort] = useState("id")
    const [order, setOrder] = useState("asc")

    useEffect(() => {
        const timer = setTimeout(() => setDebouncedSearch(search), 500)
        return () => clearTimeout(timer)
    }, [search])

    function handleReset() {
        setSearch("")
        setDebouncedSearch("")
        setActif("all")
        setSort("id")
        setOrder("asc")
    }

    const hasFilters = search || actif !== "all" || sort !== "id" || order !== "asc"

    return (
        <div className="flex flex-col gap-4">
            <p className="text-sm text-muted-foreground">Liste des magasins ElectroPlanet.</p>
            <div className="flex flex-wrap items-center gap-2">
                <Input
                    placeholder="Rechercher un magasin..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    className="w-48"
                />
                <Select value={actif} onValueChange={setActif}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Statut" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="all">Tous</SelectItem>
                        <SelectItem value="true">Actif</SelectItem>
                        <SelectItem value="false">Inactif</SelectItem>
                    </SelectContent>
                </Select>
                <Select value={sort} onValueChange={setSort}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Trier par" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="id">ID</SelectItem>
                        <SelectItem value="nom">Nom</SelectItem>
                        <SelectItem value="ville">Ville</SelectItem>
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
                    <AddNewMagasin />
                </div>
            </div>
            <div className="overflow-x-auto rounded-md">
                <MagasinsTable
                    search={debouncedSearch}
                    actif={actif}
                    sort={sort}
                    order={order}
                />
            </div>
        </div>
    )
}

export default Magasin;