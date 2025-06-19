import {useContext, useRef} from "react";
import {basic, basicJson} from "./Headers.js";
import {Api} from "./Context.js";
import {formatTimeAgo} from "./Formatter.js";

export default function Comments({auth, postId,comments,setComments,closeComments}){
    const newComment = useRef(undefined);
    const api = useContext(Api);

    function deleteComment(id){
        fetch(api + "/posts/" + postId + "/comments/" + id, {method: "DELETE", headers: basic(auth)}
        )
            .then(response => {
                if (!response.ok) throw new Error(response.statusText);
            })
            .then(() => {
                setComments(comments.filter(c => c.id !== id));
            });

    }

    function addComment(){
        const comment = {commentText: newComment.current.value}
        fetch(api + "/posts/"+postId+"/comments", {method: "POST", headers: basicJson(auth),body: JSON.stringify(comment)}
        )
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error(response.statusText);
            })
            .then(result => {
                setComments([...comments,result])
            });
    }
    return <>
        <div className="overlay"></div>
        <div className="modal-content">
            <h3>Comments</h3>
            <ul>
                {comments.map(c => <li key={c.id} className="item">
                    <div className="grid">
                        <p>{c.user.name}</p>
                        <p className="info">{formatTimeAgo(new Date(c.createdAt))}</p>
                    </div>
                    <p>{c.textComment}</p>
                    {c.user.name === auth.name &&
                        <button className="delete" onClick={() => deleteComment(c.id)}>&#x1F5D1;</button>}
                </li>)}
            </ul>

            <div id= "comment_box" className="grid" >
                <textarea name="comment" rows="1" placeholder="Add your comment" ref={newComment}></textarea>
                <button onClick={addComment}>Add</button>
            </div>
            <button className="close-modal delete" onClick={closeComments}>❌</button>

        </div>
    </>

}
