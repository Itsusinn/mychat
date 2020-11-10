package moe.itsusinn.mychat.controller

import moe.itsusinn.mychat.models.ApplicationUser
import moe.itsusinn.mychat.repository.ApplicationUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
    val applicationUserRepository: ApplicationUserRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @RequestMapping("test")
    fun sayHello(): String {
        return "Hello"
    }
    @PostMapping("signup")
    fun signUp(@RequestBody applicationUser:ApplicationUser){
        applicationUser.password = bCryptPasswordEncoder.encode(applicationUser.password)
        applicationUserRepository.save(applicationUser)
    }
}