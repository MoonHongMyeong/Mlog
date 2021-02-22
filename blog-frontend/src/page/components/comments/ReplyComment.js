import React from 'react'
import SingleComment from './SingleComment';

export default function ReplyComment(props) {

  return (
    <div>
      {props.comments &&
        props.comments.map((comment, index) => {
          if (comment.parents && comment.parents.id === props.parentCommentId) {
            return (
              <>
                <SingleComment
                  reRenderCommentsAdd={props.reRenderCommentsAdd}
                  reRenderCommentUpdate={props.reRenderCommentUpdate}
                  comment={comment}
                  key={comment.id}
                  postId={props.postId} />

                <ReplyComment
                  reRenderCommentsAdd={props.reRenderCommentsAdd}
                  reRenderCommentUpdate={props.reRenderCommentUpdate}
                  parentCommentId={comment.id}
                  comments={props.comments}
                  postId={props.postId} />
              </>
            )
          }
          return <div key={comment.id}></div>
        })
      }
    </div>
  )
}
