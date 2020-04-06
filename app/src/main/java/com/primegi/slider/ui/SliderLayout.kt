package com.primegi.slider.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.primegi.slider.R
import com.primegi.slider.extensions.onClick
import kotlinx.android.synthetic.main.widget_slider.view.*
import kotlin.properties.Delegates

class SliderLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val sliderAdapter by lazy { SliderAdapter() }
    private val sliderLayout by lazy { SliderLayoutManager(context) }
    private val onScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                pickedItem.onScrolling(sliderLayout.findFirstVisibleItemPosition())
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> zoomPill.onScrollFinished(sliderLayout.findFirstVisibleItemPosition())
                }
            }
        }
    }

    var selectItem by Delegates.observable("0") { _, _, newValue ->
        zoomPickerRv.smoothScrollToPosition(sliderAdapter.getPositionFor(newValue))
    }

    init {
        inflate(context, R.layout.widget_slider, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        with(zoomPickerRv) {
            layoutManager = sliderLayout
            adapter = sliderAdapter
            addOnScrollListener(onScrollListener)
        }
        val startingValue = getVisibleItemValue()
        zoomPill.setFormattedText(startingValue)
        pickedItem.setFormattedText(startingValue)

        zoomPill.onClick { toggleSlider() }
        zoomMacroButton.onClick { zoomPickerRv.smoothScrollToPosition(0) }
        zoomInfiniteButton.onClick { zoomPickerRv.smoothScrollToPosition(sliderAdapter.itemCount - 1) }
    }

    fun setValues(values: List<String>): Unit = sliderAdapter.setData(values)

    //region Private
    private fun toggleSlider() = with(sliderGroup) {
        visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
        requestLayout()
    }

    private fun enableZoomButtons(position: Int) {
        zoomMacroButton.isEnabled = position != 0
        zoomInfiniteButton.isEnabled = position != sliderAdapter.itemCount - 1
    }

    private fun getVisibleItemValue(position: Int = sliderLayout.findFirstVisibleItemPosition()): String =
        with(position) {
            if (this == -1) Float.NaN.toString()
            else sliderAdapter.getItemAt(this)
        }

    private fun TextView.setFormattedText(value: Any) {
        text = context.getString(R.string.zoom_value_format, value)
    }

    private fun TextView.onScrolling(position: Int) {
        if (position == -1) return
        else setFormattedText(getVisibleItemValue(position))
    }

    private fun TextView.onScrollFinished(position: Int) {
        onScrolling(position)
        enableZoomButtons(position)
    }
    //endregion Private
}