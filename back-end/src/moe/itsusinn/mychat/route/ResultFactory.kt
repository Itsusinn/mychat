package moe.itsusinn.mychat.route

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


