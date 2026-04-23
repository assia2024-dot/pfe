"use client"

import * as React from "react"
import { format } from "date-fns"
import { Calendar } from "@/components/ui/calendar"
import { Button } from "@/components/ui/button"
import { fr } from "date-fns/locale"

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

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
import { Input } from "@/components/ui/input"





function SelectAuditeur() {
  return (
    <Select>
      <SelectTrigger className="w-full">
        <SelectValue placeholder="Sélectionner un auditeur" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Auditeurs</SelectLabel>
          <SelectItem value="Auditeur1">Auditeur1</SelectItem>
          <SelectItem value="Auditeur2">Auditeur2</SelectItem>
          <SelectItem value="Auditeur3">Auditeur3</SelectItem>
          <SelectItem value="Auditeur4">Auditeur4</SelectItem>
          <SelectItem value="Auditeur5">-</SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  )
}

function SelectMagasin() {
  return (
    <Select>
      <SelectTrigger className="w-full">
        <SelectValue placeholder="Sélectionner un Magasin" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Magasins</SelectLabel>
          <SelectItem value="ElectroPlanet Maarif">ElectroPlanet Maarif</SelectItem>
          <SelectItem value="Auditeur2">ElectroPlanet Hay Riad</SelectItem>
          <SelectItem value="ElectroPlanet Agdal">ElectroPlanet Agdal</SelectItem>
          <SelectItem value="ElectroPlanet Guéliz">ElectroPlanet Guéliz</SelectItem>
          <SelectItem value="ElectroPlanet Meknès">ElectroPlanet Meknès</SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  )
}


function DatePicker() {
  const [date, setDate] = React.useState()
  const [open, setOpen] = React.useState(false)

  return (
    <Field className="mx-auto w-44">
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            id="date-picker-simple"
            className="justify-start font-normal"
          >
            {date ? format(date, "PPP", { locale: fr }) : <span>Choisir une date</span>}
          </Button>
        </PopoverTrigger>

        <PopoverContent className="w-auto p-0" align="start">
          <Calendar
            mode="single"
            selected={date}
            onSelect={(value) => {
              setDate(value)
              setOpen(false)
            }}
            defaultMonth={date || new Date()}
          />
        </PopoverContent>
      </Popover>
    </Field>
  )
}


export function AddNewMission() {
  return (
    <Dialog>
      <form>
        <DialogTrigger asChild>
          <Button >Ajouter une mission</Button>
        </DialogTrigger>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Créer une nouvelle mission</DialogTitle>
            <DialogDescription>
              Remplissez les informations de la nouvelle mission ci-dessous, puis cliquez sur « Enregistrer » pour valider.            </DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="Titre">Titre
                <span className="text-destructive">*</span>
              </FieldLabel>
              <Input id="Titre" placeholder="titre de mission" required />
            </Field>
            <Field>
              <FieldLabel htmlFor="Magasin">Magasin
                <span className="text-destructive">*</span>
              </FieldLabel>
              <SelectMagasin />
            </Field>
            <Field>
              <FieldLabel htmlFor="Magasin">Auditeur
              </FieldLabel>
              <SelectAuditeur />
            </Field>
            <FieldGroup className="grid max-w-sm grid-cols-2">
              <Field>
                <FieldLabel htmlFor="date-picker-simple">Date de Début</FieldLabel>
                <DatePicker />
              </Field>
              <Field>
                <FieldLabel htmlFor="date-picker-simple">Date de Fin</FieldLabel>
                <DatePicker />
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

