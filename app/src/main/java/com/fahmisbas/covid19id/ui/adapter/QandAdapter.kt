package com.fahmisbas.covid19id.ui.adapter

import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.QandA
import com.fahmisbas.covid19id.databinding.ItemQAndABinding
import com.fahmisbas.covid19id.ui.base.BaseRecyclerViewAdapter

class QandAdapter : BaseRecyclerViewAdapter<QandA,ItemQAndABinding>() {

    override fun getLayout(): Int = R.layout.item_q_and_a
    override fun onBindViewHolder(holder: Companion.BaseViewHolder<ItemQAndABinding>, position: Int) {
        holder.binding.qAndA = masterList[position]
    }
}