"use client";
import { useState, useEffect } from "react";
import { userService } from "@/services/userService";
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

export function UserTable({ search = "", role = "all", sort = "id", order = "asc" }) {
    const [users, setUsers] = useState([])
    const [totalItems, setTotalItems] = useState(0)
    const [totalPages, setTotalPages] = useState(1)
    const [currentPage, setCurrentPage] = useState(1)
    const [itemsPerPage, setItemsPerPage] = useState(DEFAULT_ITEMS_PER_PAGE)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState("")
    const [userToDelete, setUserToDelete] = useState(null)

    useEffect(() => {
        setCurrentPage(1)
    }, [search, role, sort, order])

    useEffect(() => {
        fetchUsers()
    }, [search, role, sort, order, currentPage, itemsPerPage])

    const fetchUsers = async () => {
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
            if (role !== "all") params.role = role

            const response = await userService.getFiltered(params)
            const data = response.data

            setUsers(data.content)
            setTotalItems(data.totalElements)
            setTotalPages(data.totalPages)
        } catch (err) {
            setError("Erreur lors du chargement des utilisateurs.")
        } finally {
            setLoading(false)
        }
    }

    const handleDelete = async (user) => {
        try {
            await userService.delete(user.id)
            setUserToDelete(null)
            fetchUsers()
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
                        <TableHead><Badge>Identifiant</Badge></TableHead>
                        <TableHead><Badge>Prénom</Badge></TableHead>
                        <TableHead><Badge>Nom</Badge></TableHead>
                        <TableHead><Badge>Email</Badge></TableHead>
                        <TableHead><Badge>Rôle</Badge></TableHead>
                        <TableHead><Badge>Statut</Badge></TableHead>
                        <TableHead className="text-right">Action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {loading ? (
                        <TableRow>
                            <TableCell colSpan={7} className="text-center py-8">
                                <Loader2 className="animate-spin mx-auto h-6 w-6 text-muted-foreground" />
                            </TableCell>
                        </TableRow>
                    ) : users.length === 0 ? (
                        <TableRow>
                            <TableCell colSpan={7} className="text-center py-8 text-muted-foreground">
                                Aucun utilisateur trouvé.
                            </TableCell>
                        </TableRow>
                    ) : (
                        <>
                            {users.map((user) => (
                                <TableRow key={user.id}>
                                    <TableCell>{user.userCode}</TableCell>
                                    <TableCell>{user.prenom}</TableCell>
                                    <TableCell>{user.nom}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>
                                        <Badge variant={user.role === "ADMIN" ? "admin" : "auditor"}>
                                            {user.role}
                                        </Badge>
                                    </TableCell>
                                    <TableCell>
                                        <Badge variant={user.actif ? "success" : "destructive"}>
                                            {user.actif ? "Actif" : "Inactif"}
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
                                                <DropdownMenuItem>Profil</DropdownMenuItem>
                                                <DropdownMenuItem>Modifier</DropdownMenuItem>
                                                <DropdownMenuItem
                                                    onSelect={() => userService.toggleActif(user.id, !user.actif).then(fetchUsers)}
                                                >
                                                    {user.actif ? "Désactiver" : "Activer"}
                                                </DropdownMenuItem>
                                                <DropdownMenuSeparator />
                                                <DropdownMenuItem
                                                    variant="destructive"
                                                    onSelect={() => setUserToDelete(user)}
                                                >
                                                    Supprimer
                                                </DropdownMenuItem>
                                            </DropdownMenuContent>
                                        </DropdownMenu>
                                    </TableCell>
                                </TableRow>
                            ))}
                            {Array.from({ length: itemsPerPage - users.length }).map((_, i) => (
                                <TableRow key={`empty-${i}`} className="pointer-events-none h-[49px]">
                                    {Array.from({ length: 7 }).map((_, j) => (
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

            <AlertDialog open={!!userToDelete} onOpenChange={(open) => !open && setUserToDelete(null)}>
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Supprimer {userToDelete?.prenom} {userToDelete?.nom} ?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            Cette action est irréversible. L'utilisateur sera définitivement supprimé.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Annuler</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(userToDelete)}>
                            Supprimer
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </>
    )
}