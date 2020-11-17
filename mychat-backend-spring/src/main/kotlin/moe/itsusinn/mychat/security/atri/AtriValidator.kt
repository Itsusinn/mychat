package moe.itsusinn.mychat.security.atri

interface AtriValidator {
    /**
     * 要检查此Token是否在缓存中过期
     * @return 登录态仍然维持则返回true，否则false
     */
    fun verifyToken(rawToken: String): Boolean
}