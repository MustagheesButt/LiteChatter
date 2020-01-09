package com.example.litechatter.screens.near_me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class NearMeViewModel: ViewModel() {
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
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        db.collection("Users")
            .get()
            .addOnSuccessListener { result ->
                val userList = mutableListOf<User>()

                for (document in result) {
                    Timber.d("${document.id} => ${document.data}")

                    if (document.id != currentUser!!.uid) {
                        val d = document.data
                        val user = User(document.id, d["userName"].toString())
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
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        val userRef = db.collection("Users").document(currentUser!!.uid)
        userRef.update("contacts", FieldValue.arrayUnion(userId))
            .addOnSuccessListener {
                _showAddedToContactsMsg.value = true
            }
            .addOnFailureListener {
                _showAddedToContactsMsg.value = false
            }

    }
}