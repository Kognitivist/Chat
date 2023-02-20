package com.kognitivist.chat.data.database

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kognitivist.chat.*
import com.kognitivist.chat.data.models.Message

class FirebaseRepository: DataBaseRepository {
    private val mAuth = FirebaseAuth.getInstance()
    override val readAll: LiveData<List<Message>> = AllMessageLiveData()
    private val dataBase = Firebase.database.reference.
    child(mAuth.currentUser?.uid.toString())


    override suspend fun create(message: Message, onSuccess: () -> Unit) {
        val messageId = dataBase.push().key.toString()
        val mapMessages = hashMapOf<String,Any>()

        mapMessages[FIREBASE_ID] = messageId
        mapMessages[NAME_SENDER] = message.nameSander
        mapMessages[MAIL_SENDER] = message.mailSender
        mapMessages[NAME_RECIPIENT] = message.nameRecipient
        mapMessages[MAIL_RECIPIENT] = message.mailRecipient
        mapMessages[MESSAGE] = message.message



        dataBase.child(messageId).updateChildren(mapMessages)
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

    override fun connectToDataBase(onSuccess: ()-> Unit, onFail: (String)-> Unit) {

        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { it.message.toString() } }
    }
}