package com.alaturing.incidentify.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alaturing.incidentify.R
import com.alaturing.incidentify.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Implementación de [AppCompatActivity] para albergar la funcionalidad general de la aplicación
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navViewModel: NavigationSharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_navigation_area) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBottomNav.setupWithNavController(navController)

        navViewModel = ViewModelProvider(this)[NavigationSharedViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navViewModel.navigationEvents.collect { event ->
                when (event) {
                    is NavigationEvent.ToIncidents -> {
                        navController.navigate(R.id.incident)
                        navController.navigate(R.id.action_homeFragment_to_incidentsFragment, null,
                            NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build())
                    }
                    is NavigationEvent.ToHome -> {
                        // Perform the navigation to the HomeFragment
                        //navController.navigate(R.id.action_incidentsFragment_to_homeFragment)
                    }
                }
            }
        }
        }
    }
}