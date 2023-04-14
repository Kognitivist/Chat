package com.kognitivist.chat.domain.models


data class Message (
    val firebaseId: String = "",
    val nameSender: String = "",
    val mailSender: String = "",
    val nameRecipient: String = "",
    val mailRecipient: String = "",
    val message: String = "message"
        )