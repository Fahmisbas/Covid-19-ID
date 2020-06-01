package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.ProvinceCases
import com.fahmisbas.covid19id.databinding.ItemProvinceBinding


class ProvinceAdapter(private val provinceCasesList: ArrayList<ProvinceCases>) :
    RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {


    private lateinit var provinceCasesListFull: ArrayList<ProvinceCases>


    fun updateProvinceCasesList(newList : List<ProvinceCases>) {
        provinceCasesListFull = ArrayList(newList)
        provinceCasesList.clear()
        provinceCasesList.addAll(newList)
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
        return provinceCasesList.size
    }

    fun getFiler(): Filter {
        return provinceFilter
    }

    private val provinceFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<ProvinceCases> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(provinceCasesListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in provinceCasesListFull) {
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
            provinceCasesList.clear()
            provinceCasesList.addAll(results.values as Collection<ProvinceCases>)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.province = provinceCasesList[position]
    }

    class ViewHolder(var view: ItemProvinceBinding) : RecyclerView.ViewHolder(view.root) {}

}