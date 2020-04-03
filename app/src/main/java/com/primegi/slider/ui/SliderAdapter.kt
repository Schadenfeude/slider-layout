package com.primegi.slider.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.primegi.slider.R

internal class SliderAdapter : RecyclerView.Adapter<SliderItemViewHolder>() {

    private var data: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemViewHolder =
        SliderItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_widget_slider, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SliderItemViewHolder, position: Int) {}

    fun getItemAt(position: Int): String = if (data.isNotEmpty()) data[position] else "0"

    fun getPositionFor(item: String): Int = if (data.indexOf(item) != -1) data.indexOf(item) else 0

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }
}

internal class SliderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)