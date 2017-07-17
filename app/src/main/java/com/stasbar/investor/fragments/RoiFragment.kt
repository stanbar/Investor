package com.stasbar.investor.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.stasbar.investor.InvestorMath
import com.stasbar.investor.Input
import com.stasbar.investor.R
import kotlinx.android.synthetic.main.fragment_main.*
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
        setupSpinners()
    }

    override fun setupSpinners() {
        spinnerTimeOfInvestmentType.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, CompoundFragment.PERIODS.map { it.name })
    }



    override fun setTagsAndListeners() {
        fab.setOnClickListener { calculateAndShow() }

        val onTouchListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                showDialogFor(view as EditText, view.getTag() as Input)
                true
            } else false
        }
        editTextInvestedAmount.tag = Input.INVESTED_AMOUNT
        editTextInvestedAmount.setOnTouchListener(onTouchListener)
        editTextReturnAmount.tag = Input.RETURNED_AMOUNT
        editTextReturnAmount.setOnTouchListener(onTouchListener)
        editTextTimeOfInvestment.tag = Input.INVESTMENT_TIME
        editTextTimeOfInvestment.setOnTouchListener(onTouchListener)
    }

    private fun calculateAndShow() {


    }

    private fun collectInputData() {
        mFailed = false
        var investmentAmount: Double = 0.0
        try {
            investmentAmount = editTextInvestedAmount.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            editTextInvestedAmount.error = "Wrong Number !"
            mFailed = true
        }

        var returnAmount: Double = 0.0
        try {
            returnAmount = editTextReturnAmount.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            editTextReturnAmount.error = "Wrong Number !"
            mFailed = true
        }


        //TODO("Parse TimeOfInvestment")
        val timeOfInvestment: Int = CompoundFragment.PERIODS[spinnerTimeOfInvestmentType.selectedItemPosition].value
        if (mFailed) {
            Toast.makeText(activity, "Please correct invalid input", Toast.LENGTH_SHORT).show()
            return
        }

        val returnOfInvestment = InvestorMath.returnOfInvestment(investmentAmount,returnAmount)
        val investmentGain = InvestorMath.investmentGain(investmentAmount,returnAmount)
        val annualizedROI = InvestorMath.annualizedReturnOfInvestment(investmentAmount,returnAmount,timeOfInvestment)
        showResults(returnOfInvestment, investmentGain,annualizedROI)


    }

    private fun showResults(returnOfInvestment: Double, investmentGain: Double, annualizedROI: Double) {
        textViewROI.text = returnOfInvestment.toString()
        textViewProfit.text = investmentGain.toString()
        textViewAnnualROI.text = annualizedROI.toString()

    }

    override fun set(input: Input, newValue: String) {
        when (input) {
            Input.INVESTED_AMOUNT -> editTextInvestedAmount.setText(newValue)
            Input.RETURNED_AMOUNT -> editTextReturnAmount.setText(newValue)
            Input.INVESTMENT_TIME -> editTextTimeOfInvestment.setText(newValue)
            else -> throw IllegalStateException("Caught illegal Input for this fragment")
        }
    }



}
