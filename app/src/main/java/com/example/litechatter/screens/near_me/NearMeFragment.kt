package com.example.litechatter.screens.near_me


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentNearMeBinding
import com.example.litechatter.helpers.showSnackbar

class NearMeFragment : Fragment() {

    private lateinit var nearMeRecycler: RecyclerView
    private lateinit var nearMeManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNearMeBinding>(inflater,
            R.layout.fragment_near_me, container, false)

        binding.lifecycleOwner = this

        val viewModel = ViewModelProviders.of(this).get(NearMeViewModel::class.java)

        // RecyclerView stuff
        nearMeManager = LinearLayoutManager(this.context)
        val nearMeAdapter = NearMeAdapter(NearMeListener { userId ->
            viewModel.addToContacts(userId)
        })

        nearMeRecycler = binding.nearMeRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = nearMeManager
            adapter       = nearMeAdapter
        }

        viewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                nearMeAdapter.submitList(it)
            }
        })

        viewModel.showAddedToContactsMsg.observe(this, Observer {
            if (it == true) {
                showSnackbar(
                    binding.nearMeFragmentContainer,
                    "Contacts updated!"
                )
                viewModel.onShownAddedToContactsMsg()
            } else if (it == false) {
                showSnackbar(
                    binding.nearMeFragmentContainer,
                    "Could not add to contacts :("
                )
                viewModel.onShownAddedToContactsMsg()
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}