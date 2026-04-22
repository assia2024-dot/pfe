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

const initialMagasins = [
    { id: 1, nom: "ElectroPlanet Maarif", adresse: "Centre Commercial Maarif, Bd Massira", ville: "Casablanca", actif: true },
    { id: 2, nom: "ElectroPlanet Hay Riad", adresse: "Avenue Al Amir Moulay Abdallah, Hay Riad", ville: "Rabat", actif: true },
    { id: 3, nom: "ElectroPlanet Agdal", adresse: "Rue Patrice Lumumba, Agdal", ville: "Rabat", actif: true },
    { id: 4, nom: "ElectroPlanet Guéliz", adresse: "Avenue Mohammed V, Guéliz", ville: "Marrakech", actif: true },
    { id: 5, nom: "ElectroPlanet Atlas", adresse: "Route de Fès, Quartier Atlas", ville: "Fès", actif: true },
    { id: 6, nom: "ElectroPlanet Tanger City Mall", adresse: "Route de Rabat, Tanger City Mall", ville: "Tanger", actif: true },
    { id: 7, nom: "ElectroPlanet Agadir", adresse: "Avenue du Prince Héritier, Talborjt", ville: "Agadir", actif: false },
    { id: 8, nom: "ElectroPlanet Meknès", adresse: "Avenue des FAR, Hamria", ville: "Meknès", actif: true },
    { id: 9, nom: "ElectroPlanet Oujda", adresse: "Boulevard Mohammed VI", ville: "Oujda", actif: true },
    { id: 10, nom: "ElectroPlanet Kénitra", adresse: "Avenue Mohammed Diouri", ville: "Kénitra", actif: false },
]

export function MagasinsTable() {
    const [magasins, setMagasins] = useState(initialMagasins);
    const [magasinToDelete, setMagasinToDelete] = useState(null);

    function handleDelete(magasin) {
        setMagasins((prev) => prev.filter((m) => m.id !== magasin.id));
        setMagasinToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableCaption>Liste des magasins ElectroPlanet.</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead><Badge>Id</Badge></TableHead>
                        <TableHead><Badge>Nom</Badge></TableHead>
                        <TableHead><Badge>Adresse</Badge></TableHead>
                        <TableHead><Badge>Ville</Badge></TableHead>
                        <TableHead><Badge>Actif</Badge></TableHead>
                        <TableHead className="text-right">Action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {magasins.map((magasin) => (
                        <TableRow key={magasin.id}>
                            <TableCell>{magasin.id}</TableCell>
                            <TableCell>{magasin.nom}</TableCell>
                            <TableCell>{magasin.adresse}</TableCell>
                            <TableCell>{magasin.ville}</TableCell>
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
                </TableBody>
                <TableFooter>
                    <TableRow>
                        <TableCell colSpan={6}>Total magasins : {magasins.length}</TableCell>
                    </TableRow>
                </TableFooter>
            </Table>

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