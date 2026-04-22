import {Button} from "@/components/ui/button"
import {MagasinsTable} from "@/components/magasin-table"

const Magasin = () =>{
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <Button>Ajouter</Button>
            </div>
            <div className="overflow-x-auto rounded-md ">
                <MagasinsTable />
            </div>
        </div>
    )
}

export default Magasin;