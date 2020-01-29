package com.example.litechatter.screens.chatroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatRoomViewModelFactory(private val chatRoomId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatRoomViewModel::class.java)) {
            return ChatRoomViewModel(chatRoomId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}