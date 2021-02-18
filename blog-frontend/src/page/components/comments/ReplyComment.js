import React from 'react'
import SingleComment from './SingleComment';

export default function ReplyComment(props) {

  return (
    <div>
      {props.commentsList &&
        props.commentsList.map((comment, index) => {
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
                  commentsList={props.commentsList}
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
