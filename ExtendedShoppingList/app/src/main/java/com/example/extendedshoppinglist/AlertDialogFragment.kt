package com.example.extendedshoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import edu.skillbox.extendedshoppinglist.AddFragment

class   AlertDialogFragment : DialogFragment() {

    companion object {
        const val REQUEST_KEY_ALERT_DIALOG = "REQUEST_KEY_ALERT_DIALOG"

        const val QUESTION_CODE_REMOVE_ITEM = 1;
        const val QUESTION_CODE_CLEAR_ITEMS = 2;
        const val QUESTION_CODE_ABOUT_INFO = 3;

        const val ALERT_DIALOG_POSITIVE_RESULT = 1;
        const val ALERT_DIALOG_NEGATIVE_RESULT = -1;

        const val KEY_ALERT_DIALOG_RESULT = "KEY_ALERT_DIALOG_RESULT"
        const val KEY_QUESTION_CODE = "KEY_QUESTION_CODE"
        const val KEY_TITLE = "KEY_TITLE";
        const val KEY_MESSAGE = "KEY_MESSAGE";
        const val KEY_POSITIVE_BUTTON_TEXT = "KEY_POSITIVE_BUTTON_TEXT";
        const val KEY_NEGATIVE_BUTTON_TEXT = "KEY_NEGATIVE_BUTTON_TEXT";

        @JvmStatic
        fun newInstance(
            questionCode: Int,
            title: String,
            message: String,
            positiveButtonText: String?,
            negativeButtonText: String?
        ) =
            AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_QUESTION_CODE, questionCode)
                    putString(KEY_TITLE, title)
                    putString(KEY_MESSAGE, message)
                    putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText)
                    putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(arguments?.getString(KEY_TITLE))
            setMessage(arguments?.getString(KEY_MESSAGE))
            val positiveButtonText = arguments?.getString(KEY_POSITIVE_BUTTON_TEXT)
            if (positiveButtonText != null) {
                setPositiveButton(
                    positiveButtonText,
                    { _, _ -> returnAlertDialogResult(ALERT_DIALOG_POSITIVE_RESULT)}
                )
            }
            val negativeButtonText = arguments?.getString(KEY_NEGATIVE_BUTTON_TEXT)
            if (negativeButtonText != null) {
                setNegativeButton(
                    negativeButtonText,
                    { _, _ -> Unit}
                )
            }
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun returnAlertDialogResult(result: Int) {
            val data = Bundle()
            data.putInt(KEY_QUESTION_CODE, arguments?.getInt(KEY_QUESTION_CODE) ?: -1)
            parentFragmentManager.setFragmentResult(REQUEST_KEY_ALERT_DIALOG, data)
    }
}