package com.example.proj3

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AlertDialogFragment : DialogFragment() {
    companion object {
        const val REQUEST_KEY_ALERT_DIALOG = "REQUEST_KEY_ALERT_DIALOG"

        const val KEY_TITLE = "KEY_TITLE";
        const val KEY_MESSAGE = "KEY_MESSAGE";
        const val KEY_POSITIVE_BUTTON_TEXT = "KEY_POSITIVE_BUTTON_TEXT";
        const val KEY_NEGATIVE_BUTTON_TEXT = "KEY_NEGATIVE_BUTTON_TEXT";

        @JvmStatic
        fun newInstance(
            title: String,
            message: String,
            positiveButton: String?,
            negativeButton: String? /*null check to show button*/
        ) = AlertDialogFragment().apply {
            arguments = Bundle().apply {
                //TO-DO FOR FEATURE MB?
                putString(KEY_TITLE, title)
                putString(KEY_MESSAGE, message)
                putString(KEY_POSITIVE_BUTTON_TEXT, positiveButton)
                putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButton)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(arguments?.getString(KEY_TITLE))
            setMessage(arguments?.getString(KEY_MESSAGE))
            val posButtonText = arguments?.getString(KEY_POSITIVE_BUTTON_TEXT)
            if (posButtonText != null) {
                setPositiveButton(posButtonText) { _, _ -> Unit } //Kogda nado bydet 4to-to vernut' zamenit' Unit na buttonListener()
            }
            val negButtonText = arguments?.getString(KEY_NEGATIVE_BUTTON_TEXT)
            if (negButtonText != null) {
                setNegativeButton(negButtonText) { _, _ -> Unit }
            }
        }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun buttonListener() {
        //TO-DO FOR FEATURE MB?
    }
}