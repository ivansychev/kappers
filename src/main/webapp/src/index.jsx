import React from 'react'
import ReactDOM from 'react-dom'

import App from './app'

import normalized_style from 'normalize.css'
import style from './style.scss'

const user = {
    roles: ["user"],
    rights: ["can_view_articles"]
};

const admin = {
    roles: ["user", "admin"],
    rights: ["can_view_articles", "can_view_users"]
};

ReactDOM.render(<App user={user} />, document.getElementById('root'))