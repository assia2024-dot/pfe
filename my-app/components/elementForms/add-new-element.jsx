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
import { Textarea } from "@/components/ui/textarea"


export function AddNewElement() {
  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          <Button >Ajouter un élément</Button>
        </DialogTrigger>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Créer un nouvel élément d&apos;audit</DialogTitle>
            <DialogDescription>
              Remplissez les informations du nouvel élément d&apos;audit ci-dessous, puis cliquez sur « Enregistrer » pour valider.
            </DialogDescription>
          </DialogHeader>

          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="first-name">Nom
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input id="first-name" placeholder="Nom de l&apos;élément d&apos;audit" required />
            </Field>
            <Field>
              <FieldLabel htmlFor="last-name">Description
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Textarea placeholder="Entrez une description ici." />
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
