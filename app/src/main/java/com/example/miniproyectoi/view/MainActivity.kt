package com.example.miniproyectoi.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.miniproyectoi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationContainer) as NavHostFragment
        val navController = navHostFragment.navController

        // Maneja la navegación basada en el Intent recibido
        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "homeFragment") {
            navController.navigate(R.id.homeFragment) // Navega directamente al HomeFragment
        }
    }
}