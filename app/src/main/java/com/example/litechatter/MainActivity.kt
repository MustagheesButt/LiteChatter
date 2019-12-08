package com.example.litechatter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.litechatter.databinding.ActivityMainBinding
import com.example.litechatter.screens.chats.ChatsFragment
import com.example.litechatter.screens.contacts.ContactsFragment
import com.example.litechatter.screens.near_me.NearMeFragment
import com.example.litechatter.screens.settings.SettingsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

val fragmentsList: List<Fragment> = listOf(
    ChatsFragment(),
    ContactsFragment(),
    NearMeFragment(),
    SettingsFragment()
)
val fragmentsTitleList: List<String> = listOf("Chats", "Contacts", "Near Me", "Settings")

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    //private lateinit var pagerAdapter: PagerAdapter
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        viewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                // Styling each tab here
                tab.text = fragmentsTitleList[position]
            }
        ).attach()
    }
}

//class PagerAdapter: RecyclerView.Adapter<PagerAdapter.EventViewHolder>() {
//    val eventList = listOf("0", "1", "2")
//
//    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)
//
//    // Layout "layout_viewpager2_cell.xml" will be defined later
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_viewpager2_cell, parent, false))
//
//    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
//        (holder.view as? TextView)?.also{
//            it.text = "Page " + eventList.get(position)
//
//            val backgroundColorResId = if (position % 2 == 0) R.color.colorPrimary else R.color.colorAccent
//            it.setBackgroundColor(ContextCompat.getColor(it.context, backgroundColorResId))
//        }
//    }
//
//    override fun getItemCount() = eventList.count()
//}

class PagerAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = fragmentsList[position]

        return fragment
    }
}