import {Button} from "@/components/ui/button"
import {ElementsAuditTable} from "@/components/element-table"

const ElementAduit = () =>{
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <Button>Ajouter</Button>
            </div>
            <div className="overflow-x-auto rounded-md ">
                <ElementsAuditTable />
            </div>
        </div>
    )
}

export default ElementAduit;