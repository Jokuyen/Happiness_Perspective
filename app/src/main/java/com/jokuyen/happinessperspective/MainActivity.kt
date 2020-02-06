package com.jokuyen.happinessperspective

import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.jokuyen.happinessperspective.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup "CurrentYear" singleton to be used throughout application
        val c = Calendar.getInstance()
        CurrentYearSingleton.changeCurrentYear(c.get(Calendar.YEAR))

        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)

        // Configure ActionBar
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // Configure navigation drawer
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Set title for each fragment
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title = when (destination.id) {
                R.id.homeFragment -> "Happiness Perspective"
                R.id.newEntryFragment -> "New Entry"

                R.id.entryDetailsFragment -> "Entry Details"

                R.id.progressFragment -> "Progress Chart"
                R.id.settingsFragment -> "Settings"

                else -> " "
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
