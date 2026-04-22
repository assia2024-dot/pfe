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
    TableFooter, TableHead, TableHeader, TableRow, TableCaption
} from "@/components/ui/table";

const initialRegions = [
    { id: 1, nom: "Casablanca-Settat", code: "20000 - 28999" },
    { id: 2, nom: "Rabat-Salé-Kénitra", code: "10000 - 14999" },
    { id: 3, nom: "Marrakech-Safi", code: "40000 - 46999" },
    { id: 4, nom: "Fès-Meknès", code: "30000 - 35999"},
    { id: 5, nom: "Tanger-Tétouan-Al Hoceïma", code: "90000 - 93999" },
    { id: 6, nom: "Souss-Massa", code: "80000 - 85999" },
    { id: 7, nom: "Oriental", code: "60000 - 64999" },
    { id: 8, nom: "Béni Mellal-Khénifra", code: "23000 - 25999" },
    { id: 9, nom: "Drâa-Tafilalet", code: "45000 - 47999" },
    { id: 10, nom: "Laâyoune-Sakia El Hamra", code: "70000 - 72999" },
    { id: 11, nom: "Dakhla-Oued Ed-Dahab", code: "73000 - 73999" },
    { id: 12, nom: "Guelmim-Oued Noun", code: "81000 - 82999" },
]

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
                        <TableCell colSpan={3}>Total régions : {regions.length}</TableCell>
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