package com.kognitivist.chat.domain.usecases

import com.kognitivist.chat.domain.interface_database_repository.DataBaseRepository
import com.kognitivist.chat.domain.models.Chat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CreateChatUseCase {

    suspend fun execute(chat: Chat, repository: DataBaseRepository, onSuccess: () -> Unit){
        coroutineScope {
            launch (Dispatchers.IO){
                repository.createChat(chat = chat){
                    launch (Dispatchers.Main){onSuccess()}
                }
            }
        }
    }
}