package com.kognitivist.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kognitivist.chat.data.database.FirebaseRepository
import com.kognitivist.chat.data.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    fun initDataBase(onSuccess: () -> Unit) {
        REPOSITORY = FirebaseRepository()
        REPOSITORY.connectToDataBase(
            {onSuccess()},
            { Log.d("mylog","Error: ${it}")}
        )
    }
    fun addMessage(message: Message, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(message = message){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }
    fun readAllNotes() = REPOSITORY.readAll

    fun deleteNote(message: Message, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.delete(message = message){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }
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