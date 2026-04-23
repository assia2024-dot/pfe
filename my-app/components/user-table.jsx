"use client";
import { useState } from "react";
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
    TableHead, TableHeader, TableRow, TableCaption
} from "@/components/ui/table";

const initialUsers = [
    {
        id: 1,
        firstName: "Youssef",
        lastName: "El Amrani",
        email: "youssef.elamrani@example.ma",
        role: "admin",
    },
    {
        id: 2,
        firstName: "Salma",
        lastName: "Bennani",
        email: "salma.bennani@example.ma",
        role: "auditor",
    },
    {
        id: 3,
        firstName: "Omar",
        lastName: "Alaoui",
        email: "omar.alaoui@example.ma",
        role: "auditor",
    },
]

export function UserTable() {

    const [users, setUsers] = useState(initialUsers);
    const [userToDelete, setUserToDelete] = useState(null);

    function handleDelete(user) {
        setUsers((prev) => prev.filter((u) => u.id !== user.id));
        setUserToDelete(null);
    }

    return (
        <>
            <Table>
                <TableCaption>Liste des utilisateurs et leurs rôles.</TableCaption>
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
                    {users.map((user) => (
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
                </TableBody>
            </Table>

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
