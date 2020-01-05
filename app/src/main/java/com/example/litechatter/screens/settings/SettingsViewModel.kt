package com.example.litechatter.screens.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SettingsViewModel: ViewModel() {

    private val _userData = MutableLiveData<FirebaseUser>()
    val userData: LiveData<FirebaseUser>
        get() = _userData

    private val _signOut = MutableLiveData<Boolean>()
    val signOut: LiveData<Boolean>
        get() = _signOut

    fun signOut() {
        _signOut.value = true
    }

    fun onSignOutCompleted() {
        _signOut.value = false
    }

    init {
        val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        _userData.value = user
    }
}