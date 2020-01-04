package com.example.litechatter.screens.splashscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentSplashScreenBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class SplashScreenFragment : Fragment() {

    private val RC_SIGN_IN = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSplashScreenBinding.inflate(inflater)

        val viewModel = SplashScreenViewModel()

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

                Timber.i("Signed-in successfully ${user.toString()}")

            } else { // Sign in failed
                if (response == null) { // User pressed back button
                    Snackbar.make(activity!!.findViewById(R.id.content),"Sign-in cancelled", Snackbar.LENGTH_SHORT).show()
                    return
                }
                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(activity!!.findViewById(R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show()
                    return
                }
                Snackbar.make(activity!!.findViewById(R.id.content), "Unknown error", Snackbar.LENGTH_SHORT).show()
                Timber.e("Sign-in error: ", response.error)
            }
        }
    }
}