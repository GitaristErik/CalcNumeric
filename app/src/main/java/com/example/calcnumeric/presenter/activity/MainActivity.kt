package com.example.calcnumeric.presenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.ActivityMainBinding
import com.example.calcnumeric.presenter.fragment.settings.ThemeService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        startThemeJob()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupNavController()
    }

    private fun startThemeJob() {
        lifecycleScope.launch {
            ThemeService.loadAndSetTheme(this@MainActivity)
            ThemeService.theme.collect {
                if (it == ThemeService.ThemeType.MONET)
                    window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this@MainActivity)
            }
        }
    }

    private fun setupNavController() {
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
