package moe.itsusinn.mychat.service

import moe.itsusinn.mychat.appConfig
import moe.itsusinn.mychat.service.RedisService.sessionList
import org.redisson.Redisson
import org.redisson.api.RList
import org.redisson.api.RMap
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import java.util.concurrent.TimeUnit

/**
 * @property sessionList "$uid:$uuid"
 */
object RedisService {
    private val config = Config().apply {
        val address = appConfig.property("redis.address").getString()
        this.useSingleServer().setTimeout(1000000).address = address
    }
    private val redisson: RedissonClient = Redisson.create(config)


    private val sessionList:RList<String> = redisson.getList("tokenList")
    private val uuidMap:RMap<String,String> = redisson.getMap("uuidList")
    init {
        sessionList.add("PreventExpireNotWork")
        //7天过期
        sessionList.expire(7, TimeUnit.DAYS)
    }

    fun login(uid:Int,uuid:String){
        sessionList.add("$uid:$uuid")
        val meanwhile = getMeanwhile(uid)
        meanwhile.add(uuid)
        setMeanwhile(uid,meanwhile)
    }
    fun isOnline(uid:Int,uuid:String):Boolean{
        return sessionList.contains("$uid:$uuid").also {online ->
            //session过期的情况
            if (!online){
                val meanwhile = getMeanwhile(uid)
                meanwhile.forEach {
                    if(it == uuid){meanwhile.remove(it)}
                }
                setMeanwhile(uid,meanwhile)
            }
        }
    }
    fun logout(uid:Int,uuid:String){
        sessionList.remove("$uid:$uuid")
        val meanwhile = getMeanwhile(uid)
        meanwhile.forEach {
            if(it == uuid){meanwhile.remove(it)}
        }
        setMeanwhile(uid,meanwhile)
    }
    fun allLogout(uid:Int){
        val meanwhileNow = getMeanwhile(uid)
        meanwhileNow.forEach { uuid ->
            sessionList.remove("$uid:$uuid")
        }
        setMeanwhile(uid, null)
    }

    private fun getMeanwhile(uid: Int): MutableList<String> {
        val meanwhile:String = uuidMap[uid.toString()] ?: ""

        val list = meanwhile.split(".")
        return mutableListOf<String>().apply {
            addAll(list)
        }
    }
    private fun setMeanwhile(uid: Int, uuids:List<String>?){
        uuidMap[uid.toString()] = uuids?.joinToString(".")
    }
}
