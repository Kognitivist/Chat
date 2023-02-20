package com.kognitivist.chat.data.database

import androidx.lifecycle.LiveData
import com.kognitivist.chat.data.models.Message

interface DataBaseRepository {
    val readAll: LiveData<List<Message>>

    suspend fun create(message: Message, onSuccess: ()-> Unit)
    suspend fun delete(message: Message, onSuccess: ()-> Unit)

    fun signOut(){}

    fun connectToDataBase(onSuccess: ()-> Unit, onFail: (String)-> Unit){}
}