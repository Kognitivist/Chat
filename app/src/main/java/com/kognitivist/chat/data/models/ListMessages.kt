package com.kognitivist.chat.data.models

data class ListMessages(
    val messages: MutableList<Message>? = mutableListOf<Message>()
)