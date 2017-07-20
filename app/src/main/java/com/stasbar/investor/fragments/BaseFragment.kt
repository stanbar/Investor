package com.stasbar.investor.fragments

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.EditText
import com.stasbar.investor.Input
import com.stasbar.investor.MainActivity
import com.stasbar.investor.dialogs.LabelDialogFragment


/**
 * Created by stasbar on 16.07.2017
 */
abstract class BaseFragment : Fragment() {
    protected var mFailed = false
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinners()
        setTagsAndListeners()
    }

    abstract protected fun setupSpinners()
    abstract protected fun setTagsAndListeners()


    protected fun setTitle(@StringRes title : Int){
        if(activity is MainActivity)
            (activity as MainActivity).title = getString(title)
    }

    protected fun showDialogFor(editText: EditText, input: Input) {
        val fragmentManager = fragmentManager
        fragmentManager.executePendingTransactions()
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag("label_dialog")
        if (prev != null) ft.remove(prev)
        ft.addToBackStack(null)
        val newFragment = LabelDialogFragment.newInstance(input, editText.text.toString())
        newFragment.setTargetFragment(this, 0)
        newFragment.show(ft, "label_dialog")
    }

    abstract fun set(input: Input, newValue: String)
}