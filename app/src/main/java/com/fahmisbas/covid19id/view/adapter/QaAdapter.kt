package com.fahmisbas.covid19id.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.model.QandA

class QaAdapter(private val qandAList: ArrayList<QandA>) :
    RecyclerView.Adapter<QaAdapter.ViewHolder>() {

    fun updateProvinceList(newList: List<QandA>) {
        qandAList.clear()
        qandAList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QaAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.activity_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return qandAList.size
    }

    override fun onBindViewHolder(holder: QaAdapter.ViewHolder, position: Int) {

    }

}