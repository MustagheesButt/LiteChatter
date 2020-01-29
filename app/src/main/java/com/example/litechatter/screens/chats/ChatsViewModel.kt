package com.example.litechatter.screens.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.ChatItem
import com.example.litechatter.database.PrivateChatRoom
import com.google.firebase.firestore.FirebaseFirestore

class ChatsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _recentChatMessages = MutableLiveData<List<ChatItem>>()
    val recentChatMessages: LiveData<List<ChatItem>>
        get() = _recentChatMessages

    init {
        // get contact list

        // for each contact item, get its chatroom

    }
}