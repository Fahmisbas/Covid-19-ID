package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.Province
import com.fahmisbas.covid19id.databinding.ItemProvinceBinding


class ProvinceAdapter(private val provinceList: ArrayList<Province>) :
    RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {

    private lateinit var provinceListFull: ArrayList<Province>

    fun updateProvinceList(newList : List<Province>) {
        provinceListFull = ArrayList(newList)
        provinceList.clear()
        provinceList.addAll(newList)
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
        return provinceList.size
    }

    fun getFiler(): Filter {
        return provinceFilter
    }

    private val provinceFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<Province> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(provinceListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in provinceListFull) {
                    if (item.provinceName!!.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            provinceList.clear()
            provinceList.addAll(results.values as Collection<Province>)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.province = provinceList[position]
    }

    class ViewHolder(var view: ItemProvinceBinding) : RecyclerView.ViewHolder(view.root) {}

}