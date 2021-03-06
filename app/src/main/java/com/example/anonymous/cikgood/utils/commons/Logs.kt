package com.ismealdi.hidoc.utils.commons

import android.util.Log
import com.example.anonymous.cikgood.BuildConfig

/**
 * Created by Al
 * on 06/04/19 | 01:48
 */
object Logs {
    fun e(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e("Am error", "AmMsg: $msg")
        }
    }

    fun d(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d("Am debug", "AmMsg: $msg")
        }
    }

    fun v(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.v("Am verbose","AmMsg: $msg")
        }
    }

    fun i(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i("Am info","AmMsg: $msg")
        }
    }

    fun w(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.w("Am warn","AmMsg: $msg")
        }
    }

    fun log(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e("Am","AmMsg: $msg")
        }
    }

    fun db(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e("AmDatabase", msg)
        }
    }

    fun fireBase(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e("AmFireBase", msg)
        }
    }
}