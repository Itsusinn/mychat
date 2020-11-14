package moe.itsusinn.mychat.services

import moe.itsusinn.mychat.security.atri.AtriValidator
import org.springframework.stereotype.Component

@Component
class MyValidator : AtriValidator {
    /**
     * 检查此Token是否在缓存中过期
     */
    override fun verifyToken(rawToken: String): Boolean {
        //TODO 检查在Redis中是否过期
        return true
    }

}
