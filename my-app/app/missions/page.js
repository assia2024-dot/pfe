import { MissionsTable } from "@/components/mission-table";
import { AddNewMission } from "@/components/missionForms/add-new-mission";

const Missions = () => {
    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-end">
                <AddNewMission />
            </div>
            <div className="overflow-x-auto rounded-md ">
                <MissionsTable />
            </div>
        </div>
    )
}

export default Missions;