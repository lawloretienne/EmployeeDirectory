package com.example.employeedirectory.core.util

import android.content.Context

fun Float.dpToPx(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}