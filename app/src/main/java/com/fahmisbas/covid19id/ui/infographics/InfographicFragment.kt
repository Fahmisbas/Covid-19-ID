package com.fahmisbas.covid19id.ui.infographics

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.Infographics
import com.fahmisbas.covid19id.databinding.FragmentInfographicsBinding
import com.fahmisbas.covid19id.ui.adapter.InfographicsAdapter
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.gone
import com.fahmisbas.covid19id.util.observe
import kotlinx.android.synthetic.main.fragment_infographics.*
import kotlinx.android.synthetic.main.toolbar.*


class InfographicFragment : BaseFragment<InfrographicViewModel, FragmentInfographicsBinding>() {


    private val infographicsAdapter = InfographicsAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch()
    }

    override fun initViews() {
        toolbarTitle.text = getString(R.string.title_infographics)
        rvInfographics.apply {
            adapter = infographicsAdapter
        }
    }

    override fun observeChanges() {
        observe(viewModel.infographics, ::updateInfographicsList)
    }

    private fun updateInfographicsList(list: List<Infographics>) {
        loadingInfographics.gone()
        infographicsAdapter.updateInfographicList(list)
    }

    override fun navigationButton() {
        btnBack.setOnClickListener {
            val action =
                InfographicFragmentDirections.actionInfographicFragmentToDashboardFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getViewModel(): Class<InfrographicViewModel> = InfrographicViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_infographics
}
