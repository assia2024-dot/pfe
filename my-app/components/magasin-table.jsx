"use client";
import { useState, useEffect } from "react";
import { storeService } from "@/services/storeService";
import { TablePagination } from "@/components/tables-pagination";
import { MoreHorizontalIcon, Loader2 } from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
    AlertDialog, AlertDialogAction, AlertDialogCancel,
    AlertDialogContent, AlertDialogDescription,
    AlertDialogFooter, AlertDialogHeader, AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import {
    DropdownMenu, DropdownMenuContent, DropdownMenuItem,
    DropdownMenuSeparator, DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
    Table, TableBody, TableCell,
    TableHead, TableHeader, TableRow,
} from "@/components/ui/table";

const DEFAULT_ITEMS_PER_PAGE = 10

export function MagasinsTable({ search = "", actif = "all", sort = "id", order = "asc" }) {
    const [magasins, setMagasins] = useState([])
    const [totalItems, setTotalItems] = useState(0)
    const [totalPages, setTotalPages] = useState(1)
    const [currentPage, setCurrentPage] = useState(1)
    const [itemsPerPage, setItemsPerPage] = useState(DEFAULT_ITEMS_PER_PAGE)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState("")
    const [magasinToDelete, setMagasinToDelete] = useState(null)

    useEffect(() => {
        setCurrentPage(1)
    }, [search, actif, sort, order])

    useEffect(() => {
        fetchMagasins()
    }, [search, actif, sort, order, currentPage, itemsPerPage])

    const fetchMagasins = async () => {
        setLoading(true)
        setError("")
        try {
            const params = {
                page: currentPage - 1, // backend is 0-indexed
                size: itemsPerPage,
                sortBy: sort,
                sortDir: order,
            }
            if (search) params.keyword = search
            if (actif !== "all") params.actif = actif === "true"

            const response = await storeService.getFiltered(params)
            const data = response.data

            setMagasins(data.content)
            setTotalItems(data.totalElements)
            setTotalPages(data.totalPages)
        } catch (err) {
            setError("Erreur lors du chargement des magasins.")
        } finally {
            setLoading(false)
        }
    }

    const handleDelete = async (magasin) => {
        try {
            await storeService.delete(magasin.id)
            setMagasinToDelete(null)
            fetchMagasins()
        } catch {
            setError("Erreur lors de la suppression.")
        }
    }

    return (
        <>
            {error && (
                <div className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-800 border border-red-200">
                    {error}
                </div>
            )}

            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead><Badge>Id</Badge></TableHead>
                        <TableHead><Badge>Code</Badge></TableHead>
                        <TableHead><Badge>Nom</Badge></TableHead>
                        <TableHead><Badge>Adresse</Badge></TableHead>
                        <TableHead><Badge>Ville</Badge></TableHead>
                        <TableHead><Badge>Region</Badge></TableHead>
                        <TableHead><Badge>Actif</Badge></TableHead>
                        <TableHead className="text-right">Action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {loading ? (
                        <TableRow>
                            <TableCell colSpan={8} className="text-center py-8">
                                <Loader2 className="animate-spin mx-auto h-6 w-6 text-muted-foreground" />
                            </TableCell>
                        </TableRow>
                    ) : magasins.length === 0 ? (
                        <TableRow>
                            <TableCell colSpan={8} className="text-center py-8 text-muted-foreground">
                                Aucun magasin trouvé.
                            </TableCell>
                        </TableRow>
                    ) : (
                        <>
                            {magasins.map((magasin) => (
                                <TableRow key={magasin.id}>
                                    <TableCell>{magasin.id}</TableCell>
                                    <TableCell>{magasin.code}</TableCell>
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
                            {Array.from({ length: itemsPerPage - magasins.length }).map((_, i) => (
                                <TableRow key={`empty-${i}`} className="pointer-events-none h-[49px]">
                                    {Array.from({ length: 8 }).map((_, j) => (
                                        <TableCell key={j}>&nbsp;</TableCell>
                                    ))}
                                </TableRow>
                            ))}
                        </>
                    )}
                </TableBody>
            </Table>

            <TablePagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={setCurrentPage}
                itemsPerPage={itemsPerPage}
                onItemsPerPageChange={setItemsPerPage}
                totalItems={totalItems}
            />

            <AlertDialog open={!!magasinToDelete} onOpenChange={(open) => !open && setMagasinToDelete(null)}>
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Supprimer {magasinToDelete?.nom} ?
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
        </>
    )
}