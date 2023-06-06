package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.MainPageBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler

class MainActivity : AppCompatActivity(){
    private lateinit var binding: MainPageBinding
    private lateinit var navigationView: BottomNavigationView
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHandler = DatabaseHandler(this)

        navigationView = binding.bottomNavigationView

        val dashboardFragment = DashboardFragment()
        replaceFragment(dashboardFragment)

        navigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val dashboardFragment = DashboardFragment()
                    replaceFragment(dashboardFragment)
                }
                R.id.navigation_contactus -> {
                    val contactUsFragment = ContactUsFragment()
                    replaceFragment(contactUsFragment)
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment()
                    replaceFragment(profileFragment)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}