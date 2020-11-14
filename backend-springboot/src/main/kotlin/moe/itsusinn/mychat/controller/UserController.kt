package moe.itsusinn.mychat.controller

import moe.itsusinn.mychat.models.NewUser
import moe.itsusinn.mychat.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var userService: UserService

    @RequestMapping("test")
    fun sayHello(): String {
        return "Hello"
    }

    @PostMapping("register")
    fun register(@RequestBody newUser: NewUser) {
        newUser.apply {
            password = bCryptPasswordEncoder.encode(password)
            userService.registerUser(username, nick, password)
        }
    }

    @PostMapping("login")
    fun login() {

    }
}