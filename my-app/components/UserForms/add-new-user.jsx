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
import { Field, FieldGroup, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"


export function AddNewUser() {
  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          <Button >Ajouter un utilisateur</Button>
        </DialogTrigger>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Ajouter un nouvel utilisateur</DialogTitle>
            <DialogDescription>
              Remplissez les informations du nouvel utilisateur ci-dessous, puis cliquez sur « Enregistrer » pour valider.
            </DialogDescription>
          </DialogHeader>
          <FieldGroup className="grid max-w-sm grid-cols-2">
            <Field>
              <FieldLabel htmlFor="first-name">Prénom
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input id="first-name" placeholder="Assia" required />
            </Field>
            <Field>
              <FieldLabel htmlFor="last-name">Nom
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input id="last-name" placeholder="DAHIR" required />
            </Field>
          </FieldGroup>
          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="fieldgroup-email">Email
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input
                id="fieldgroup-email"
                type="email"
                placeholder="name@example.com"
                required
              />
            </Field>
          </FieldGroup>
          <FieldLabel htmlFor="fieldgroup-email">Rôle
            <span className="text-destructive">*</span>
          </FieldLabel>
          <FieldGroup>
            <RadioGroup defaultValue="Auditeur" className="w-fit">
              <div className="flex items-center gap-3">
                <RadioGroupItem value="Administateur" id="r1" />
                <Label htmlFor="r1">Administateur</Label>
              </div>
              <div className="flex items-center gap-3">
                <RadioGroupItem value="Auditeur" id="r2" />
                <Label htmlFor="r2">Auditeur</Label>
              </div>
            </RadioGroup>
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
