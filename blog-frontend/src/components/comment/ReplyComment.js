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
                <SingleComment reRenderComments={props.reRenderComments} comment={comment} key={index} postId={props.postId} />
                <ReplyComment reRenderComments={props.reRenderComments} parentCommentId={comment.id} commentsList={props.commentsList} postId={props.postId} />
              </>
            )
          }
          return <div></div>
        })
      }
    </div>
  )
}
