"use client"

import { useState } from "react"
import { userService } from "@/services/userService"
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
import { Spinner } from "@/components/ui/spinner"

export function AddNewUser({ onSuccess }) {
  const [open, setOpen] = useState(false)
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState("")
  const [form, setForm] = useState({
    prenom: "",
    nom: "",
    email: "",
    password: "",
    telephone: "",
    region: "",
    role: "AUDITEUR",
  })

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
    setError("")
  }

  const handleSubmit = async () => {
    // Basic validation
    if (!form.prenom || !form.nom || !form.email || !form.password) {
      setError("Veuillez remplir tous les champs obligatoires.")
      return
    }

    setIsLoading(true)
    try {
      await userService.create({
        prenom: form.prenom,
        nom: form.nom,
        email: form.email,
        password: form.password,
        telephone: form.telephone || null,
        region: form.region || null,
        role: form.role,
      })
      setOpen(false)
      setForm({ prenom: "", nom: "", email: "", password: "", telephone: "", region: "", role: "AUDITEUR" })
      onSuccess?.() // refresh the table
    } catch (err) {
      setError(err.response?.data?.message || "Erreur lors de la création de l'utilisateur.")
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button>Ajouter un utilisateur</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-sm">
        <DialogHeader>
          <DialogTitle>Ajouter un nouvel utilisateur</DialogTitle>
          <DialogDescription>
            Remplissez les informations du nouvel utilisateur ci-dessous, puis cliquez sur « Enregistrer » pour valider.
          </DialogDescription>
        </DialogHeader>

        {error && (
          <div className="rounded-lg bg-red-50 p-3 text-sm text-red-800 border border-red-200">
            {error}
          </div>
        )}

        <FieldGroup className="grid max-w-sm grid-cols-2">
          <Field>
            <FieldLabel htmlFor="prenom">Prénom <span className="text-destructive">*</span></FieldLabel>
            <Input id="prenom" name="prenom" placeholder="Assia" value={form.prenom} onChange={handleChange} />
          </Field>
          <Field>
            <FieldLabel htmlFor="nom">Nom <span className="text-destructive">*</span></FieldLabel>
            <Input id="nom" name="nom" placeholder="DAHIR" value={form.nom} onChange={handleChange} />
          </Field>
        </FieldGroup>

        <FieldGroup>
          <Field>
            <FieldLabel htmlFor="email">Email <span className="text-destructive">*</span></FieldLabel>
            <Input id="email" name="email" type="email" placeholder="name@example.com" value={form.email} onChange={handleChange} />
          </Field>
          <Field>
            <FieldLabel htmlFor="password">Mot de passe <span className="text-destructive">*</span></FieldLabel>
            <Input id="password" name="password" type="password" placeholder="••••••••" value={form.password} onChange={handleChange} />
          </Field>
          <Field>
            <FieldLabel htmlFor="telephone">Téléphone</FieldLabel>
            <Input id="telephone" name="telephone" placeholder="06XXXXXXXX" value={form.telephone} onChange={handleChange} />
          </Field>
          <Field>
            <FieldLabel htmlFor="region">Région</FieldLabel>
            <Input id="region" name="region" placeholder="Casablanca" value={form.region} onChange={handleChange} />
          </Field>
        </FieldGroup>

        <div>
          <FieldLabel>Rôle <span className="text-destructive">*</span></FieldLabel>
          <RadioGroup
            value={form.role}
            onValueChange={(val) => setForm({ ...form, role: val })}
            className="mt-2 w-fit"
          >
            <div className="flex items-center gap-3">
              <RadioGroupItem value="ADMIN" id="r1" />
              <Label htmlFor="r1">Administrateur</Label>
            </div>
            <div className="flex items-center gap-3">
              <RadioGroupItem value="AUDITEUR" id="r2" />
              <Label htmlFor="r2">Auditeur</Label>
            </div>
          </RadioGroup>
        </div>

        <DialogFooter>
          <DialogClose asChild>
            <Button variant="outline" disabled={isLoading}>Annuler</Button>
          </DialogClose>
          <Button onClick={handleSubmit} disabled={isLoading}>
            {isLoading ? <><Spinner /> Enregistrement...</> : "Enregistrer"}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}