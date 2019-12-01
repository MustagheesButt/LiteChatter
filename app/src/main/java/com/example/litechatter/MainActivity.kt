package com.example.litechatter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.litechatter.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    //private lateinit var pagerAdapter: PagerAdapter
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        viewPager.adapter = PagerAdapter()

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                // Styling each tab here
                tab.text = "Tab $position"
            }
        ).attach()
    }
}

class PagerAdapter: RecyclerView.Adapter<PagerAdapter.EventViewHolder>() {
    val eventList = listOf("0", "1", "2")

    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // Layout "layout_viewpager2_cell.xml" will be defined later
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_viewpager2_cell, parent, false))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        (holder.view as? TextView)?.also{
            it.text = "Page " + eventList.get(position)

            val backgroundColorResId = if (position % 2 == 0) R.color.colorPrimary else R.color.colorAccent
            it.setBackgroundColor(ContextCompat.getColor(it.context, backgroundColorResId))
        }
    }

    override fun getItemCount() = eventList.count()
}
