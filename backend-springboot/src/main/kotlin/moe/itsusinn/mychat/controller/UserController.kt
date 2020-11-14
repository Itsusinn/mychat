package moe.itsusinn.mychat.controller

import moe.itsusinn.mychat.models.UserLoginRequest
import moe.itsusinn.mychat.models.UserRegisterRequest
import moe.itsusinn.mychat.security.tool.generateToken
import moe.itsusinn.mychat.services.UserRoleService
import moe.itsusinn.mychat.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var userRoleService: UserRoleService

    @RequestMapping("test")
    fun sayHello(): String {
        return "Hello"
    }

    @PostMapping("register")
    fun register(@RequestBody userRegisterRequest: UserRegisterRequest) {
        userRegisterRequest.apply {
            password = bCryptPasswordEncoder.encode(password)
            userService.registerUser(username, nick, password)
        }
    }

    @PostMapping("login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): Pair<String, String> {
        userLoginRequest.apply {
            val user = userService.checkPassword(username, password)
                ?: throw BadCredentialsException("Wrong Password")
            val roles = userRoleService.findRolesByUid(user.uid)
            val token = generateToken(
                user.uid,
                UUID.fromString(user.uid.toString()).toString(),
                roles.joinToString(":")
            )

            return "token" to token
        }
    }
}