package com.kognitivist.chat.domain.models

data class User (
    val id: String,
    val firstName: String,
    val lastName: String = "",
    val password: String
        )