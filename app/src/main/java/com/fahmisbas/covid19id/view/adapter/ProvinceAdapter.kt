package com.fahmisbas.covid19id.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.model.Province


class ProvinceAdapter(private val provincesList : ArrayList<Province>) : RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {


    fun updateProvinceList(newList : List<Province>) {
        provincesList.clear()
        provincesList.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_province,parent,false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return provincesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val province = provincesList[position]

        holder.tvDeath.text = "Meninggal : ${province.death}"
        holder.tvRecovered.text = "Sembuh : ${province.recovered}"
        holder.tvPositive.text = "Positif : ${province.positive}"
        holder.tvProvinceName.text = province.provinceName
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeath = itemView.findViewById<TextView>(R.id.death)
        val tvProvinceName = itemView.findViewById<TextView>(R.id.provinceName)
        val tvRecovered = itemView.findViewById<TextView>(R.id.recovered)
        val tvPositive = itemView.findViewById<TextView>(R.id.positive)
    }

}