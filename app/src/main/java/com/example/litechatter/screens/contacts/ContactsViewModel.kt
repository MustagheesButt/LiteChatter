package com.example.litechatter.screens.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class ContactsViewModel: ViewModel() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var userCollection: CollectionReference

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    init {
        userCollection = db.collection("Users")

        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserDoc = userCollection.document(currentUser!!.uid)

        currentUserDoc.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.e("Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Timber.d("Current data: ${snapshot.data}")

                val contactIds = snapshot["contacts"] as List<String>?

                loadContacts(contactIds)
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    private fun loadContacts(contactIds: List<String>?) {

        if ((contactIds == null) or contactIds!!.isEmpty()) {
            _users.value = listOf()
            return
        }

        var userList = mutableListOf<User>()

        for (contactId in contactIds) {
            userCollection.document(contactId)
                .get()
                .addOnSuccessListener { userData ->
                    val ud = userData.data!!
                    val user = User(userData.id, ud["userName"].toString())

                    Timber.i("fetched contact: ${user.userName}")
                    userList.add(user)
                    _users.value = userList.toList()
                }
                .addOnFailureListener { exception ->
                    Timber.w("Error getting documents.", exception)
                }
        }
    }
}