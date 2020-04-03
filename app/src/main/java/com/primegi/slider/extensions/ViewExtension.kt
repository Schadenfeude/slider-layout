package com.primegi.slider.extensions

import android.view.View

fun View.onClick(onClick: () -> Unit) = setOnClickListener(DebouncedOnClickListener(onClick))

private class DebouncedOnClickListener(private val onClick: () -> Unit) : View.OnClickListener {
    override fun onClick(view: View) {
        isEnabled || return
        isEnabled = false
        view.post { isEnabled = true }
        onClick()
    }

    companion object {
        var isEnabled = true
    }
}