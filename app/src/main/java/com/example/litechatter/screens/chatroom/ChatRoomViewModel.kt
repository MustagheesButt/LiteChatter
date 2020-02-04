package com.example.litechatter.screens.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.ChatMessage
import com.example.litechatter.database.PrivateChatRoom
import com.example.litechatter.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class ChatRoomViewModel(chatRoomId: String) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser

    private val db = FirebaseFirestore.getInstance()
    private val privateChatRoomsCollection = db.collection("PrivateChatRooms")
    private val currentChatRoomRef = privateChatRoomsCollection.document(chatRoomId)

    private val _privateChatRoom = MutableLiveData<PrivateChatRoom>()
    val privateChatRoom : LiveData<PrivateChatRoom>
    get() = _privateChatRoom

    private val _user = MutableLiveData<Pair<String, User>>()
    val user : LiveData<Pair<String, User>>
    get() = _user

    init {
        Timber.i("initialized with: $chatRoomId")

        currentChatRoomRef.addSnapshotListener { snapshot, e ->
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

    fun sendChatMessage(msg: ChatMessage) {
        currentChatRoomRef.update("messages", FieldValue.arrayUnion(msg))
    }

    fun getUserById(id: String) {
        db.collection("Users")
            .document(id)
            .get()
            .addOnSuccessListener {
                _user.value = Pair(id, it.toObject(User::class.java)!!)
            }
    }
}