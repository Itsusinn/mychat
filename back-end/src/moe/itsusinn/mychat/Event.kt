package moe.itsusinn.mychat

data class CommentAddEvent(val subject:Int?,
                           val content:String?)

data class UserRegisterEvent(val account:String,
                             val nick:String,
                             val password:String)

data class PostAddEvent(val title:String?)

data class GetCommentsEvent(val post_id:Int?)