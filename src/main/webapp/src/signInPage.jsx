import React from 'react'
import axios from 'axios'

const handleOnSubmit = (e) => {
    e.preventDefault();
    axios({
        method: 'POST',
        url: 'http://localhost:8080/login',
        data: {
            userName: e.target.userName.value,
            password: e.target.password.value
        }
    }).then(function (response) {
        console.log("response=" + JSON.stringify(response));
        if (response.data) {
            const token
                = window.btoa(e.target.userName.value + ':' + e.target.password.value);
            const userData = {
                userName: e.target.userName.value,
                authData: token
            }
            window.sessionStorage.setItem(
                'userData', JSON.stringify(userData)
            );
            console.log("Autorized");
            console.log("userData = " + JSON.stringify(userData));
        } else {
            alert("Authentication failed.")
        }
    }).catch(function (error) {
        alert("Authentication failed.");
        console.log("error response=" + JSON.stringify(error));
    });
}

const SignInPage = () => (
    <form onSubmit={handleOnSubmit}>
        <label>
            Username: <input type="text" name="userName" />
        </label>
        <br/>
        <label>
            Password: <input type="text" name="password" />
        </label>
        <br/>
        <input type="submit" value="Submit"/>
    </form>
)

export default SignInPage