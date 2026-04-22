import {Button} from "@/components/ui/button"
import {RegionsTable} from "@/components/region-table"

const Region = () =>{
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <Button>Ajouter</Button>
            </div>
            <div className="overflow-x-auto rounded-md ">
                <RegionsTable />
            </div>
        </div>
    )
}

export default Region;