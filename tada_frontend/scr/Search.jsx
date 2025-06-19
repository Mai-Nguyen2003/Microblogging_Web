import {useContext, useState, useRef, useEffect} from "react";
import {Api} from "./Context.js";
import {basic} from "./Headers.js";

export default function Search({auth}) {
    const api = useContext(Api);
    const inputName = useRef(undefined);
    const [userList, setUserList] = useState([]);


    function findUserList(){
        fetch(api + "/users", {method: "GET", headers: basic(auth)}
    )
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error(response.statusText);
        })
        .then(result => {
            setUserList(result.filter(u => u.name.includes(inputName.current.value) && u.name !== auth.name));
        });
    }

    function FoundUser({user}) {
        const [isFollowing,setIsFollowing] = useState(false);
        const [isFollowed,setIsFollowed] = useState(false);
        useEffect(() => {
                fetch(api + "/users/"+user.name+"/follows", {method: "GET", headers: basic(auth)}
                )
                    .then(response => {
                        if (response.ok) return response.json();
                        else throw new Error(response.statusText);
                    })
                    .then(result => {
                        setIsFollowing(result);
                    });
        }, [api,auth]);
        useEffect(() => {
            fetch(api + "/users/"+user.name+"/followed", {method: "GET", headers: basic(auth)}
            )
                .then(response => {
                    if (response.ok) return response.json();
                    else throw new Error(response.statusText);
                })
                .then(result => {
                    setIsFollowed(result);
                });
        }, [api,auth]);

        function updateFollow(status){
            fetch(api + "/users/"+ user.name + "/follows", {method: status ? "POST" : "DELETE", headers: basic(auth)}
            )
                .then(response => {
                    if (!response.ok) throw new Error(response.statusText);
                })
                .then(() => {
                    setIsFollowing(status);
                });
        }

        return <>
            <div className="grid found_user">
                <div className="user_profile">
                    <p>{user.name} <span>{isFollowed ? "(follower)" : ""}</span></p>
                    <div className="user_infos">
                        <p>{user.fullName}, {user.email}</p>
                        <p>{user.biography}</p>
                    </div>
                </div>

                <div className="follow_buttons">
                {isFollowing ? <div className="grid">
                        <button id="following">Following</button>
                        <button className="delete" onClick={() => updateFollow(false)}>Unfollow</button>
                    </div> :
                    <button onClick={() => updateFollow(true)}>Follow</button>}
                </div>
            </div>
        </>


    }

    return <>
        <input placeholder="Find your bestie" ref={inputName} onInput={findUserList}/>
        <ul>
            {userList.length === 0 ? <p>You are not alone 🥺</p> : userList.map(u => <li key={u.id}>
                <FoundUser user={u}/>
            </li>)}
        </ul>


    </>
}