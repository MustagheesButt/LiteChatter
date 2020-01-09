package com.example.litechatter.screens.contacts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentContactsBinding
import com.example.litechatter.databinding.FragmentNearMeBinding
import timber.log.Timber

class ContactsFragment : Fragment() {

    private lateinit var contactsRecycler: RecyclerView
    private lateinit var contactsManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentContactsBinding>(inflater, R.layout.fragment_contacts, container, false)

        binding.lifecycleOwner = this

        val viewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)

        contactsManager = LinearLayoutManager(this.context)
        val contactsAdapter = ContactsAdapter(ContactsListener {
            Timber.i("contact item clicked")
        })

        contactsRecycler = binding.contactsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = contactsManager
            adapter = contactsAdapter
        }

        viewModel.users.observe(viewLifecycleOwner, Observer {
            Timber.i("users livedata updated: $it")
            it?.let {
                contactsAdapter.submitList(it)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}