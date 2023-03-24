package com.kognitivist.chat.data.database

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kognitivist.chat.CHAT_ID
import com.kognitivist.chat.MESSAGES
import com.kognitivist.chat.data.models.Message

class AllMessageOfChatCurrentUserLiveData: LiveData<List<Message>>() {
    private val mAuth = FirebaseAuth.getInstance()
    private val dataBase = Firebase.database.reference


    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val messages = mutableListOf<Message>()
            snapshot.children.map {
                messages.add(it.getValue(Message::class.java) ?: Message())
            }
            value = messages
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    override fun onActive() {
        dataBase.child(CHAT_ID.name).child(MESSAGES).addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        dataBase.child(CHAT_ID.name).child(MESSAGES).removeEventListener(listener)
        super.onInactive()
    }
}