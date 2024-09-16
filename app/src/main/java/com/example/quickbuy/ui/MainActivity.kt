package com.example.quickbuy.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quickbuy.BookmarkFragment
import com.example.quickbuy.HomeFragment
import com.example.quickbuy.NotificationFragment
import com.example.quickbuy.ProfileFragment
import com.example.quickbuy.R
import com.example.quickbuy.SearchFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var home: ImageView
    private lateinit var search: ImageView
    private lateinit var favourite: ImageView
    private lateinit var profile: ImageView
    private lateinit var fab: FloatingActionButton
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the views from custom bottom navigation
        home = findViewById(R.id.home)
        search = findViewById(R.id.search)
        favourite = findViewById(R.id.favourite)
        profile = findViewById(R.id.profile)
        fab = findViewById(R.id.fab)

        // Load the default fragment (Home)
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, homeFragment)
                .commit()
            activeFragment = homeFragment
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        // Home button click
        home.setOnClickListener {
            switchFragment(HomeFragment(), R.id.home)
        }

        // Search button click
        search.setOnClickListener {
            switchFragment(SearchFragment(), R.id.search)
        }

        // Favourite button click
        favourite.setOnClickListener {
            switchFragment(BookmarkFragment(), R.id.favourite)
        }

        // Profile button click
        profile.setOnClickListener {
            switchFragment(ProfileFragment(), R.id.profile)
        }

        // FloatingActionButton (FAB) click
        fab.setOnClickListener {
            switchFragment(NotificationFragment(), R.id.fab)
        }

        // Set default selected item
        updateNavBar(R.id.home)
    }

    private fun switchFragment(newFragment: Fragment, selectedItemId: Int) {
        // Replace fragment only if the new fragment is different
        if (activeFragment == null || newFragment::class != activeFragment!!::class) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, newFragment)
                commit()
            }
            activeFragment = newFragment
            updateNavBar(selectedItemId)
        } else {
            Toast.makeText(this, "Already on the selected page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNavBar(selectedItemId: Int) {
        // Reset all icons to default state
        resetNavBarIcons()

        // Change the icon and background for the selected item
        when (selectedItemId) {
            R.id.home -> {
                home.apply {
                    setImageResource(R.drawable.ic_home)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.search -> {
                search.apply {
                    setImageResource(R.drawable.ic_search)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.favourite -> {
                favourite.apply {
                    setImageResource(R.drawable.ic_favourite)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.profile -> {
                profile.apply {
                    setImageResource(R.drawable.ic_profile)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.fab -> {
                fab.apply {
                    setImageResource(R.drawable.ic_add)
                    setBackgroundResource(R.drawable.circle_background)

                    // Set background tint
                    val backgroundColor = ContextCompat.getColor(context, R.color.white)
                    backgroundTintList = ColorStateList.valueOf(backgroundColor)
                }
            }
        }
    }

    private fun resetNavBarIcons() {
        home.apply {
            setImageResource(R.drawable.ic_home_border)
            background = null
        }
        search.apply {
            setImageResource(R.drawable.ic_search_border)
            background = null
        }
        favourite.apply {
            setImageResource(R.drawable.ic_favorite_border)
            background = null
        }
        profile.apply {
            setImageResource(R.drawable.ic_profile_border)
            background = null
        }
        fab.apply {
            setImageResource(R.drawable.ic_add_border)
            val backgroundColor = ContextCompat.getColor(context, R.color.darkGray)
            backgroundTintList = ColorStateList.valueOf(backgroundColor)
        }
    }
}
