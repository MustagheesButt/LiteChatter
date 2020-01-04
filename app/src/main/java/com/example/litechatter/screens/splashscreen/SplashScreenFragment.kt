package com.example.litechatter.screens.splashscreen

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSplashScreenBinding>(inflater,
            R.layout.fragment_splash_screen, container, false)

        binding.loginBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }

        binding.signupBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_splashScreenFragment_to_registerFragment)
        }

        // if user is not logged in, display the login/signup buttons
        if (true) {
            binding.loginBtn.visibility = View.VISIBLE
            binding.signupBtn.visibility = View.VISIBLE
        }

        return binding.root
    }
}
