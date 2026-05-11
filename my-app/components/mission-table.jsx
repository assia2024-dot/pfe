"use client";
import { useState, useEffect } from "react";
import { TablePagination } from "@/components/tables-pagination"
import { initialMissions } from "@/lib/data";
import { MoreHorizontalIcon } from "lucide-react";
import { Badge, BadgeDot } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
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
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
    Table, TableBody, TableCell,
    TableHead, TableHeader, TableRow, TableCaption, TableFooter
} from "@/components/ui/table";



const DEFAULT_ITEMS_PER_PAGE = 10




export function MissionsTable({ search = "", statut = "all", sort = "id", order = "asc" }) {

    const [missions, setMissions] = useState(initialMissions);
    const [missionToDelete, setMissionToDelete] = useState(null);

    const [currentPage, setCurrentPage] = useState(1)
    const [itemsPerPage, setItemsPerPage] = useState(DEFAULT_ITEMS_PER_PAGE)

    // reset to page 1 when filters change
    useEffect(() => {
        setCurrentPage(1)
    }, [search, statut, sort, order])

    // apply filters + sort
    const filtered = missions
        .filter((m) => {
            const matchSearch = search === "" ||
                m.titre.toLowerCase().includes(search.toLowerCase()) ||
                m.magasin.toLowerCase().includes(search.toLowerCase()) ||
                (m.auditeur?.toLowerCase().includes(search.toLowerCase()))

            const matchStatut = statut === "all" || m.statut === statut

            return matchSearch && matchStatut
        })
        .sort((a, b) => {
            const valA = a[sort] ?? ""
            const valB = b[sort] ?? ""
            if (order === "asc") return valA > valB ? 1 : -1
            return valA < valB ? 1 : -1
        })

    const totalPages = Math.ceil(filtered.length / itemsPerPage)
    const paginated = filtered.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    )


    function handleDelete(mission) {
        setMissions((prev) => prev.filter((m) => m.id !== mission.id));
        setMissionToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>
                            <Badge>Id</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Titre</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Magasin</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>
                                Auditeur
                            </Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Date Début</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Date Fin</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Statut</Badge>
                        </TableHead>
                        <TableHead className="text-right">
                            Action
                        </TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody >
                    {paginated.map((mission) => (
                        <TableRow key={mission.id}>
                            <TableCell>{mission.id}</TableCell>
                            <TableCell>{mission.titre}</TableCell>
                            <TableCell>{mission.magasin}</TableCell>
                            <TableCell>{mission.auditeur ?? "-"}</TableCell>
                            <TableCell>{mission.dateDebut}</TableCell>
                            <TableCell>{mission.dateFin}</TableCell>
                            <TableCell>
                                <Badge variant={mission.statut === "En attente" ? "en_attente" : mission.statut === "En cours" ? "en_cours" : "terminee"}>
                                    <BadgeDot />
                                    {mission.statut}

                                </Badge>
                            </TableCell>
                            <TableCell className="text-right">
                                <DropdownMenu>
                                    <DropdownMenuTrigger asChild>
                                        <Button variant="ghost" size="icon" className="size-8">
                                            <MoreHorizontalIcon />
                                            <span className="sr-only">Open menu</span>
                                        </Button>
                                    </DropdownMenuTrigger>

                                    <DropdownMenuContent align="end">
                                        <DropdownMenuItem>Modifier</DropdownMenuItem>
                                        <DropdownMenuSeparator />
                                        <DropdownMenuItem variant="destructive" onSelect={() => setMissionToDelete(mission)}>
                                            Supprimer
                                        </DropdownMenuItem>
                                    </DropdownMenuContent>
                                </DropdownMenu>
                            </TableCell>
                        </TableRow>
                    ))}
                    {Array.from({ length: itemsPerPage - paginated.length }).map((_, i) => (
                        <TableRow key={`empty-${i}`} className="pointer-events-none h-[49px]">
                            {Array.from({ length: 8 }).map((_, j) => (
                                <TableCell key={j}>&nbsp;</TableCell>
                            ))}
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <TablePagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={setCurrentPage}
                itemsPerPage={itemsPerPage}
                onItemsPerPageChange={setItemsPerPage}
                totalItems={filtered.length}
            />

            <AlertDialog
                open={!!missionToDelete}
                onOpenChange={(open) => !open && setMissionToDelete(null)}
            >
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Delete {missionToDelete?.titre}?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            This will permanently remove this user. This action cannot be undone.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Cancel</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(missionToDelete)}>
                            Delete
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    )
}
