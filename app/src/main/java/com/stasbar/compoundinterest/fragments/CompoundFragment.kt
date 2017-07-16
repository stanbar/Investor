package com.stasbar.compoundinterest.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.stasbar.compoundinterest.CompoundMath
import com.stasbar.compoundinterest.Input
import com.stasbar.compoundinterest.R
import com.stasbar.compoundinterest.dialogs.LabelDialogFragment
import kotlinx.android.synthetic.main.fragment_main.*
import lecho.lib.hellocharts.model.*

/**
 * Created by stasbar on 13.07.2017
 */

class CompoundFragment : BaseFragment() {

    private var mFailed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_main, container, false)
        setTitle(R.string.compound_effect)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart.isInteractive = true
        fab.setOnClickListener { calculateAndShow() }

        setTagsAndListeners()

        spinnerCapitalizationPerYear.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, COMPOUNDS.map { it.name })
        spinnerPeriodType.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, PERIODS.map { it.name })
    }

    private fun setTagsAndListeners() {
        val onTouchListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                showDialogFor(view as EditText, view.getTag() as Input)
                true
            } else false
        }
        editTextPrincipalAmount.tag = Input.AMOUNT
        editTextPrincipalAmount.setOnTouchListener(onTouchListener)
        editTextPeriod.tag = Input.PERIOD
        editTextPeriod.setOnTouchListener(onTouchListener)
        editTextInterest.tag = Input.INTEREST
        editTextInterest.setOnTouchListener(onTouchListener)

    }

    private fun showDialogFor(editText: EditText, input: Input) {
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


    private fun calculateAndShow() {
        collectInputData()

    }

    private fun collectInputData() {
        mFailed = false
        var principalAmount: Double
        try {
            principalAmount = editTextPrincipalAmount.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            editTextPrincipalAmount.error = "Wrong Number !"
            mFailed = true
            principalAmount = 0.0
        }
        val compoundsPerYear: Int = COMPOUNDS[spinnerCapitalizationPerYear.selectedItemPosition].value
        var interest: Double
        try {
            interest = editTextInterest.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            editTextInterest.error = "Wrong Number !"
            mFailed = true
            interest = 0.0
        }
        var period: Int
        try {
            period = editTextPeriod.text.toString().toInt()
        } catch (e: NumberFormatException) {
            editTextPeriod.error = "Wrong Number !"
            mFailed = true
            period = 0
        }
        val periodType: Int = PERIODS[spinnerPeriodType.selectedItemPosition].value
        if (mFailed) {
            Toast.makeText(activity, "Please correct invalid input", Toast.LENGTH_SHORT).show()
            return
        }


        val compoundValues = CompoundMath.calculateHistogram(principalAmount.toFloat(), (interest / 100).toFloat(), compoundsPerYear, period / periodType)
        val nonCompoundValues = CompoundMath.calculateHistogram(principalAmount.toFloat(), 0.0f, compoundsPerYear, period / periodType)
        val compoundPoints = makePoints(compoundValues)
        val nonCompoundPoints = makePoints(nonCompoundValues)
        compoundPoints.forEach { Log.d("compoundPoints", it.toString()) }
        nonCompoundPoints.forEach { Log.d("nonCompoundPoints", it.toString()) }

        show(compoundPoints,compoundValues, nonCompoundPoints, nonCompoundValues)

        textViewAmountAtTheEnd.text = compoundValues.last().toString()
        textViewProfit.text = (compoundValues.last() - principalAmount).toString()

    }


    fun makePoints(values: FloatArray): ArrayList<PointValue> {
        val points = ArrayList<PointValue>()
        (0..values.size-1).mapTo(points) {
            PointValue(it.toFloat(), values[it])
        }
        return points
    }

    private fun show(pointsCompound: ArrayList<PointValue>, valuesCompound : FloatArray, pointsNormal: ArrayList<PointValue>,valuesNormal : FloatArray) {
        val normalLine = Line(pointsNormal).setColor(Color.DKGRAY).setCubic(true).setFilled(true).setStrokeWidth(2)
        val compoundLine = Line(pointsCompound).setColor(Color.BLUE).setCubic(true).setStrokeWidth(2)
        val lines = ArrayList<Line>()
        lines.add(normalLine)
        lines.add(compoundLine)

        val data = LineChartData()
        data.lines = lines
        data.axisYLeft = Axis.generateAxisFromCollection(valuesCompound.asList()).setName("Amount")

        chart.lineChartData = data

    }

    override fun set(input: Input, newValue: String) {
        when (input) {
            Input.AMOUNT -> editTextPrincipalAmount.setText(newValue)
            Input.INTEREST -> editTextInterest.setText(newValue)
            Input.PERIOD -> editTextPeriod.setText(newValue)
        }
    }

    companion object {
        val COMPOUNDS = object : ArrayList<Compound>() {
            init {
                add(Compound("Daily", 365))
                add(Compound("Monthly", 12))
                add(Compound("Quarterly", 4))
                add(Compound("Annually", 1))
            }
        }

        data class Compound(val name: String, val value: Int)

        val PERIODS = object : ArrayList<Period>() {
            init {
                add(Period("Months", 12))
                add(Period("Years", 1))
            }
        }

        data class Period(val name: String, val value: Int)
    }
}
