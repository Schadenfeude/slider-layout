package com.primegi.slider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO add your data and widget starting position
        sliderWidget.setValues(List(25) { "${it + 10}" }).also { sliderWidget.selectItem = "15" }
    }
}
