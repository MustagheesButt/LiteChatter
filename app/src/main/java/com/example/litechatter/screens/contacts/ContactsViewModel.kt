package com.example.litechatter.screens.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class ContactsViewModel: ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    init {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        db.collection("Users").document(currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val contactIds = document["contacts"] as List<String>?
                    loadContacts(contactIds)
                } else {
                    Timber.d("No such document")
                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }

    }

    fun loadContacts(contactIds: List<String>?) {
        val db = FirebaseFirestore.getInstance()

        for (contactId in contactIds!!) {
            db.collection("Users").document(contactId)
                .get()
                .addOnSuccessListener { userData ->
                    var userList = mutableListOf<User>()
                    val ud = userData.data!!
                    val user = User(userData.id, ud["userName"].toString())

                    // load existing users if any
                    if (_users.value != null) {
                        userList = users.value!!.toMutableList()
                    }

                    Timber.i("fetched contact: ${user.userName}")
                    userList.add(user)
                    _users.value = userList?.toList()
                }
                .addOnFailureListener { exception ->
                    Timber.w("Error getting documents.", exception)
                }
        }
    }

}