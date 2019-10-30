package com.dicoding.personalenglish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar = getSupportActionBar()
        val fm = getSupportFragmentManager()
        actionBar?.setCustomView(R.layout.actionbar_layout)

        val navbar: BottomNavigationView = findViewById(R.id.navigation)

        navbar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    goToHomePage()
                }

                R.id.navigation_challenge -> {
                    goToChallengesPage()
                }

                else -> {
                    true
                }
            }
        }

        fm.addOnBackStackChangedListener {

        }
    }

    fun goToHomePage(): Boolean {
        val fragment = HomepageFragment()
        val fragmentTransaction: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_home, fragment, fragment.toString())
        fragmentTransaction.commit()
        return true
    }

    fun goToChallengesPage(): Boolean {
        return true
    }
}