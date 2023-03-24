package com.kognitivist.chat.data.database

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kognitivist.chat.*
import com.kognitivist.chat.data.models.Chat
import com.kognitivist.chat.data.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirebaseRepository (val context: Context): DataBaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    override val readAllMessage: LiveData<List<Message>> = AllMessageOfChatCurrentUserLiveData()
    override val readAllChatCurrentUser: LiveData<Set<Chat>> = AllChatsCurrentUserLiveData()
    private val dataBase = Firebase.database.reference
    override val currentUser: FirebaseUser? = mAuth.currentUser








    override suspend fun createMessage(chat: Chat, message: Message, onSuccess: () -> Unit) {
        val messageId = dataBase.push().key.toString()
        val mapMessages = hashMapOf<String,Any>()

        mapMessages[FIREBASE_ID] = messageId
        mapMessages[NAME_SENDER] = message.nameSender
        mapMessages[MAIL_SENDER] = message.mailSender
        mapMessages[NAME_RECIPIENT] = message.nameRecipient
        mapMessages[MAIL_RECIPIENT] = message.mailRecipient
        mapMessages[MESSAGE] = message.message



        dataBase.child(chat.name).child(MESSAGES).child(messageId).updateChildren(mapMessages)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {  }
    }

    override suspend fun createChat(chat: Chat, onSuccess: () -> Unit) {
        val chatId = dataBase.push().key.toString()
        val mapChat = hashMapOf<String,Any?>()

        mapChat[FIREBASE_ID] = chatId
        mapChat[MY_ID] = chat.myId
        mapChat[NAME_CHAT] = chat.name
        mapChat[MAIL_CHAT] = chat.mail
        mapChat[MESSAGES] = chat.messages



        dataBase.child(chat.name).updateChildren(mapChat)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {  }

    }




    override suspend fun delete(message: Message, onSuccess: () -> Unit) {
        val messageId = message.firebaseId

        dataBase.child(messageId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {  }

    }

    override fun signOut() {
        mAuth.signOut()
    }

    override suspend fun enterToDataBase(login: String, password: String, onSuccess: ()-> Unit, onFail: (String)-> Unit) {
        mAuth.signInWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                //Toast.makeText(context, "Вы вошли ", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                //Toast.makeText(context, "Нет такого пользователя", Toast.LENGTH_LONG).show()
            }
    }

    override suspend fun registrationAndEnterOfDataBases(login: String, password: String, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.createUserWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                /*currentUser?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName("Пирожок").build())*/
                if (mAuth.currentUser?.email != null){
                    Toast.makeText(context, "Вы зарегестрированы", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { it.message.toString() }
    }
}