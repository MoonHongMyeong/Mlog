import React from 'react'
import SingleComment from './SingleComment';

export default function ReplyComment(props) {

  return (
    <div>
      {/* <p style={{ "paddingLeft": "2rem", }}>
        View 1 more Comment(s)
      </p> */}
      {props.commentsList &&
        props.commentsList.map((comment, index) => {
          if (comment.parents && comment.parents.id === props.parentCommentId) {
            return (
              <>
                <SingleComment
                  reRenderCommentsAdd={props.reRenderCommentsAdd}
                  reRenderCommentUpdate={props.reRenderCommentUpdate}
                  comment={comment}
                  key={index}
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
          return <div></div>
        })
      }
    </div>
  )
}
