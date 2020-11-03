package moe.itsusinn.mychat.route

import io.ktor.jackson.*

enum class Status{
    Success,Failed
}

/**
 * create a "result" for response
 */
fun createResult(status: Status,vararg msg:Pair<String,Any>):Map<String,Any>{
    val result = hashMapOf(*msg)
    result["status"] = status.name
    return result
}
fun createResult(status: Status,data:List<Any>):Map<String,Any>{
    val result = mutableMapOf<String,Any>()
    result["status"] = status.name
    result["data"] = data
    return result
}


