"use client";
import { useState } from "react";
import {initialRegions} from "@/lib/data";
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
    TableFooter, TableHead, TableHeader, TableRow, TableCaption
} from "@/components/ui/table";


export function RegionsTable() {
    const [regions, setRegions] = useState(initialRegions);
    const [regionToDelete, setRegionToDelete] = useState(null);

    function handleDelete(region) {
        setRegions((prev) => prev.filter((r) => r.id !== region.id));
        setRegionToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableCaption>Liste des régions.</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead><Badge>Id</Badge></TableHead>
                        <TableHead><Badge>Nom</Badge></TableHead>
                        <TableHead><Badge>Code</Badge></TableHead>
                        <TableHead className="text-right">Action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {regions.map((region) => (
                        <TableRow key={region.id}>
                            <TableCell>{region.id}</TableCell>
                            <TableCell>{region.nom}</TableCell>
                            <TableCell>{region.code}</TableCell>
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
                                            onSelect={() => setRegionToDelete(region)}
                                        >
                                            Supprimer
                                        </DropdownMenuItem>
                                    </DropdownMenuContent>
                                </DropdownMenu>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
                <TableFooter>
                    <TableRow>
                        <TableCell colSpan={5}>Total régions : {regions.length}</TableCell>
                    </TableRow>
                </TableFooter>
            </Table>

            <AlertDialog
                open={!!regionToDelete}
                onOpenChange={(open) => !open && setRegionToDelete(null)}
            >
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Supprimer {regionToDelete?.nom}?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            Cette action est irréversible. Cette région sera définitivement supprimée.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Annuler</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(regionToDelete)}>
                            Supprimer
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    )
}