package com.kushnir.app.easytofind.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.models.responses.BaseErrorMessage
import com.kushnir.app.easytofind.databinding.DialogErrorBinding

class ErrorDialog: DialogFragment() {

    private val viewBinding: DialogErrorBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_error, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTexts()

        viewBinding.btnOk.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setTexts() {
        val error: BaseErrorMessage? = requireArguments().getParcelable("error")
        val errorBaseMessage: String? = requireArguments().getString("error_string")

        if (error != null) {
            viewBinding.tvDetails.text = getErrorTextFromErrorModel(error)
        } else {
            if (!errorBaseMessage.isNullOrEmpty()) {
                viewBinding.tvDetails.text = "$errorBaseMessage\n"
            } else {
                viewBinding.tvDetails.text = "${resources.getString(R.string.txt_error)}\n"
            }
        }

    }

    private fun getErrorTextFromErrorModel(baseErrorMessage: BaseErrorMessage): String {
        var resultError = ""
        baseErrorMessage.apply {
            errorMessages?.let {
                for (message in it) resultError += "$message\n"
            }
            errors?.let {
                for (message in it) resultError += "$message\n"
            }
            errorMessage?.let {
                resultError += "$it\n"
            }
        }
        return resultError
    }
}