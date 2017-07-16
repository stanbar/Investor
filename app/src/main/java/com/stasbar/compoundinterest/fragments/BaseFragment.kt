package com.stasbar.compoundinterest.fragments

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import com.stasbar.compoundinterest.Input
import com.stasbar.compoundinterest.MainActivity


/**
 * Created by stasbar on 16.07.2017
 */
abstract class BaseFragment : Fragment() {


    fun setTitle(@StringRes title : Int){
        if(activity is MainActivity)
            (activity as MainActivity).title = getString(title)
    }

    abstract fun set(input: Input, newValue: String)
}