package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.ProvinceData
import com.fahmisbas.covid19id.databinding.ItemProvinceBinding
import java.util.*
import kotlin.collections.ArrayList


class ProvinceAdapter(private val provinceCasesList: ArrayList<ProvinceData>) :
    RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {

    private lateinit var provinceCasesListFull: ArrayList<ProvinceData>

    fun updateProvinceCasesList(newList: List<ProvinceData>) {
        provinceCasesListFull = ArrayList(newList)
        provinceCasesList.clear()
        provinceCasesList.addAll(newList)
        provinceCasesList.sortByDescending {
            it.positive.toInt()
        }
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
            val filteredList: ArrayList<ProvinceData> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(provinceCasesListFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.getDefault()).trim { it <= ' ' }
                for (item in provinceCasesListFull) {
                    if (item.provinceName.toLowerCase(Locale.getDefault())
                            .contains(filterPattern)
                    ) {
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
            provinceCasesList.addAll(results.values as Collection<ProvinceData>)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.province = provinceCasesList[position]
    }

    class ViewHolder(var view: ItemProvinceBinding) : RecyclerView.ViewHolder(view.root) {}

}