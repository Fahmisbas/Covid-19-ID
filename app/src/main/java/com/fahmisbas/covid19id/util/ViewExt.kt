package com.fahmisbas.covid19id.util

import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable



fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun ImageView.loadImage(url : String?, progressDrawable: CircularProgressDrawable){

}