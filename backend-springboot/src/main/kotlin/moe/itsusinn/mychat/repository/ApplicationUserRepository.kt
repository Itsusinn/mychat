package moe.itsusinn.mychat.repository

import moe.itsusinn.mychat.models.ApplicationUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApplicationUserRepository : JpaRepository<ApplicationUser,Long>{

    fun findByUsername(username: String):ApplicationUser?

}