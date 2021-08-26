package com.kushnir.app.easytofind.utils

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

fun Fragment.delayOnLifecycle(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    actionBlock: () -> Unit
) {
    viewLifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
        delay(durationInMillis)
        actionBlock()
    }
}

fun Fragment.shareUrl(url: String, title: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, url)
    }
    startActivity(intent)
}