import { AddNewMagasin } from "@/components/magasinForms/add-new-magasin";
import {MagasinsTable} from "@/components/magasin-table"
import { Input } from "@/components/ui/input"



const Magasin = () =>{
    return (
        
        <div className="flex flex-col gap-4">
            <p className="text-xl text-muted-foreground">Liste des magasins ElectroPlanet</p>
            <div className="flex items-center justify-between gap-4">
                <Input placeholder="Rechercher un magasin..." className="max-w-sm" />
                <AddNewMagasin />
            </div>
            <div className="overflow-x-auto rounded-md ">
                <MagasinsTable />
            </div>
        </div>
        
    )
}

export default Magasin;