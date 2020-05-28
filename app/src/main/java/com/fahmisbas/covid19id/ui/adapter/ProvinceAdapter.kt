package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.ItemProvinceBinding
import com.fahmisbas.covid19id.model.Province


class ProvinceAdapter(private val provincesList : ArrayList<Province>) : RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {


    fun updateProvinceList(newList : List<Province>) {
        provincesList.clear()
        provincesList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemProvinceBinding>(
            inflater,
            R.layout.item_province,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return provincesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.province = provincesList[position]
    }

    class ViewHolder(var view: ItemProvinceBinding) : RecyclerView.ViewHolder(view.root) {}

}