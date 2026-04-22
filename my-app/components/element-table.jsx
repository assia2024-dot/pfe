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

const initialElements = [
    { id: 1, nom: "Propreté du sol", description: "Vérifier l'état de propreté du sol dans tout le magasin", statut: "Actif" },
    { id: 2, nom: "Qualité de l'éclairage", description: "Vérifier que l'éclairage est suffisant et fonctionnel", statut: "Actif" },
    { id: 3, nom: "État du parking", description: "Vérifier la propreté et l'organisation du parking", statut: "Actif" },
    { id: 4, nom: "Façade du magasin", description: "Vérifier l'état de la façade et de l'enseigne", statut: "Inactif" },
    { id: 5, nom: "Disposition des articles", description: "Vérifier la mise en place et l'organisation des produits", statut: "Actif" },
    { id: 6, nom: "Mise en place des promotions", description: "Vérifier l'affichage et la visibilité des offres promotionnelles", statut: "Actif" },
    { id: 7, nom: "Tenue vestimentaire du personnel", description: "Vérifier que le personnel porte la tenue réglementaire", statut: "Actif" },
    { id: 8, nom: "Proactivité du personnel", description: "Évaluer la capacité du personnel à aborder les clients", statut: "Inactif" },
    { id: 9, nom: "File d'attente en caisse", description: "Vérifier la gestion des files d'attente aux caisses", statut: "Actif" },
    { id: 10, nom: "Conseil vendeur", description: "Évaluer la qualité des conseils donnés par les vendeurs", statut: "Actif" },
]

export function ElementsAuditTable() {
    const [elements, setElements] = useState(initialElements);
    const [elementToDelete, setElementToDelete] = useState(null);

    function handleDelete(element) {
        setElements((prev) => prev.filter((e) => e.id !== element.id));
        setElementToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableCaption>Liste des éléments d'audit.</TableCaption>
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
                    {elements.map((element) => (
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
                </TableBody>
                <TableFooter>
                    <TableRow>
                        <TableCell colSpan={6}>Total éléments : {elements.length}</TableCell>
                    </TableRow>
                </TableFooter>
            </Table>

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