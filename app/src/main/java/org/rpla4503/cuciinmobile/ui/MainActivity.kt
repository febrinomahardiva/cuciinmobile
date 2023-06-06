package org.rpla4503.cuciinmobile.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.MainPageBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class MainActivity : AppCompatActivity(){
    private lateinit var binding: MainPageBinding
    private lateinit var navigationView: BottomNavigationView
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHandler = DatabaseHandler(this)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        navigationView = binding.bottomNavigationView

        val dashboardFragment = DashboardFragment()
        replaceFragment(dashboardFragment)

        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val dashboardFragment = DashboardFragment()
                    replaceFragment(dashboardFragment)
                    true
                }
                R.id.navigation_contactus -> {
                    val contactUsFragment = ContactUsFragment()
                    replaceFragment(contactUsFragment)
                    true
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment()
                    replaceFragment(profileFragment)
                    true
                } else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}