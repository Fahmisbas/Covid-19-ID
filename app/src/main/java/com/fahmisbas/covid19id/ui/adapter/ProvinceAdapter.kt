package com.fahmisbas.covid19id.ui.adapter

import android.widget.Filter
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.ProvinceData
import com.fahmisbas.covid19id.databinding.ItemProvinceBinding
import com.fahmisbas.covid19id.ui.base.BaseRecyclerViewAdapter
import java.util.*
import kotlin.collections.ArrayList


class ProvinceAdapter : BaseRecyclerViewAdapter<ProvinceData,ItemProvinceBinding>() {

    private lateinit var provinceCasesListFull: ArrayList<ProvinceData>

    fun sortDescendingOrder(){
        provinceCasesListFull = ArrayList(masterList)
        masterList.sortByDescending {
            it.positive.toInt()
        }
        notifyDataSetChanged()
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
            masterList.clear()
            masterList.addAll(results.values as Collection<ProvinceData>)
            masterList.sortByDescending {
                it.positive.toInt()
            }
            notifyDataSetChanged()
        }
    }

    override fun getLayout() = R.layout.item_province

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemProvinceBinding>,
        position: Int
    ) {
        holder.binding.province = masterList[position]
    }
}