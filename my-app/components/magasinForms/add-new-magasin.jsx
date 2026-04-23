import { Button } from "@/components/ui/button"
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"

import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

import { Field, FieldGroup, FieldLabel } from "@/components/ui/field"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"



function SelectCity() {
    return (
        <Select>
            <SelectTrigger className="w-full">
                <SelectValue placeholder="Sélectionner une ville" />
            </SelectTrigger>

            <SelectContent>
                <SelectGroup>
                    <SelectLabel>Villes du Maroc</SelectLabel>

                    <SelectItem value="casablanca">Casablanca</SelectItem>
                    <SelectItem value="rabat">Rabat</SelectItem>
                    <SelectItem value="marrakech">Marrakech</SelectItem>
                    <SelectItem value="fes">Fès</SelectItem>
                    <SelectItem value="tanger">Tanger</SelectItem>
                    <SelectItem value="agadir">Agadir</SelectItem>
                    <SelectItem value="meknes">Meknès</SelectItem>
                    <SelectItem value="oujda">Oujda</SelectItem>
                    <SelectItem value="tetouan">Tétouan</SelectItem>
                    <SelectItem value="kenitra">Kénitra</SelectItem>
                </SelectGroup>
            </SelectContent>
        </Select>
    )
}

export function AddNewMagasin() {
    return (
        <Dialog>
            <form>
                <DialogTrigger asChild>
                    <Button >Ajouter un magasin</Button>
                </DialogTrigger>
                <DialogContent className="sm:max-w-sm">
                    <DialogHeader>
                        <DialogTitle>Créer un nouveau magasin</DialogTitle>
                        <DialogDescription>
                            Renseignez les informations du nouveau magasin ci-dessous, puis cliquez sur « Enregistrer » pour valider.
                        </DialogDescription>
                    </DialogHeader>

                    <FieldGroup>
                        <Field>
                            <FieldLabel htmlFor="first-name">Nom
                                <span className="text-destructive">*</span>
                            </FieldLabel>
                            <Input id="first-name" placeholder="Nom du magasin" required />
                        </Field>
                        <Field>
                            <FieldLabel htmlFor="first-name">Adresse
                                <span className="text-destructive">*</span>
                            </FieldLabel>
                            <Input id="first-name" placeholder="Ex: 10 Avenue Mohammed V, Casablanca" required />
                        </Field>
                        <FieldGroup>
                            <Field>
                                <FieldLabel htmlFor="Magasin">Ville
                                    <span className="text-destructive">*</span>
                                </FieldLabel>
                                <SelectCity />
                            </Field>
                            <Field>
                                <FieldLabel>Status</FieldLabel>
                                <RadioGroup defaultValue="Actif" className="w-fit">
                                    <div className="flex items-center gap-3">
                                        <RadioGroupItem value="Actif" id="r1" />
                                        <Label htmlFor="r1">Actif</Label>
                                    </div>
                                    <div className="flex items-center gap-3">
                                        <RadioGroupItem value="Inactif" id="r2" />
                                        <Label htmlFor="r2">Inactif</Label>
                                    </div>
                                </RadioGroup>
                            </Field>
                        </FieldGroup>
                    </FieldGroup>


                    <DialogFooter>
                        <DialogClose asChild>
                            <Button variant="outline">Annuler</Button>
                        </DialogClose>
                        <Button type="submit">Enregistrer</Button>
                    </DialogFooter>
                </DialogContent>
            </form>
        </Dialog >
    )
}
