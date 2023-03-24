package com.kognitivist.chat.data.models

data class Chat(
    val firebaseId: String = "",
    val myId: String = "",
    val name: String = "",
    val mail: String = "",
    val messages: ListMessages = ListMessages()
)
