package moe.itsusinn.mychat.models

data class PostData(
    val id: Long,
    val uid: Long,
    val title: String
)

data class PostCreateRequest(
    val title: String
)
