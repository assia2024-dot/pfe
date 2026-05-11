/*
import { MissionsTable } from "@/components/mission-table";
import { AddNewMission } from "@/components/missionForms/add-new-mission";
import {Input} from "@/components/ui/input"

const Missions = () => {
    return (
        <div className="flex flex-col gap-4">
            <p className="text-xl text-muted-foreground">Liste des missions d'audit récentes</p>
            <div className="flex items-center justify-between gap-4">
                <Input placeholder="Rechercher une mission..." className="max-w-sm" />
                <AddNewMission />
            </div>
            <div className="overflow-x-auto rounded-md ">
                <MissionsTable />
            </div>
        </div>
    )
}

export default Missions;*/

"use client"
import { useState, useEffect } from "react"
import { MissionsTable } from "@/components/mission-table";
import { AddNewMission } from "@/components/missionForms/add-new-mission";
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

const Missions = () => {
    const [search, setSearch] = useState("")
    const [debouncedSearch, setDebouncedSearch] = useState("")
    const [statut, setStatut] = useState("all")
    const [sort, setSort] = useState("id")
    const [order, setOrder] = useState("asc")

    // debounce search
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
            <p className="text-sm text-muted-foreground">Liste des missions d'audit récentes.</p>

            {/* Filters row */}
            <div className="flex flex-wrap items-center gap-2">
                {/* Search */}
                <Input
                    placeholder="Rechercher..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    className="w-48"
                />

                {/* Statut filter */}
                <Select value={statut} onValueChange={setStatut}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Statut" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="all">Tous</SelectItem>
                        <SelectItem value="En attente">En attente</SelectItem>
                        <SelectItem value="En cours">En cours</SelectItem>
                        <SelectItem value="Terminée">Terminée</SelectItem>
                    </SelectContent>
                </Select>

                {/* Sort by */}
                <Select value={sort} onValueChange={setSort}>
                    <SelectTrigger className="w-28">
                        <SelectValue placeholder="Trier par" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="id">ID</SelectItem>
                        <SelectItem value="titre">Titre</SelectItem>
                        <SelectItem value="magasin">Magasin</SelectItem>
                        <SelectItem value="dateDebut">Date Début</SelectItem>
                        <SelectItem value="dateFin">Date Fin</SelectItem>
                        <SelectItem value="statut">Statut</SelectItem>
                    </SelectContent>
                </Select>

                {/* Order */}
                <Button
                    variant="outline"
                    size="icon"
                    onClick={() => setOrder(order === "asc" ? "desc" : "asc")}
                >
                    <ArrowUpDown className="h-4 w-4" />
                </Button>

                {/* Reset */}
                {hasFilters && (
                    <Button variant="ghost" size="sm" onClick={handleReset}>
                        <X className="h-4 w-4 mr-1" />
                        Réinitialiser
                    </Button>
                )}

                {/* Add button pushed to right */}
                <div className="ml-auto">
                    <AddNewMission />
                </div>
            </div>

            <div className="overflow-x-auto rounded-md">
                <MissionsTable
                    search={debouncedSearch}
                    statut={statut}
                    sort={sort}
                    order={order}
                />
            </div>
        </div>
    )
}

export default Missions;