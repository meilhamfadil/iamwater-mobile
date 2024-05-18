package com.akuasih.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.akuasih.app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initLayout()
    }

    private fun initLayout() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_frame) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = viewBinding.navbar
        navView.setupWithNavController(navController)
    }
}