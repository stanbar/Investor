package com.stasbar.investor

import android.support.annotation.StringRes

/**
 * Created by stasbar on 13.07.2017
 */
public enum class Input(val decimal: Boolean, @StringRes val title: Int, @StringRes val message: Int) {
    //Compoud Effect
    AMOUNT(true, R.string.amount_title, R.string.amount_message),
    PERIOD(false, R.string.period_title, R.string.period_message),
    INTEREST(true, R.string.interest_title, R.string.interest_message),
    // ROI
    INVESTED_AMOUNT(true, R.string.investment_amount_title, R.string.investment_amount_message),
    RETURNED_AMOUNT(false, R.string.return_amount_title, R.string.return_amount_message),
    INVESTMENT_TIME(true, R.string.investment_time_title, R.string.investment_time_message);



}