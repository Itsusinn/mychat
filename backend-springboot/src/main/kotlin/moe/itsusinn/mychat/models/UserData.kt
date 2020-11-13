package moe.itsusinn.mychat.models

data class UserData(
    var uid: Long,
    var username: String,
    var nick: String,
    var password: String
)

data class NewUser(
    val username: String,
    val nick: String,
    var password: String
)

data class UserLoginRequest(
    val username: String,
    var password: String
)