import {useState} from "react";
import Login from "./Login.jsx";
import Home from "./Home.jsx";
import Search from "./Search.jsx";
import Post from "./Post.jsx";
import Profile from "./Profile.jsx";


export default function TaDaFrontend() {
    const [auth, setAuth] = useState({name: null, password: null, loggedIn: false});
    const [view, setView] = useState("welcome");

    return <>
        <header>
            <div><h1 id="logo">taDa</h1></div>
            <input type="checkbox" id="check"/>
            <label htmlFor="check" className="icons">
                <i className="bxr bx-menu" id="menu-icon"/>
                <i className='bxr  bx-x' id="close-icon"/>
            </label>
            <nav className="navbar">
                <ul>
                    {auth.name === null && <li><a href="#" className={view === "welcome" ? "current" : "default"}
                                                  onClick={() => setView("welcome")}>Welcome</a></li>}
                    {auth.name !== null && <li><a href="#" className={view === "home" ? "current" : "default"}
                                                  onClick={() => setView("home")}>Home</a></li>}
                    {auth.name !== null && <li><a href="#" className={view === "search" ? "current" : "default"}
                                                  onClick={() => setView("search")}>Search</a></li>}
                    {auth.name !== null && <li><a href="#" className={view === "post" ? "current" : "default"}
                                                  onClick={() => setView("post")}>Post</a></li>}
                    {auth.name !== null && <li><a href="#" className={view === "profile" ? "current" : "default"}
                                                  onClick={() => setView("profile")}>Profile</a></li>}

                    <li><a href="#" className={view === "login" ? "current" : "default"}
                           onClick={() => setView("login")}>
                        {auth.loggedIn ? "Log out" : "Log in"}</a></li>
                </ul>
            </nav>

        </header>
        <main>
        {view === "welcome" ?
                <div id="welcome_page">
                    <h2>PEEK - A - BOO</h2>
                    <img src="/src/asset/bunny.jpg" alt="bunny_image"/>
                    <p>Don't hesitate to express your emotions</p>
                    <p>Join Us !!!</p>
                </div>
                : view === "home" ? <Home auth={auth}/>
                    : view === "search" ? <Search auth={auth}/>
                        : view === "post" ? <Post auth={auth}/>
                            : view === "profile" ? <Profile auth={auth}/>
                                : <Login auth={auth} setAuth={setAuth}/>
            }
        </main>

    </>;
}