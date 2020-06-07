package com.fahmisbas.covid19id.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.Infographics
import com.fahmisbas.covid19id.databinding.ItemInfographicsBinding
import com.fahmisbas.covid19id.ui.infographics.InfographicFragmentDirections

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
        clickEvent(holder, position, infographicList[position])
    }

    private fun clickEvent(
        holder: InfographicsAdapter.ViewHolder,
        position: Int,
        infographics: Infographics
    ) {
        holder.view.picOne.setOnClickListener {
            navigateWithData(
                it,
                infographics.picOne,
                infographics.title
            )
        }
        holder.view.picTwo.setOnClickListener {
            navigateWithData(
                it,
                infographics.picTwo,
                infographics.title
            )
        }
        holder.view.picThree.setOnClickListener {
            navigateWithData(
                it,
                infographics.picThree,
                infographics.title
            )
        }
        holder.view.picFour.setOnClickListener {
            navigateWithData(
                it,
                infographics.picFour,
                infographics.title
            )
        }
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

    class ViewHolder(var view: ItemInfographicsBinding) : RecyclerView.ViewHolder(view.root) {}

}


