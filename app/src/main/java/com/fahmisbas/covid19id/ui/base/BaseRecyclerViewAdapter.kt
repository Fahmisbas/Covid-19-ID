package com.fahmisbas.covid19id.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T : Any, VB : ViewDataBinding>(
    protected var masterList: ArrayList<T> = arrayListOf()
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.Companion.BaseViewHolder<VB>>() {

    fun updateItems(items: List<T>) {
        masterList = ArrayList(items)
        masterList.clear()
        masterList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = masterList.size

    abstract fun getLayout() : Int

    var listener : ((view : View, item : T, position : Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB> (
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayout(),
            parent, false
        )
    )

    companion object{
        class BaseViewHolder<VB : ViewDataBinding>(val binding : VB) :
                RecyclerView.ViewHolder(binding.root)
    }
}