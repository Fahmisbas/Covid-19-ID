package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.Infographics
import com.fahmisbas.covid19id.databinding.ItemInfographicsBinding

class InfographicsAdapter(private val infographicList: ArrayList<Infographics>) :
    RecyclerView.Adapter<InfographicsAdapter.ViewHolder>() {


    fun updateInfographicList(newList: List<Infographics>) {
        infographicList.clear()
        infographicList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfographicsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemInfographicsBinding>(
            inflater,
            R.layout.item_infographics,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return infographicList.size
    }

    override fun onBindViewHolder(holder: InfographicsAdapter.ViewHolder, position: Int) {
        holder.view.infographics = infographicList[position]
    }

    class ViewHolder(var view: ItemInfographicsBinding) : RecyclerView.ViewHolder(view.root) {}
}