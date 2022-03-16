package com.tighten.turboram.util

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.tighten.turboram.R

fun Toolbar.displayBackButtonAsCloseIcon() {
    navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_close)
}