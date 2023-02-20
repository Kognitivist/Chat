package com.kognitivist.chat.data.database

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kognitivist.chat.data.models.Message

class AllMessageLiveData: LiveData<List<Message>>() {
    private val mAuth = FirebaseAuth.getInstance()
    private val dataBase = Firebase.database.reference.
    child(mAuth.currentUser?.uid.toString())


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
        dataBase.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        dataBase.removeEventListener(listener)
        super.onInactive()
    }
}