package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.newsapp.databinding.ActivityMainBinding

/**
 * <h1>MainActivity</h1>
 *<p>
 * Main activity that runs all the fragments of NewsApp application
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class MainActivity : AppCompatActivity() {
    // Drawer layout
    private lateinit var drawerLayout: DrawerLayout

    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Data binding
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout     // Initializes the drawer layout

        // navController
        val navController = this.findNavController(R.id.navStart)

        // Setup of return arrow in the appbar when navigating
        NavigationUI.setupActionBarWithNavController(this,navController)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    /**
     * Adds functionality to the return arrow
     * @return Boolean
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navStart)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}