package com.fahmisbas.covid19id.ui.qa

import android.os.Bundle
import android.view.View
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.FragmentQAndABinding
import com.fahmisbas.covid19id.model.MythBuster
import com.fahmisbas.covid19id.model.QandA
import com.fahmisbas.covid19id.ui.adapter.MythBusterAdapter
import com.fahmisbas.covid19id.ui.adapter.QandAdapter
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.observe
import kotlinx.android.synthetic.main.fragment_q_and_a.*


class QandAFragment : BaseFragment<QandAViewModel, FragmentQAndABinding>() {

    private var adapter = QandAdapter(arrayListOf())
    private var vpAdapter = MythBusterAdapter(arrayListOf())
    private var jumpPosition: Int = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch()

        initViews()
    }

    private fun initViews() {
        rvQandA.adapter = adapter
        viewPagerMythBuster.adapter = vpAdapter
    }

    override fun observeChanges() {
        observe(viewModel.qandA, ::updateQandAData)
        observe(viewModel.error, ::displayError)
        observe(viewModel.mythBuster, ::updateMythBusterData)
    }

    private fun updateMythBusterData(list: List<MythBuster>) {
        vpAdapter.updateMythBusterList(list)
    }

    private fun updateQandAData(list: List<QandA>) {
        adapter.updateProvinceList(list)
    }

    override fun getViewModel() = QandAViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_q_and_a


}
