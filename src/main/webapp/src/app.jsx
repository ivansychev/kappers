import React from 'react'
import {useRoutes} from 'hookrouter';

import HomePage from './homePage'
import AboutPage from './aboutPage'
import ContactsPage from './contactsPage'
import SignInPage from './signInPage'
import SignUpPage from './signUpPage'
import NotFoundPage from './notFoundPage'

const routes = {
    '/': () => (user) => <HomePage user={user} />,
    '/about': () => () => <AboutPage />,
    '/contacts': () => () => <ContactsPage />,
    '/sign-in': () => () => <SignInPage />,
    '/sign-up': () => () => <SignUpPage />
};

const App = ({ user }) => {
    const routeResult = useRoutes(routes)
    return (
        <div className="App">
            <a href="/">Users Page</a><br />
            <a href="/about">About Page</a><br />
            <a href="/contacts">Contacts Page</a><br />
            <a href="/sign-in">Sign-In</a><br />
            <a href="/sign-up">Sign-Up</a><br />
            {routeResult ? routeResult(user) : <NotFoundPage />}
        </div>
    )
};

export default App