package com.jaquelinebruzasco.onlynotes

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.jaquelinebruzasco.onlynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setSupportActionBar(_binding.toolbar.toolbar)
        drawerToggle = ActionBarDrawerToggle(
            this,
            _binding.drawerLayout,
            _binding.toolbar.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        _binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_icon)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.findNavController()

        _binding.nvView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_notes -> {
                    navController.navigate(Uri.parse("onlynotes://home"))
                    _binding.drawerLayout.close()
                }
                R.id.nav_categories -> {
                    navController.navigate(Uri.parse("onlynotes://category"))
                    _binding.drawerLayout.close()
                }
                R.id.nav_trash -> {
                    navController.navigate(Uri.parse("onlynotes://trash"))
                    _binding.drawerLayout.close()
                }
            }
            true
        }
    }
}