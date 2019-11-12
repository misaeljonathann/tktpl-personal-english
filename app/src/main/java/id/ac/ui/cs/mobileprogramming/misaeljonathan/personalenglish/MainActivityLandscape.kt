package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView.HistoryItemFragment
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView.ProfileFragment



class MainActivityLandscape : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_land)

        var actionBar = getSupportActionBar()
        actionBar?.setCustomView(R.layout.actionbar_layout)

        val navbar: BottomNavigationView = findViewById(R.id.navigation)

        navbar.setOnNavigationItemSelectedListener { menuItem ->

            val parentFragment: LinearLayout = findViewById(R.id.linear_layout_fragment_container)
            val leftFragment: LinearLayout = findViewById(R.id.linear_layout_fragment_home)
            val rightFragment: LinearLayout = findViewById(R.id.linear_layout_fragment_add_challenge)

            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    parentFragment.weightSum = 2.toFloat()
                    navigateToOtherMenu(HomepageFragment())
                }

                R.id.navigation_challenge -> {
                    parentFragment.weightSum = 1.toFloat()
                    navigateToOtherMenu(HistoryItemFragment())
                }

                R.id.navigation_profile -> {
                    parentFragment.weightSum = 1.toFloat()
                    navigateToOtherMenu(ProfileFragment())
                }

                else -> {
                    true
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {

        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        super.onConfigurationChanged(newConfig)
    }

    fun navigateToOtherMenu(givenFragment: Fragment): Boolean {
        val fragmentTransaction: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_home, givenFragment, givenFragment.toString())
        fragmentTransaction.commit()
        return true
    }
}