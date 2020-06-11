package com.fahmisbas.covid19id.ui.adapter

import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.Infographics
import com.fahmisbas.covid19id.databinding.ItemInfographicsBinding
import com.fahmisbas.covid19id.ui.base.BaseRecyclerViewAdapter
import com.fahmisbas.covid19id.ui.infographics.InfographicFragmentDirections

class InfographicsAdapter : BaseRecyclerViewAdapter<Infographics,ItemInfographicsBinding>(){

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<ItemInfographicsBinding>, position: Int) {
        holder.binding.infographics = masterList[position]
        clickEvent(holder,position,masterList[position])
    }

    private fun clickEvent(holder: BaseRecyclerViewAdapter.Companion.BaseViewHolder<ItemInfographicsBinding>, position: Int, infographics: Infographics) {
        holder.binding.picOne.setOnClickListener { navigateWithData(it, infographics.picOne, infographics.title) }
        holder.binding.picTwo.setOnClickListener { navigateWithData(it, infographics.picTwo, infographics.title) }
        holder.binding.picThree.setOnClickListener { navigateWithData(it, infographics.picThree, infographics.title) }
        holder.binding.picFour.setOnClickListener { navigateWithData(it, infographics.picFour, infographics.title) }
    }

    private fun navigateWithData(it: View?, picUrl: String, title: String) {
        it?.run {
            val action =
                InfographicFragmentDirections.actionInfographicFragmentToInfographicDetailFragment()
            action.itemUrl = picUrl
            action.itemTitle = title
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getLayout() = R.layout.item_infographics

}


