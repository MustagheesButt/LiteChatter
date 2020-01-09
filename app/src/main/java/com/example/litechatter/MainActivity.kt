package com.example.litechatter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.litechatter.databinding.ActivityMainBinding
import com.example.litechatter.screens.chats.ChatsFragment
import com.example.litechatter.screens.contacts.ContactsFragment
import com.example.litechatter.screens.near_me.NearMeFragment
import com.example.litechatter.screens.settings.SettingsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

val fragmentsTitleList: List<String> = listOf("Chats", "Contacts", "Near Me", "Settings")

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
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

class PagerAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return when (position) {
            0 -> ChatsFragment()
            1 -> ContactsFragment()
            2 -> NearMeFragment()
            3 -> SettingsFragment()
            else -> SettingsFragment()
        }
    }
}
