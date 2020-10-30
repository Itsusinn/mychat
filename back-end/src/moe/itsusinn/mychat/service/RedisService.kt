package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.service.RedisService.tokenList
import org.redisson.Redisson
import org.redisson.api.RList
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import java.util.concurrent.TimeUnit


/**
 * @property tokenList "TOKEN:${user.uid}:$token.uuid"
 */
object RedisService {
    private val config = Config().apply {
        this.useSingleServer().setTimeout(1000000).address = "redis://127.0.0.1:6379"
    }
    private val redisson: RedissonClient = Redisson.create(config)


    val tokenList:RList<String> = redisson.getList("tokenList")
    init {
        tokenList.add("PreventExpireNotWork")
        tokenList.expire(7, TimeUnit.DAYS)
    }
}
