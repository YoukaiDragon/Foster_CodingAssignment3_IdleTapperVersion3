package com.example.idletapperversion3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.idletapperversion3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    var taps: Int = 0
    var prestige: Float = 1f
    var tapPower: Int = 1
    var idlePower: Int = 0
    var tapUpgradeSmall: Int = 0
    var tapUpgradeMed: Int = 0
    var tapUpgradeBig: Int = 0
    var idleUpgradeSmall: Int = 0
    var idleUpgradeMed: Int = 0
    var idleUpgradeBig: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}