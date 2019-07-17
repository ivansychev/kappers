import React, { Component } from 'react'
import ReactDOM from 'react-dom'

import style from './style.scss'

class App extends Component{
    render(){
        return(
            <div>Hello world</div>
        )
    }
}

const rootElement = document.getElementById('root')
ReactDOM.render(<App />, rootElement)