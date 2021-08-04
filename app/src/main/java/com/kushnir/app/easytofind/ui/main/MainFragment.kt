package com.kushnir.app.easytofind.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.FragmentMainBinding
import com.kushnir.app.easytofind.utils.disableBackStack
import timber.log.Timber

class MainFragment: Fragment(R.layout.fragment_main) {

    private val viewBinding: FragmentMainBinding by viewBinding()

    private var navController: NavController? = null
    private var navHostFragment: NavHostFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = childFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController
        navController?.let { viewBinding.mainBottomNavigation.setupWithNavController(it) }

        disableBackStack()
    }
}