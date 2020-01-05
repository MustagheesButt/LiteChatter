package com.example.litechatter.screens.settings


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.litechatter.AuthenticationActivity
import com.example.litechatter.databinding.FragmentSettingsBinding
import com.firebase.ui.auth.AuthUI

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSettingsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        viewModel.signOut.observe(this, Observer {
            if (it) {
                AuthUI.getInstance()
                    .signOut(context!!)
                    .addOnCompleteListener {
                        startActivity(Intent(context, AuthenticationActivity::class.java))
                        viewModel.onSignOutCompleted()
                    }
            }

        })

        binding.viewModel = viewModel

        return binding.root
    }


}
