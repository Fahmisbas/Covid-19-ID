package com.fahmisbas.covid19id.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.fahmisbas.covid19id.data.model.ProvinceCases
import com.fahmisbas.covid19id.data.model.ProvinceLocations
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.*


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 20f
        centerRadius = 50f
        backgroundColor = android.R.color.background_light
        start()
    }
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

fun sortedProvinceCasesList(result: List<ProvinceCases>): ArrayList<ProvinceCases> {
    val sorted = arrayListOf<ProvinceCases>()
    if (result.isNotEmpty()) {
        sorted.clear()
        sorted.addAll(result)
        sorted.sort()
    }
    return sorted
}

fun sortedLocationsList(result: List<ProvinceLocations>): ArrayList<ProvinceLocations> {
    val sorted = arrayListOf<ProvinceLocations>()
    if (result.isNotEmpty()) {
        sorted.clear()
        sorted.addAll(result)
        sorted.sort()
    }
    return sorted
}


fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    return if (vectorDrawable != null) {
        vectorDrawable.setBounds(
            0, 0, vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    } else {
        null
    }
}