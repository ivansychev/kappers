import React from 'react'
import {hasRole, isAllowed} from "./auth";

const HomePage = ({user}) => (
    <div>
        This is a home page. Roles:
        <div>
            {hasRole(user, ["user"]) && <p>Is User</p>}
            {hasRole(user, ["admin"]) && <p>Is Admin</p>}
            {isAllowed(user, ["can_view_articles"]) && <p>Can view Articles</p>}
            {isAllowed(user, ["can_view_users"]) && <p>Can view Users</p>}
        </div>
    </div>
)

export default HomePage