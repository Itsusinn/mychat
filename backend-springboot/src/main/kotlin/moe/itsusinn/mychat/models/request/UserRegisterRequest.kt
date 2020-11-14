package moe.itsusinn.mychat.models.request

data class UserRegisterRequest(
    val username: String,
    var password: String
)