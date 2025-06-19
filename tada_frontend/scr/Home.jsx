import {useContext, useState, useEffect,useRef} from "react";
import {Api} from "./Context.js";
import {basic} from "./Headers.js";
import PostOfUser from "./PostOfUser.jsx";

export default function Home({auth}) {
    const api = useContext(Api);
    const [posts, setPosts] = useState([]);
    const userName = useRef("")
    
    function viewAllUsersPosts(){
        fetch(api + "/allPosts", {method: "GET", headers: basic(auth)}
        )
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setPosts(result.reverse().slice(0, 20));
            });
    }

  useEffect(() => {
           viewAllUsersPosts()
       }, [api, auth]);


    function findUserPosts(){
        if (userName.current.value !== ""){
            if (userName.current.value !== auth.name){
                fetch(api + "/users/" + userName.current.value +"/posts", {method: "GET", headers: basic(auth)}
                )
                    .then(response => {
                        if (response.ok) return response.json();
                        else throw new Error(response.statusText);
                    })
                    .then(result => {
                        setPosts(result.reverse().slice(0, 20));
                    });
            }
            else if(userName.current.value === auth.name){
                fetch(api + "/posts", {method: "GET", headers: basic(auth)}
                )
                    .then(response => {
                        if (response.ok) return response.json();
                        else throw new Error(response.statusText);
                    })
                    .then(result => {
                        setPosts(result.reverse());
                    });
            }
        } else{viewAllUsersPosts()}
    }

    return <>
        <input placeholder="Search posts from ..." ref={userName} onInput={findUserPosts} />
        {(posts.length === 0 && userName.current.value !== "") && <p>Cannot find posts from "{userName.current.value}" 🥺</p>}

        <div>
            <ul>{posts.map(p => <li className="post_item"><PostOfUser key={p} auth={auth} post={p} setPosts={setPosts}
                                                                 posts={posts} view="Home"/></li>)}</ul>
        </div>
    </>
}
