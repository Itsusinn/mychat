package org.meowcat.data

data class MySession(val account:String,val token:String)

fun check(account: String,token: String):Boolean{
    return false
    //TODO 查redis
}