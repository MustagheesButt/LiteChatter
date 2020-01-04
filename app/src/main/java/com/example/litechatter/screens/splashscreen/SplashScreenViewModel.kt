package com.example.litechatter.screens.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class SplashScreenViewModel: ViewModel() {


    private val _navigateToMainActivity = MutableLiveData<Boolean>()
    val navigateToMainActivity: LiveData<Boolean>
        get() = _navigateToMainActivity

    fun onNavigatedToMainActivity() {
        _navigateToMainActivity.value = false
    }

    private val _startSigninProcess = MutableLiveData<Boolean>()
    val startSigninProcess: LiveData<Boolean>
        get() = _startSigninProcess

    fun onSigninProcessCompleted() {
        _startSigninProcess.value = false
    }

    init {
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) { // already signed in
            Timber.i("User already signed in")
            _navigateToMainActivity.value = true
        } else { // not signed in
            Timber.i("User not signed in")
            _startSigninProcess.value = true
        }
    }
}