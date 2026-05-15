package com.nammahasiru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nammahasiru.ui.addplant.AddPlantFragment
import com.nammahasiru.ui.alerts.AlertsFragment
import com.nammahasiru.ui.guide.GuideFragment
import com.nammahasiru.ui.home.HomeFragment
import com.nammahasiru.ui.map.MapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // show home screen when app opens
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, HomeFragment())
                .commit()
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_add -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, AddPlantFragment())
                        .commit()
                    true
                }
                R.id.nav_map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, MapFragment())
                        .commit()
                    true
                }
                R.id.nav_guide -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, GuideFragment())
                        .commit()
                    true
                }
                R.id.nav_alerts -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, AlertsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
