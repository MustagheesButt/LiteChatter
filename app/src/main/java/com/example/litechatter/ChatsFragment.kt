package com.example.litechatter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {
    private lateinit var chatsRecycler: RecyclerView
    private lateinit var chatsAdapter: RecyclerView.Adapter<*>
    private lateinit var chatsManager: RecyclerView.LayoutManager

    private val chatsDataSet: Array<String> = arrayOf("John Doe", "Omar", "Mr. X")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentChatsBinding>(inflater, R.layout.fragment_chats, container, false)

        chatsManager = LinearLayoutManager(this.context)
        chatsAdapter = ChatsAdapter(chatsDataSet)

        chatsRecycler = binding.chatsRecyclerView.apply {
            setHasFixedSize(true)

            layoutManager = chatsManager

            adapter = chatsAdapter
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}

class ChatsAdapter(private val myDataset: Array<String>): RecyclerView.Adapter<ChatsAdapter.MyViewHolder>() {

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
            .inflate(R.layout.chat_view, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.findViewById<TextView>(R.id.textView).text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}

