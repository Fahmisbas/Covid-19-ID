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
        clickEvent(holder, position)
    }

    private fun clickEvent(holder: InfographicsAdapter.ViewHolder, position: Int) {
        holder.view.picOne.setOnClickListener {
            val picUrl = infographicList[position].picOne
            val title = infographicList[position].title
            navigateWithData(it, picUrl, title)
        }

        holder.view.picTwo.setOnClickListener {
            val picUrl = infographicList[position].picTwo
            val title = infographicList[position].title
            navigateWithData(it, picUrl, title)
        }

        holder.view.picThree.setOnClickListener {
            val picUrl = infographicList[position].picThree
            val title = infographicList[position].title
            navigateWithData(it, picUrl, title)
        }

        holder.view.picFour.setOnClickListener {
            val picUrl = infographicList[position].picFour
            val title = infographicList[position].title
            navigateWithData(it, picUrl, title)
        }
    }

    private fun navigateWithData(it: View?, picUrl: String, title: String) {
        if (it != null) {
            val action =
                InfographicFragmentDirections.actionInfographicFragmentToInfographicDetailFragment()
            action.itemUrl = picUrl
            action.itemTitle = title
            Navigation.findNavController(it).navigate(action)
        }
    }


    class ViewHolder(var view: ItemInfographicsBinding) : RecyclerView.ViewHolder(view.root) {}

}


