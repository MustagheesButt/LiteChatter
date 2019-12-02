package com.example.litechatter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.litechatter.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        binding.loginBtn.setOnClickListener {
            if (binding.email.text.toString().equals("gcc@yahoo.com") and binding.password.text.toString().equals("1234")) {
                it.findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            } else {
                Toast.makeText(context, "whatever", Toast.LENGTH_LONG).show()
                it.findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            }
        }

        return binding.root
    }


}
