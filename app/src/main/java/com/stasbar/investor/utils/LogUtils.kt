package com.stasbar.investor.utils

import android.util.Log
import com.stasbar.investor.BuildConfig

/**
 * Created by stasbar on 20.07.2017
 */
object LogUtils {

    val LOGTAG = "Inverstor" + BuildConfig.FLAVOR
    val DEBUG = BuildConfig.DEBUG

    fun v(message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.VERBOSE)) {
            Log.v(LOGTAG, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun v(tag: String, message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.VERBOSE)) {
            Log.v(LOGTAG + "/" + tag, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun d(message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.DEBUG)) {
            Log.d(LOGTAG, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun d(tag: String, message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.DEBUG)) {
            Log.d(LOGTAG + "/" + tag, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun i(message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.INFO)) {
            Log.i(LOGTAG, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun i(tag: String, message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.INFO)) {
            Log.i(LOGTAG + "/" + tag, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun w(message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.WARN)) {
            Log.w(LOGTAG, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun w(tag: String, message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.WARN)) {
            Log.w(LOGTAG + "/" + tag, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun e(message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.ERROR)) {
            Log.e(LOGTAG, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun e(tag: String, message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.ERROR)) {
            Log.e(LOGTAG + "/" + tag, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun e(message: String, e: Exception) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.ERROR)) {
            Log.e(LOGTAG, message, e)
        }
    }

    fun e(tag: String, message: String, e: Exception) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.ERROR)) {
            Log.e(LOGTAG + "/" + tag, message, e)
        }
    }

    fun wtf(message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.ASSERT)) {
            Log.wtf(LOGTAG, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }

    fun wtf(tag: String, message: String, vararg args: Any) {
        if (DEBUG || Log.isLoggable(LOGTAG, Log.ASSERT)) {
            Log.wtf(LOGTAG + "/" + tag, if (args.isEmpty())
                message
            else
                String.format(message, *args))
        }
    }
}