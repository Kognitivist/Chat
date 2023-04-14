package com.kognitivist.chat.domain.models

data class Chat(
    val firebaseId: String = "",
    var mailCompanionSecond: String = "",
    val name: String = "Unknown",
    var mailCompanionFirst: String = "",
    val messages: ListMessages = ListMessages()
)
