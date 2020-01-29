package com.example.litechatter.screens.contacts


import android.content.Intent
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
import com.example.litechatter.screens.chatroom.PrivateChatRoomActivity

import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentContactsBinding
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

        val contactsAdapter = ContactsAdapter(ContactsListener {chatRoomId ->
            val intent = Intent(context, PrivateChatRoomActivity::class.java).apply {
                putExtra("chatRoomId", chatRoomId)
            }
            startActivity(intent)
        })

        contactsRecycler = binding.contactsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = contactsManager
            adapter = contactsAdapter
        }

        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            Timber.i("users livedata updated: $it")

            if (it.isEmpty())
                binding.contactsEmptyMsg.visibility = View.VISIBLE
            else
                binding.contactsEmptyMsg.visibility = View.GONE

            it.let {
                contactsAdapter.submitList(it)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}