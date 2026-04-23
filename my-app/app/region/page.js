import {AddNewRegion} from "@/components/regionForms/add-new-region"
import {RegionsTable} from "@/components/region-table"


const Region = () =>{
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <AddNewRegion />
            </div>
            <div className="overflow-x-auto rounded-md ">
                <RegionsTable />
            </div>
        </div>
    )
}

export default Region;