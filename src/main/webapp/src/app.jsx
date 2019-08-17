import React from 'react'
import {useRoutes, A} from 'hookrouter';

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
            <A href="/">Users Page</A><br />
            <A href="/about">About Page</A><br />
            <A href="/contacts">Contacts Page</A><br />
            <A href="/sign-in">Sign-In</A><br />
            <A href="/sign-up">Sign-Up</A><br />
            {routeResult ? routeResult(user) : <NotFoundPage />}
        </div>
    )
};

export default App