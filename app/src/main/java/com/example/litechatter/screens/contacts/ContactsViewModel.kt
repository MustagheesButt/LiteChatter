package com.example.litechatter.screens.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.litechatter.database.Contact
import com.example.litechatter.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class ContactsViewModel: ViewModel() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var userCollection: CollectionReference

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = _contacts

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

                val contactIds = snapshot["contacts"] as HashMap<String, String>?

                if (contactIds.isNullOrEmpty()) {
                    _contacts.value = listOf()
                } else {
                    loadContacts(contactIds)
                }
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    fun loadContacts(contactIds: HashMap<String, String>) {
        val contacts = mutableListOf<Contact>()

        for (contactId in contactIds) {
            userCollection.document(contactId.key)
                .get()
                .addOnSuccessListener { userSnapshot->
                    val user = userSnapshot.toObject(User::class.java)
                    val contact = Contact(user!!, contactId.value)
                    contacts.add(contact)

                    _contacts.value = contacts.toList()
                }
                .addOnFailureListener {
                    Timber.d("Something went wrong while fetching contact: ${it.message}")
                }
        }
    }
}