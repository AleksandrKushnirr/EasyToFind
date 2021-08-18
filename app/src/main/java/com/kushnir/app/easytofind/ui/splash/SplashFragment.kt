package com.kushnir.app.easytofind.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.FragmentSplashBinding
import com.kushnir.app.easytofind.utils.delayOnLifecycle

class SplashFragment: Fragment(R.layout.fragment_splash) {

    private val viewBinding: FragmentSplashBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startAnimation()
    }

    private fun startAnimation() {
        viewBinding.ivLogo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_up_to_down))
        viewBinding.ivText.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_down_to_up))

        delayOnLifecycle(2000) {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
    }
}