import React from 'react'

const SignUpPage = () => (
    <form>
        <label>
            Username: <input type="text" name="username" />
        </label>
        <br/>
        <label>
            Password: <input type="text" name="password" />
        </label>
        <br/>
        <label>
            Name: <input type="text" name="name" />
        </label>
        <br/>
        <label>
            Role: <input type="text" name="role" />
        </label>
        <br/>
        <input type="submit" value="Отправить" />
    </form>
)

export default SignUpPage