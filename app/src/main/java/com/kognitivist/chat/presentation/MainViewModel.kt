package com.kognitivist.chat.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kognitivist.chat.data.database.FirebaseRepository
import com.kognitivist.chat.domain.models.Chat
import com.kognitivist.chat.domain.models.Message
import com.kognitivist.chat.tools.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val context = application

    fun getMailUser(): String{
        REPOSITORY = FirebaseRepository(context = context)
        return REPOSITORY.currentUser?.email ?: "null"
    }

    fun enterDataBase(mail: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch (Dispatchers.IO){
            REPOSITORY = FirebaseRepository(context = context)
            REPOSITORY.enterToDataBase(
                mail = mail,
                password = password,
                onSuccess = { onSuccess() },
                onFail = { Log.d("mylog","Error: ${it}") }
            )
        }
    }

    fun registrationOfDataBase(mail: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch (Dispatchers.IO) {
            REPOSITORY = FirebaseRepository(context = context)
            REPOSITORY.registrationAndEnterOfDataBases(
                mail = mail,
                password = password,
                {onSuccess()},
                { Log.d("mylog","Error: ${it}")}
            )
        }
    }

    fun addMessage(chat: Chat, message: Message, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.createMessage(chat = chat, message = message){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun addChat(chat: Chat, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.createChat(chat = chat){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun readAllMessages() = REPOSITORY.readAllMessage
    fun readAllChatCurrentUser() = REPOSITORY.readAllChatCurrentUser





    fun signOut(onSuccess: () -> Unit){
        REPOSITORY.signOut()
        onSuccess()
    }

}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}

