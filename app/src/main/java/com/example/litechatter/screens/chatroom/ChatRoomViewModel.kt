package com.example.litechatter.screens.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.PrivateChatRoom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class ChatRoomViewModel(chatRoomId: String) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    private val db = FirebaseFirestore.getInstance()
    private val privateChatRoomsCollection = db.collection("PrivateChatRooms")

    private val _privateChatRoom = MutableLiveData<PrivateChatRoom>()
    val privateChatRoom : LiveData<PrivateChatRoom>
    get() = _privateChatRoom

    init {
        Timber.i("initialized with: $chatRoomId")

        val currentChatRoom = privateChatRoomsCollection.document(chatRoomId)

        currentChatRoom.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.e("Listen failed", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Timber.d("Current data: ${snapshot.data}")

                _privateChatRoom.value = snapshot.toObject(PrivateChatRoom::class.java)
            } else {
                Timber.d("Current data: null")
            }
        }
    }
}