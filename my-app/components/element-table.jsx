"use client";
import { useState, useEffect } from "react";
import { MoreHorizontalIcon } from "lucide-react";
import { initialElements } from "@/lib/data";
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
export function ElementsAuditTable({ search = "", statut = "all", sort = "id", order = "asc" }) {

    const [elements, setElements] = useState(initialElements);
    const [elementToDelete, setElementToDelete] = useState(null);
    const [currentPage, setCurrentPage] = useState(1)
    const [itemsPerPage, setItemsPerPage] = useState(DEFAULT_ITEMS_PER_PAGE)

    useEffect(() => {
        setCurrentPage(1)
    }, [search, statut, sort, order])

    const filtered = elements
        .filter((e) => {
            const matchSearch = search === "" ||
                e.nom.toLowerCase().includes(search.toLowerCase()) ||
                e.description.toLowerCase().includes(search.toLowerCase())

            const matchStatut = statut === "all" || e.statut === statut

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

    function handleDelete(element) {
        setElements((prev) => prev.filter((e) => e.id !== element.id));
        setElementToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead><Badge>Id</Badge></TableHead>
                        <TableHead><Badge>Nom</Badge></TableHead>
                        <TableHead><Badge>Description</Badge></TableHead>
                        <TableHead><Badge>Statut</Badge></TableHead>
                        <TableHead className="text-right">Action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {paginated.map((element) => (
                        <TableRow key={element.id}>
                            <TableCell>{element.id}</TableCell>
                            <TableCell>{element.nom}</TableCell>
                            <TableCell>{element.description}</TableCell>
                            <TableCell>
                                <Badge variant={element.statut === "Actif" ? "actif" : "inactif"}>
                                    {element.statut}
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
                                            onSelect={() => setElementToDelete(element)}
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
                totalItems={elements.length}
            />

            <AlertDialog
                open={!!elementToDelete}
                onOpenChange={(open) => !open && setElementToDelete(null)}
            >
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Supprimer {elementToDelete?.nom}?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            Cette action est irréversible. Cet élément sera définitivement supprimé.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Annuler</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(elementToDelete)}>
                            Supprimer
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    )
}