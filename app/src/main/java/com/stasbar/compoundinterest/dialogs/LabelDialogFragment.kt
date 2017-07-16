package com.stasbar.compoundinterest.dialogs


import android.app.Dialog
import android.widget.EditText
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.text.InputType
import com.stasbar.compoundinterest.R
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import com.stasbar.compoundinterest.Input
import com.stasbar.compoundinterest.fragments.BaseFragment
import com.stasbar.compoundinterest.fragments.CompoundFragment

/**
 * Created by stasbar on 13.07.2017
 */
class LabelDialogFragment : DialogFragment() {
    companion object {
        val CURRENT_VALUE_KEY = "current_value_key"
        val CURRENT_INPUT_KEY = "current_input_key"
        fun newInstance(input: Input, currentValue: String): LabelDialogFragment {
            val args = Bundle()
            args.putString(CURRENT_VALUE_KEY, currentValue)
            args.putSerializable(CURRENT_INPUT_KEY, input)
            val frag = LabelDialogFragment()
            frag.arguments = args
            return frag
        }

    }

    private lateinit var mLabelBox: EditText
    lateinit var input: Input
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity

        input = arguments.getSerializable(CURRENT_INPUT_KEY) as Input
        setupEditText()

        val alertDialog = AlertDialog.Builder(context)
                .setView(mLabelBox)
                .setTitle(input.title)
                .setMessage(input.message)
                .setPositiveButton(R.string.set, OkListener())
                .setNegativeButton(R.string.cancel, CancelListener())
                .create()
        if (alertDialog.window != null)
            alertDialog.window.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE);
        return alertDialog

    }

    private fun setupEditText() {
        mLabelBox = EditText(context)
        mLabelBox.setOnEditorActionListener(ImeDoneListener())
        mLabelBox.setSingleLine()

        val value = arguments.getString(CURRENT_VALUE_KEY)
        if (input.decimal) mLabelBox.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        else mLabelBox.inputType = InputType.TYPE_CLASS_NUMBER
        mLabelBox.setText(value)
        mLabelBox.selectAll()
    }


    /**
     * Handles completing the label edit from the IME keyboard.
     */
    private inner class ImeDoneListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                (targetFragment as BaseFragment).set(input, mLabelBox.text.toString())
                dismiss()
                return true
            }
            return false
        }
    }

    /**
     * Handles completing the label edit from the Ok button of the dialog.
     */
    private inner class OkListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            (targetFragment as BaseFragment).set(input, mLabelBox.text.toString())
            dismiss()
        }
    }

    /**
     * Handles discarding the label edit from the Cancel button of the dialog.
     */
    private inner class CancelListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            dismiss()
        }
    }


}