package com.example.litechatter.screens.splashscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentSplashScreenBinding
import com.example.litechatter.showSnackbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class SplashScreenFragment : Fragment() {

    private val RC_SIGN_IN = 123
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSplashScreenBinding.inflate(inflater)

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)

        viewModel.startSigninProcess.observe(this, Observer {
            if (it) {
                startSigninProcess()
                viewModel.onSigninProcessCompleted()
            }
        })

        viewModel.navigateToMainActivity.observe(this, Observer {
            if (it) {
                this.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainActivity())
                viewModel.onNavigatedToMainActivity()
            }
        })

        return binding.root
    }

    fun startSigninProcess() {
        startActivityForResult( // Get an instance of AuthUI based on the default app
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.EmailBuilder().build()
                    )
                )
                .setLogo(R.drawable.logo)
                .setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) { // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                // check if user is newly registered
                if (response!!.isNewUser) {
                    showSnackbar(activity!!.findViewById(R.id.splashScreenContainer), "Newly registered user")

                    val db = FirebaseFirestore.getInstance()
                    val userMap = hashMapOf(
                        "userName" to user!!.displayName
                    )

                    // store id of newly registered user in database (Firestore)
                    db.collection("Users")
                        .document(user.uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            Timber.d("DocumentSnapshot added")
                        }
                        .addOnFailureListener { e ->
                            Timber.w("Error adding document", e)
                        }
                }

                Timber.i("Signed-in successfully")
                viewModel.onSigninSuccessfull()
            } else { // Sign in failed
                if (response == null) { // User pressed back button
                    showSnackbar(activity!!.findViewById(R.id.splashScreenContainer),"Sign-in cancelled")
                    return
                }
                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(activity!!.findViewById(R.id.splashScreenContainer), "No internet connection", Snackbar.LENGTH_SHORT).show()
                    return
                }
                Snackbar.make(activity!!.findViewById(R.id.splashScreenContainer), "Unknown error", Snackbar.LENGTH_SHORT).show()
                Timber.e("Sign-in error: ", response.error)
            }
        }
    }
}