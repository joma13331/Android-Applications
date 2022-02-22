package com.example.pennydrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs =  PreferenceManager.getDefaultSharedPreferences(this)
        val themeId = when(prefs.getString("theme", "AppTheme")){
            "Crew"->R.style.Crew
            "FTD"-> R.style.FTD
            "GPG"->R.style.GPG
            "Hazel"->R.style.Hazel
            "kotlin"->R.style.Kotlin
            else->R.style.AppTheme
        }
        setTheme(themeId)

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment

        this.navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(this.navController)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if(this::navController.isInitialized) {
            item.onNavDestinationSelected(this.navController)
            super.onOptionsItemSelected(item)
        }else false

}