import { Input } from "@/components/ui/input"
import { ElementsAuditTable } from "@/components/element-table"
import { AddNewElement } from "@/components/elementForms/add-new-element"

const ElementAduit = () => {
    return (
        <div className="flex flex-col gap-4">
            <p className="text-xl text-muted-foreground">Liste des éléments d'audit</p>
            <div className="flex items-center justify-between gap-4">
                <Input placeholder="Rechercher un élément d&apos;audit..." className="max-w-sm" />
                <AddNewElement />
            </div>
            <div className="overflow-x-auto rounded-md ">
                <ElementsAuditTable />
            </div>

        </div>
    )
}

export default ElementAduit;

