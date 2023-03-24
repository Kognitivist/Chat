package com.kognitivist.chat.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kognitivist.chat.REPOSITORY
import com.kognitivist.chat.data.models.Chat

class AllChatsCurrentUserLiveData: LiveData<Set<Chat>>() {
    private val mAuth = FirebaseAuth.getInstance()
    private val dataBase = Firebase.database.reference


    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val allChats = mutableListOf<Chat>()
            val myChats = mutableSetOf<Chat>()
            snapshot.children.map {
                allChats.add(it.getValue(Chat::class.java) ?: Chat())
                for (chat in allChats){
                    Log.d("MyLogChats","$chat")
                    if (REPOSITORY.currentUser?.email.toString() in chat.myId){
                        myChats.add(chat)
                    }
                }
            }
            value = myChats
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