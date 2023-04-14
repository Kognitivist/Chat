package com.kognitivist.chat.domain.models

data class ListMessages(
    val messages: MutableList<Message>? = mutableListOf<Message>()
)