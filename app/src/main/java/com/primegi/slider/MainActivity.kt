package com.primegi.slider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO add your data and widget starting position
        sliderWidget.setValues(List(25) { "${it + 10}.0" }.reversed())
            .also { sliderWidget.selectItem = "15" }
    }
}
