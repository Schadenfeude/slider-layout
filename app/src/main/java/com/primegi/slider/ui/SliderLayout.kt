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
                val middleItem = sliderLayout.findFirstVisibleItemPosition()

                if (middleItem == -1) return
                else pickedItem.setPillText(getVisibleItemValue())
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> zoomPill.setPillText(getVisibleItemValue())
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
        zoomPill.setPillText(startingValue)
        pickedItem.setPillText(startingValue)

        zoomPill.onClick { toggleSlider() }
        zoomMacroButton.onClick { zoomPickerRv.smoothScrollToPosition(0) }
        zoomInfiniteButton.onClick { zoomPickerRv.smoothScrollToPosition(sliderAdapter.itemCount - 1) }
    }

    fun setValues(values: List<String>): Unit = sliderAdapter.setData(values)

    private fun toggleSlider() {
        if (sliderGroup.isVisible) sliderGroup.visibility = View.INVISIBLE
        else sliderGroup.visibility = View.VISIBLE
        sliderGroup.requestLayout()
    }

    private fun TextView.setPillText(value: Any) {
        text = context.getString(R.string.zoom_value_format, value)
    }

    private fun getVisibleItemValue(): String {
        val visiblePosition = sliderLayout.findFirstVisibleItemPosition()

        return if (visiblePosition == -1) Float.NaN.toString()
        else sliderAdapter.getItemAt(sliderLayout.findFirstVisibleItemPosition())
    }
}