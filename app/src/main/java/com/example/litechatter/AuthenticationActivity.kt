package com.example.litechatter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class AuthenticationActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_authentication)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) { // already signed in
            Log.i("AuthenticationActivity", "User already signed in")
        } else { // not signed in
            startActivityForResult( // Get an instance of AuthUI based on the default app
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(listOf(
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.EmailBuilder().build()
                    ))
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) { // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                Log.i("AuthenticationActivity", "Signed-in successfully ${user.toString()}")
            } else { // Sign in failed
                if (response == null) { // User pressed back button
                    Snackbar.make(findViewById(R.id.content),"Sign-in cancelled", Snackbar.LENGTH_SHORT).show()
                    return
                }
                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(findViewById(R.id.content), "No internet connection", Snackbar.LENGTH_SHORT).show()
                    return
                }
                Snackbar.make(findViewById(R.id.content), "Unknown error", Snackbar.LENGTH_SHORT).show()
                Log.e("AuthenticationActivity", "Sign-in error: ", response.error)
            }
        }
    }
}
