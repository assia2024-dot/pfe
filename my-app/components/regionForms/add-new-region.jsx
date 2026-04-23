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



export function AddNewRegion() {
  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          <Button >Ajouter une région</Button>
        </DialogTrigger>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Créer une nouvelle région</DialogTitle>
            <DialogDescription>
              Renseignez les informations de la nouvelle région ci-dessous, puis cliquez sur « Enregistrer » pour valider.
            </DialogDescription>
          </DialogHeader>

          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="first-name">Nom
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input id="first-name" placeholder="Nom de la région" required />
            </Field>
            <Field>
              <FieldLabel htmlFor="first-name">Code
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input id="first-name" placeholder="99999-9999" required />
            </Field>
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
