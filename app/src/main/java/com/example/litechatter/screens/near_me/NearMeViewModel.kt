package com.example.litechatter.screens.near_me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.PrivateChatRoom
import com.example.litechatter.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class NearMeViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollectionRef = db.collection("Users")
    private val privateChatRoomCollectionRef = db.collection("PrivateChatRooms")

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _showAddedToContactsMsg = MutableLiveData<Boolean?>()
    val showAddedToContactsMsg: LiveData<Boolean?>
        get() = _showAddedToContactsMsg

    fun onShownAddedToContactsMsg() {
        _showAddedToContactsMsg.value = null
    }

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser

        // fetching all users to display in list except currentUser
        usersCollectionRef
            .get()
            .addOnSuccessListener { result ->
                val userList = mutableListOf<User>()

                for (document in result) {
                    Timber.d("${document.id} => ${document.data}")

                    if (document.id != currentUser!!.uid) {
                        val d = document.data
                        val user = User(document.id, d["userName"].toString(), null)

                        userList.add(user)
                    }
                }

                _users.value = userList.toList()
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }
    }

    fun addToContacts(userId: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        // first create a chatroom for currentUser and this new, to be added, contact
        val newChatRoom = PrivateChatRoom(
            currentUser!!.uid,
            userId
        )
        privateChatRoomCollectionRef
            .add(newChatRoom)
            .addOnSuccessListener { documentReference ->
                val newChatRoomId = documentReference.id

                // now add the contact as => 'new contact's id' : 'chatroom id with that user '
                val userRef = usersCollectionRef.document(currentUser.uid)
                userRef.update("contacts.${userId}", newChatRoomId)
                    .addOnSuccessListener {
                        _showAddedToContactsMsg.value = true
                    }
                    .addOnFailureListener {
                        _showAddedToContactsMsg.value = false
                    }
            }
            .addOnFailureListener {
                Timber.d("Could not add chatroom")
            }

    }
}