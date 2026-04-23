import {Button} from "@/components/ui/button"
import {ElementsAuditTable} from "@/components/element-table"
import { AddNewElement } from "@/components/elementForms/add-new-element"

const ElementAduit = () =>{
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <AddNewElement />
            </div>
            <div className="overflow-x-auto rounded-md ">
                <ElementsAuditTable />
            </div>
        </div>
    )
}

export default ElementAduit;