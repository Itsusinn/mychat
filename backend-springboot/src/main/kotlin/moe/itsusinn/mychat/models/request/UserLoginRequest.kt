package moe.itsusinn.mychat.models.request

data class UserLoginRequest(
    val username: String,
    var password: String
)