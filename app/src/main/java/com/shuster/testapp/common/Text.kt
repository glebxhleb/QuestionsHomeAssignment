package com.shuster.testapp.common

import android.content.Context
import androidx.annotation.StringRes
import java.io.Serializable

sealed class Text: Serializable {

    abstract fun getString(context: Context): String

    data class Resource(@StringRes val resId: Int) : Text() {

        override fun getString(context: Context) =
            context.getString(resId)
    }
}