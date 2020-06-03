package com.fahmisbas.covid19id.ui.infographics

import android.os.Bundle
import android.view.View
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.Infographics
import com.fahmisbas.covid19id.databinding.FragmentInfographicsBinding
import com.fahmisbas.covid19id.ui.adapter.InfographicsAdapter
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.observe
import kotlinx.android.synthetic.main.fragment_infographics.*


class InfographicFragment : BaseFragment<InfrographicViewModel, FragmentInfographicsBinding>() {


    val infographicsAdapter = InfographicsAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.fetch()
    }

    private fun initViews() {
        rvInfographics.apply {
            adapter = infographicsAdapter
        }
    }

    override fun observeChanges() {
        observe(viewModel.infographics, ::updateInfographicsList)
    }

    private fun updateInfographicsList(list: List<Infographics>) {
        infographicsAdapter.updateInfographicList(list)
    }


    override fun getViewModel(): Class<InfrographicViewModel> = InfrographicViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_infographics




}
