"use client";
import { useState, useEffect } from "react";
import { TablePagination } from "@/components/tables-pagination"
import {initialUsers} from "@/lib/data"
import { MoreHorizontalIcon } from "lucide-react";
import { Badge } from "@/components/ui/badge";
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

export function UserTable( { search = "", role = "all", sort = "id", order = "asc" }) {

    const [users, setUsers] = useState(initialUsers);
    const [userToDelete, setUserToDelete] = useState(null);
    const [currentPage, setCurrentPage] = useState(1)
    const [itemsPerPage, setItemsPerPage] = useState(DEFAULT_ITEMS_PER_PAGE)

      useEffect(() => {
        setCurrentPage(1)
    }, [search, role, sort, order])

    const filtered = users
        .filter((u) => {
            const matchSearch = search === "" ||
                u.firstName.toLowerCase().includes(search.toLowerCase()) ||
                u.lastName.toLowerCase().includes(search.toLowerCase()) ||
                u.email.toLowerCase().includes(search.toLowerCase())

            const matchRole = role === "all" || u.role.toLowerCase() === role.toLowerCase()

            return matchSearch && matchRole
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

    function handleDelete(user) {
        setUsers((prev) => prev.filter((u) => u.id !== user.id));
        setUserToDelete(null);
    }

    return (
        <>

            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>
                            <Badge>Identifiant</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Prénom</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Nom</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>
                                Email
                            </Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Rôle</Badge>
                        </TableHead>
                        <TableHead className="text-right">
                            Action
                        </TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {paginated.map((user) => (
                        <TableRow key={user.id}>
                            <TableCell>{user.id}</TableCell>
                            <TableCell>{user.firstName}</TableCell>
                            <TableCell>{user.lastName}</TableCell>
                            <TableCell>{user.email}</TableCell>
                            <TableCell><Badge variant={user.role.toLowerCase() == "admin" ? "admin" : "auditor"}>{user.role}</Badge></TableCell>

                            <TableCell className="text-right">
                                <DropdownMenu>
                                    <DropdownMenuTrigger asChild>
                                        <Button variant="ghost" size="icon" className="size-8">
                                            <MoreHorizontalIcon />
                                            <span className="sr-only">Open menu</span>
                                        </Button>
                                    </DropdownMenuTrigger>

                                    <DropdownMenuContent align="end">
                                        <DropdownMenuItem>Profil</DropdownMenuItem>
                                        <DropdownMenuItem>Modifier</DropdownMenuItem>
                                        <DropdownMenuSeparator />
                                        <DropdownMenuItem variant="destructive" onSelect={() => setUserToDelete(user)}>
                                            Supprimer
                                        </DropdownMenuItem>
                                    </DropdownMenuContent>
                                </DropdownMenu>
                            </TableCell>
                        </TableRow>
                    ))}
                    {Array.from({ length: itemsPerPage - paginated.length }).map((_, i) => (
                        <TableRow key={`empty-${i}`} className="pointer-events-none h-[49px]">
                            {Array.from({ length: 6 }).map((_, j) => (
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
                open={!!userToDelete}
                onOpenChange={(open) => !open && setUserToDelete(null)}
            >
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Delete {userToDelete?.firstName} {userToDelete?.lastName}?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            This will permanently remove this user. This action cannot be undone.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Cancel</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(userToDelete)}>
                            Delete
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </>
    )
}
