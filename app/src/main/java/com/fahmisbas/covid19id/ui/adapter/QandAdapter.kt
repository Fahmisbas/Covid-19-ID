package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.ItemQAndABinding
import com.fahmisbas.covid19id.model.QandA

class QandAdapter(private val qandAList: ArrayList<QandA>) :
    RecyclerView.Adapter<QandAdapter.ViewHolder>() {

    fun updateProvinceList(newList: List<QandA>) {
        qandAList.clear()
        qandAList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QandAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemQAndABinding>(
            inflater,
            R.layout.item_q_and_a,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return qandAList.size
    }

    override fun onBindViewHolder(holder: QandAdapter.ViewHolder, position: Int) {
        holder.view.qAndA = qandAList[position]

    }

    class ViewHolder(var view: ItemQAndABinding) : RecyclerView.ViewHolder(view.root) {}


}