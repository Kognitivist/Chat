package com.kognitivist.chat.data.database

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.kognitivist.chat.data.models.Chat
import com.kognitivist.chat.data.models.Message

interface DataBaseRepository {
    val readAllMessage: LiveData<List<Message>>
    val readAllChatCurrentUser: LiveData<Set<Chat>>
    val currentUser: FirebaseUser?




    suspend fun createMessage(chat: Chat, message: Message, onSuccess: ()-> Unit)
    suspend fun createChat(chat: Chat, onSuccess: () -> Unit)
    suspend fun delete(message: Message, onSuccess: ()-> Unit)

    fun signOut(){}

    suspend fun enterToDataBase(login: String, password: String, onSuccess: ()-> Unit, onFail: (String)-> Unit){}

    suspend fun registrationAndEnterOfDataBases(login: String, password: String, onSuccess: ()-> Unit, onFail: (String)-> Unit){}

}