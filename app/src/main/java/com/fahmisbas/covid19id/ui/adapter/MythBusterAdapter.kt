package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.MythBuster
import com.fahmisbas.covid19id.util.getProgressDrawable
import com.fahmisbas.covid19id.util.loadImage

class MythBusterAdapter(private val mythBusterList: ArrayList<MythBuster>) : PagerAdapter() {

    fun updateMythBusterList(newList: List<MythBuster>) {
        mythBusterList.clear()
        mythBusterList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mythBusterList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_mythbuster, container, false)

        val imgView = view.findViewById<ImageView>(R.id.imgMythBuster)

        imgView.loadImage(mythBusterList[position].url, getProgressDrawable(imgView.context))

        container.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}