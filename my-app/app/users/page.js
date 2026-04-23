import { UserTable } from "@/components/user-table"
import { AddNewUser } from "@/components/UserForms/add-new-user"

const users = () => {
    return (
        <>
            <div className="flex justify-end mb-4">
                <AddNewUser />
            </div>
            <UserTable />
        </>
    )
}

export default users;

