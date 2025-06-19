import {useContext, useEffect, useState} from "react";
import {basic} from "./Headers.js";
import Comments from "./Comments.jsx";
import {Api} from "./Context.js";
import {formatTimeAgo} from "./Formatter.js";

export default function PostOfUser({post,auth,setPosts,posts,view}){
    const api = useContext(Api);
    const[like, setLike] = useState(false);
    const [likesCount, setLikesCount] = useState(post.likes.length);
    const[showComments,setShowComments] =useState(false);
    const [comments,setComments] = useState([]);
    const timeline = formatTimeAgo(new Date(post.createdAt));

    useEffect(() => {
        if (post.user.name !== auth.name) {
            fetch(api + "/post/" + post.id + "/like", {method: "GET", headers: basic(auth)}
            )
                .then(response => {
                    if (response.ok) return response.json();
                    else throw new Error(response.statusText);
                })
                .then(result => {
                    setLike(result);
                });}

    }, [api,auth]);
    useEffect(() => {
        fetch(api + "/posts/"+post.id+"/comments", {method: "GET", headers: basic(auth)}
        )
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setComments(result);

            });
    }, [api,auth]);

    function updateLike(status) {
        fetch(api +"/post/"+post.id+"/like",
            {headers: basic(auth), method: status ? "POST" : "DELETE"})
            .then(response => {
                if (!response.ok) throw new Error(response.statusText);
            })
            .then(() => {
                setLike(status);
                if(status){
                    setLikesCount(likesCount+1)
                }else{
                    setLikesCount(likesCount-1)
                }
            });
    }

    function deletePost(){
        fetch(api + "/posts/"+post.id,
            {headers: basic(auth), method: "DELETE"})
            .then(response => {
                if (!response.ok) throw new Error(response.statusText);
            })
            .then(() => {
                setPosts(posts.filter(p => p.id !== post.id));
            });
    }

    return<>
        <div>
            <div className="grid">
                <h5>{post.user.name}</h5>
                <div className="info">
                    <p>{timeline}</p>
                    <p>{likesCount} Likes, {comments.length} Comments</p>
                </div>
            </div>
            <p className="post_text">{post.messageText}</p>

            <div className="grid">
                {view === "Home" &&
                    <button className="emoji" onClick={() => {
                    like ? updateLike(false) : updateLike(true)
                }}>{like ? "💖" : "🤍"}</button>
                }

                <button className="emoji" onClick={() => {
                    setShowComments(!showComments)
                }}>🗫
                </button>
                {showComments && <Comments className="modal" auth={auth} postId={post.id} comments={comments} setComments={setComments} closeComments={() => setShowComments(!showComments)}/>}
                {view === "Profile" && <button className={"delete emoji"} onClick={deletePost}>&#x1F5D1;</button>}
            </div>
        </div>
    </>
}