package com.example.calcnumeric.presenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.DynamicColors
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        setTheme(R.style.AppTheme_Monet)
        DynamicColors.applyToActivityIfAvailable(this)
        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController() ?: return
        navView.setupWithNavController(navController)
    }

    private fun findNavController(): NavController? {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main
        ) as? NavHostFragment

        return navHostFragment?.navController
    }
}
