package org.meowcat.data

data class CommentAddEvent(val subject:Int, val nick:String, val email:String, val content:String)