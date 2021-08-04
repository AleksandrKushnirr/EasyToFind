package com.kushnir.app.easytofind.utils

import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper

fun Fragment.disableBackStack() {
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
    )
}

fun Fragment.checkError(error: ResultWrapper.Error?) {
    error?.let {
        val bundle =
            when {
                it.error != null -> {
                    bundleOf("error" to it.error)
                }
                it.throwableMessage == "ConnectException" -> {
                    bundleOf("error_string" to resources.getString(R.string.txt_failed_connect_error))
                }
                else -> {
                    bundleOf("error_string" to it.throwableMessage)
                }
            }
        findNavController().navigate(R.id.errorDialog, bundle)
    }
}