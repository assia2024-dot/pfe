import { UserTable } from "@/components/user-table"
import {Button } from "@/components/ui/button"

const users = () =>{
    return(
        <>
<div className="flex justify-end mb-4">
  <Button>ajoute</Button>
</div>
<UserTable />
</>
    )
}

export default users;