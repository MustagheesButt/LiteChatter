package com.example.litechatter.screens.chatroom

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class ChatRoomViewModel(chatRoomId: String) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser
}