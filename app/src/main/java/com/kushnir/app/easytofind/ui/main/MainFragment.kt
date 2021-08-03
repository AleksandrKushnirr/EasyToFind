package com.kushnir.app.easytofind.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.utils.disableBackStack

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableBackStack()
    }
}