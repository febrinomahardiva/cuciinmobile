package org.rpla4503.cuciinmobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.rpla4503.cuciinmobile.ui.ContactUsFragment
import org.rpla4503.cuciinmobile.ui.DashboardFragment
import org.rpla4503.cuciinmobile.ui.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        bottomNavigation = findViewById(R.id.mobile_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.navigation_dashboard -> DashboardFragment()
                R.id.navigation_contactus -> ContactUsFragment()
                R.id.navigation_profile -> ProfileFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commit()
            }

            true
        }
    }
}





