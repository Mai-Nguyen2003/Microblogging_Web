import {useContext, useEffect, useState, useRef} from "react";
import {Api} from "./Context.js";
import {basic,basicJson} from "./Headers.js";
import PostOfUser from "./PostOfUser.jsx";

export default function Profile({auth}) {
    const api = useContext(Api);
    const [user, setUser] = useState({
        name: null,
        fullName: null,
        email: null,
        biography: null,
        posts: [],
        followers: [],
        followings: []
    })


    const newFullName = useRef(undefined);
    const newEmail = useRef(undefined);
    const newBiography = useRef(undefined);
    const [userPosts,setUserPosts] = useState([]);

    useEffect(() => {
        fetch(api + "/users", {method: "GET", headers: basic(auth)}
        )
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setUser(result.find(u => u.name === auth.name));
            });
    }, [api,auth]);


    useEffect(() => {
        fetch(api + "/posts", {method: "GET", headers: basic(auth)}
        )
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setUserPosts(result.reverse().slice(0,20));

            });
    }, [api,auth]);


    function  updateProfile(){
        const newProfile = {fullName: newFullName.current.value,email: newEmail.current.value,biography: newBiography.current.value};
        fetch(api + "/users",{method: "PUT", headers: basicJson(auth),body: JSON.stringify(newProfile)})
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setUser(result);
            });

    }

    return <>
        <div className="grid">
            <h1>{user.name}</h1>
            <table>
                <thead>
                <tr>
                    <th>Posts</th>
                    <th>Followers</th>
                    <th>Followings</th>
                </tr>
                </thead>

                <tbody>
                <tr>
                    <th>{userPosts.length}</th>
                    <th>{user.followers.length}</th>
                    <th>{user.followings.length}</th>
                </tr>

                </tbody>
            </table>
        </div>
        <form>
            <p>
                <label htmlFor="fullName">Full Name</label>
                <input id="fullName" defaultValue={user.fullName} ref={newFullName} type="text"/>
            </p>
            <div>
                <label htmlFor="email">Email</label>
                <input id="email" defaultValue={user.email} ref={newEmail}/>
            </div>
            <div>
                <label htmlFor="biography">Biography</label>
                <textarea name="biography" rows="3" defaultValue={user.biography} ref={newBiography}/>
            </div>
        </form>
        <button onClick={updateProfile}>Update profile
        </button>
        <div>
            <h3>Posts</h3>
            <ul>{userPosts.map(p =><li className="post_item"><PostOfUser key={p.id} auth={auth} post={p} setPosts={setUserPosts} posts={userPosts} view="Profile"/></li>)}</ul>
        </div>
    </>

}