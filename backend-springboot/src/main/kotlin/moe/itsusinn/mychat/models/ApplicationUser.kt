package moe.itsusinn.mychat.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class ApplicationUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = -1,
    var username:String = "",
    var password:String = ""
)