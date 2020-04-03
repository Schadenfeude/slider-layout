package com.primegi.slider.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

internal class SliderLayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
) :
    LinearLayoutManager(context) {

    init {
        this.orientation = orientation
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        LinearSnapHelper().attachToRecyclerView(view)
    }
}