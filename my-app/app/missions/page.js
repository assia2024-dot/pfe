import { Button } from "@/components/ui/button"
import { MissionsTable } from "@/components/mission-table";

const Missions = () => {
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <Button>Ajouter</Button>
            </div>
            <div className="overflow-x-auto rounded-md ">
                <MissionsTable />
            </div>
        </div>
    )
}

export default Missions;