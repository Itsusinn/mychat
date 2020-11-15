package moe.itsusinn.mychat.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import moe.itsusinn.mychat.models.request.UserLoginRequest
import moe.itsusinn.mychat.models.request.UserRegisterRequest
import moe.itsusinn.mychat.models.respond.Status
import moe.itsusinn.mychat.models.respond.UserLoginRespond
import moe.itsusinn.mychat.models.respond.UserRegisterRespond
import moe.itsusinn.mychat.security.tool.generateToken
import moe.itsusinn.mychat.services.UserRoleService
import moe.itsusinn.mychat.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@Api(tags = ["用户管理"])
@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRoleService: UserRoleService

    @GetMapping("test")
    fun sayHello(): String {
        return "Hello"
    }

    @ApiOperation(value = "注册新用户")
    @PostMapping("register")
    fun register(@RequestBody userRegisterRequest: UserRegisterRequest): UserRegisterRespond {
        userRegisterRequest.apply {
            password = bCryptPasswordEncoder.encode(password)
            val user = userService.registerUser(username, password)
                ?: return UserRegisterRespond(Status.Failed, "Duplicated Username")
            return UserRegisterRespond(Status.Success, "userID:${user.userID}")
        }
    }

    @ApiOperation(value = "登陆，获取token")
    @PostMapping("login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): UserLoginRespond {
        userLoginRequest.apply {

            val user = userService.checkPassword(
                username,
                bCryptPasswordEncoder.encode(password)
            ) ?: return UserLoginRespond(Status.Failed, "")

            val roles = userRoleService.findRolesByUid(user.userID)
            val token = generateToken(
                user.userID,
                UUID.fromString(user.userID.toString()).toString(),
                roles.joinToString(":")
            )
            return UserLoginRespond(Status.Success, token)
        }
    }
}