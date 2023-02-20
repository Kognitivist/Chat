package com.kognitivist.chat.data.models

data class Message (
    val id: Int = 0,
    val firebaseId: String = "",
    val nameSander: String = "",
    val mailSender: String = "",
    val nameRecipient: String = "",
    val mailRecipient: String = "",
    val message: String = ""
        )