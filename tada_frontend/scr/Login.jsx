import {useContext, useRef, useState} from "react";
import {Api} from "./Context.js";
import {basic, anonJson} from "./Headers.js";

export default function Login({auth, setAuth}) {
    const api = useContext(Api);
    const [createAccount, setCreateAccount] = useState(false);
    const name = useRef(undefined);
    const password = useRef(undefined);


    function logOut() {
        fetch(api + "/users/logout", {method: "POST", headers: basic(auth)}).then(response => {
            if (!response.ok) throw new Error(response.statusText);
        }).then(() => {
            setAuth({name: null, password: null, loggedIn: false});
        });
    }

    function logIn() {
        const newAuth = {name: name.current.value, password: password.current.value}
        fetch(api + "/users/login", {method: "POST", headers: basic(newAuth)}).then(response => {
            if (response.ok) return response.json();
            else throw new Error(response.statusText);
        }).then(() => {
            newAuth.loggedIn = true;
            setAuth(newAuth);
        });
    }

    function register() {
        const newAuth = {name: name.current.value, password: password.current.value};
        console.log(newAuth);
        fetch(api + "/users", {
            method: "POST", headers: anonJson(),
            body: JSON.stringify(newAuth)
        }).then(response => {
            console.log(response);
            if (response.ok) return response.json();
            else throw new Error(response.statusText);
        }).then(() => {
            newAuth.loggedIn = true;
            setAuth(newAuth)
        });
    }

    if (auth.loggedIn) {
        return <>
            <div id="logout_page">
                <div><h3>Halli Hallo {auth.name}! Welcome to taDa App 🫨</h3>
                    <img src="./src/asset/day.jpg" alt="day_image"/></div>
                <button onClick={logOut}>Log out</button>
            </div>
        </>;
    } else {
        return <>
            <div>
                <h4>Be a part of taDa's community</h4>
                <input id="new-account" type="checkbox" defaultValue={createAccount}
                       onChange={e => setCreateAccount(e.target.checked)}/>
                <label htmlFor="new-account">YAY ! I want to be a member 🤩</label>
            </div>
            {createAccount ? <><h1>SIGN UP</h1></> : <><h1>SIGN IN</h1></>}
            <div>
                <div>
                    <label htmlFor="name">User name</label>
                    <input id="name" ref={name}/>
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" ref={password}/>
                </div>
            </div>

            {createAccount ? <>
                <button onClick={register}>Start my journey 🥳</button>
            </> : <button onClick={logIn}>Sign in</button>}
        </>;
    }
}