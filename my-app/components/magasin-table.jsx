"use client";
import { useState, useEffect } from "react";
import { initialMagasins } from "@/lib/data";
import { MoreHorizontalIcon } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { TablePagination } from "@/components/tables-pagination"
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
    TableFooter, TableHead, TableHeader, TableRow, TableCaption
} from "@/components/ui/table";

const DEFAULT_ITEMS_PER_PAGE = 10


export function MagasinsTable({ search = "", actif = "all", sort = "id", order = "asc" }) {

    const [magasins, setMagasins] = useState(initialMagasins);
    const [magasinToDelete, setMagasinToDelete] = useState(null);
    const [currentPage, setCurrentPage] = useState(1)
    const [itemsPerPage, setItemsPerPage] = useState(DEFAULT_ITEMS_PER_PAGE)

    useEffect(() => {
        setCurrentPage(1)
    }, [search, actif, sort, order])

    const filtered = magasins
        .filter((m) => {
            const matchSearch = search === "" ||
                m.nom.toLowerCase().includes(search.toLowerCase()) ||
                m.ville.toLowerCase().includes(search.toLowerCase()) ||
                m.adresse.toLowerCase().includes(search.toLowerCase())

            const matchActif = actif === "all" || String(m.actif) === actif

            return matchSearch && matchActif
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

    function handleDelete(magasin) {
        setMagasins((prev) => prev.filter((m) => m.id !== magasin.id));
        setMagasinToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead><Badge>Id</Badge></TableHead>
                        <TableHead><Badge>Nom</Badge></TableHead>
                        <TableHead><Badge>Adresse</Badge></TableHead>
                        <TableHead><Badge>Ville</Badge></TableHead>
                        <TableHead><Badge>Region</Badge></TableHead>
                        <TableHead><Badge>Actif</Badge></TableHead>
                        <TableHead className="text-right">Action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {paginated.map((magasin) => (
                        <TableRow key={magasin.id}>
                            <TableCell>{magasin.id}</TableCell>
                            <TableCell>{magasin.nom}</TableCell>
                            <TableCell>{magasin.adresse}</TableCell>
                            <TableCell>{magasin.ville}</TableCell>
                            <TableCell>{magasin.region}</TableCell>
                            <TableCell>
                                <Badge variant={magasin.actif ? "actif" : "inactif"}>
                                    {magasin.actif ? "Actif" : "Inactif"}
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
                                        <DropdownMenuItem
                                            variant="destructive"
                                            onSelect={() => setMagasinToDelete(magasin)}
                                        >
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
                    totalItems={magasins.length}
                />

            <AlertDialog
                open={!!magasinToDelete}
                onOpenChange={(open) => !open && setMagasinToDelete(null)}
            >
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Supprimer {magasinToDelete?.nom}?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            Cette action est irréversible. Ce magasin sera définitivement supprimé.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Annuler</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(magasinToDelete)}>
                            Supprimer
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    )
}