package com.stasbar.compoundinterest.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stasbar.compoundinterest.Input
import com.stasbar.compoundinterest.R
import kotlinx.android.synthetic.main.fragment_roi.*

/**
 * Created by stasbar on 16.07.2017
 */

class RoiFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_roi, container, false)
        setTitle(R.string.return_of_investment)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun set(input: Input, newValue: String) {
        when (input) {
            Input.INVESTED_AMOUNT -> editTextInvestedAmount.setText(newValue)
            Input.RETURNED_AMOUNT -> editTextReturnAmount.setText(newValue)
            Input.INVESTMENT_TIME -> editTextTimeOfInvestment.setText(newValue)
            else -> {
                throw IllegalStateException("Caught illegal Input for this fragment")
            }
        }
    }


}
