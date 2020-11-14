package moe.itsusinn.mychat.models

data class CommentData(
    val postID: Long,
    val commentID: Long,
    val authorID: Long,
    val content: String
)

data class CommentCreateRequest(
    val content: String,
    val postID: Long
)
