package moe.itsusinn.mychat.controller

import moe.itsusinn.mychat.models.UserData
import moe.itsusinn.mychat.services.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
    val bCryptPasswordEncoder: BCryptPasswordEncoder,
    val userService: UserService
) {

    @RequestMapping("test")
    fun sayHello(): String {
        return "Hello"
    }

    @PostMapping("signup")
    fun signUp(@RequestBody userData: UserData){
        userData.password = bCryptPasswordEncoder.encode(userData.password)
        userService.addNewUser(userData.username,userData.nick,userData.password)
    }
}