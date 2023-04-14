package com.kognitivist.chat.domain.interface_database_repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.kognitivist.chat.domain.models.Chat
import com.kognitivist.chat.domain.models.Message

interface DataBaseRepository {
    val readAllMessage: LiveData<List<Message>>
    val readAllChatCurrentUser: LiveData<Set<Chat>>
    val currentUser: FirebaseUser?

    suspend fun createMessage(chat: Chat, message: Message, onSuccess: ()-> Unit)
    suspend fun createChat(chat: Chat, onSuccess: () -> Unit)
    suspend fun deleteChat(chat: Chat, onSuccess: ()-> Unit)

    fun signOut(){}

    suspend fun enterToDataBase(mail: String, password: String, onSuccess: ()-> Unit, onFail: (String)-> Unit){}

    suspend fun registrationAndEnterOfDataBases(mail: String, password: String, onSuccess: ()-> Unit, onFail: (String)-> Unit){}

}