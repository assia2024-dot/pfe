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
    TableHead, TableHeader, TableRow,TableCaption 
} from "@/components/ui/table";

const initialMissions = [
    {
        id: 1,
        titre: "Audit Qualité Q1",
        magasin: "ElectroPlanet Casablanca Maarif",
        auditeur: "Karim Benali",
        dateDebut: "2024-01-15",
        dateFin: "2024-01-20",
        statut: "Terminée",
    },
    {
        id: 2,
        titre: "Contrôle Stock Hiver",
        magasin: "ElectroPlanet Rabat Agdal",
        auditeur: "Fatima Zahra Idrissi",
        dateDebut: "2024-02-01",
        dateFin: "2024-02-05",
        statut: "En cours",
    },
    {
        id: 3,
        titre: "Inspection Sécurité",
        magasin: "ElectroPlanet Marrakech Guéliz",
        auditeur: null,
        dateDebut: "2024-02-10",
        dateFin: "2024-02-15",
        statut: "En attente",
    },
    {
        id: 4,
        titre: "Audit Service Client",
        magasin: "ElectroPlanet Fès Atlas",
        auditeur: "Youssef Tazi",
        dateDebut: "2024-02-20",
        dateFin: "2024-02-25",
        statut: "En cours",
    },
    {
        id: 5,
        titre: "Vérification Affichage Prix",
        magasin: "ElectroPlanet Tanger Ibn Batouta",
        auditeur: null,
        dateDebut: "2024-03-01",
        dateFin: "2024-03-03",
        statut: "En attente",
    },
]

export function MissionsTable() {

    const [missions, setMissions] = useState(initialMissions);
    const [missionToDelete, setMissionToDelete] = useState(null);

    function handleDelete(mission) {
        setMissions((prev) => prev.filter((m) => m.id !== mission.id));
        setMissionToDelete(null);
    }

    return (
        <div>
            <Table>
                <TableCaption>Liste des missions d'audit récentes.</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead>
                            <Badge>Id</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Titre</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Magasin</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>
                                Auditeur
                            </Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Date Début</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Date Fin</Badge>
                        </TableHead>
                        <TableHead>
                            <Badge>Statut</Badge>
                        </TableHead>
                        <TableHead className="text-right">
                            Action
                        </TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {missions.map((mission) => (
                        <TableRow key={mission.id}>
                            <TableCell>{mission.id}</TableCell>
                            <TableCell>{mission.titre}</TableCell>
                            <TableCell>{mission.magasin}</TableCell>
                            <TableCell>{mission.auditeur ?? "-"}</TableCell>
                            <TableCell>{mission.dateDebut}</TableCell>
                            <TableCell>{mission.dateFin}</TableCell>
                            <TableCell>
                                <Badge variant={mission.statut === "En attente" ? "en_attente" : mission.statut === "En cours" ? "en_cours" : "terminee"}>
                                    {mission.statut}
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
                                        <DropdownMenuItem>Edit</DropdownMenuItem>
                                        <DropdownMenuItem>Duplicate</DropdownMenuItem>
                                        <DropdownMenuSeparator />
                                        <DropdownMenuItem variant="destructive" onSelect={() => setMissionToDelete(mission)}>
                                            Delete
                                        </DropdownMenuItem>
                                    </DropdownMenuContent>
                                </DropdownMenu>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>

            <AlertDialog
                open={!!missionToDelete}
                onOpenChange={(open) => !open && setMissionToDelete(null)}
            >
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Delete {missionToDelete?.titre}?
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            This will permanently remove this user. This action cannot be undone.
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>Cancel</AlertDialogCancel>
                        <AlertDialogAction onClick={() => handleDelete(missionToDelete)}>
                            Delete
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    )
}


//<TableCell><Badge variant={mission.statut.toLowerCase() == "admin" ? "admin" : "auditor"}>{user.role}</Badge></TableCell>
