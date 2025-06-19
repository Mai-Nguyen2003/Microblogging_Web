import {useContext, useState, useRef} from "react";
import {Api} from "./Context.js";
import {basicJson} from "./Headers.js";
import {dateFormatter} from "./Formatter.js";

export default function Post({auth}) {
    const api = useContext(Api);
    const newMessageText = useRef(undefined);
    const [post, setPost] = useState({messageText: null, createdAt: null});
    const [CreatedPost, setCreatedPost] = useState(false);
    const date = dateFormatter.format(new Date(post.createdAt));
    function createPost() {
        const message = {messageText: newMessageText.current.value}
        fetch(api + "/posts", {method: "POST", headers: basicJson(auth), body: JSON.stringify(message)})
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setPost(result)
                setCreatedPost(true);
            });
    }

    return <>
        <h2>{auth.name}</h2>
        <textarea maxLength="250" rows="5" placeholder="How are you feeling ?" ref={newMessageText}></textarea>
        <button onClick={createPost}> Post</button>
        {CreatedPost &&
            <div>
                <p>Thanks for sharing a post at {date} </p>
                <img src="./src/asset/thank.jpg" alt="thank_image"/>
            </div>
        }
    </>
}