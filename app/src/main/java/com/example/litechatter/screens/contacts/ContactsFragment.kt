package com.example.litechatter.screens.contacts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentNearMeBinding

class ContactsFragment : Fragment() {

    private lateinit var contactsRecycler: RecyclerView
    private lateinit var contactsAdapter: RecyclerView.Adapter<*>
    private lateinit var contactsManager: RecyclerView.LayoutManager

    private val contactsDataSet: Array<String> = arrayOf("John Doe", "Omar", "Mr. X")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNearMeBinding>(
            inflater,
            R.layout.fragment_near_me, container, false
        )

        contactsManager = LinearLayoutManager(this.context)
        contactsAdapter = ContactsAdapter(contactsDataSet)

        contactsRecycler = binding.nearMeRecyclerView.apply {
            setHasFixedSize(true)

            layoutManager = contactsManager

            adapter = contactsAdapter
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}

class ContactsAdapter(private val myDataset: Array<String>): RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_listitem_view, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.findViewById<TextView>(R.id.userName).text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}