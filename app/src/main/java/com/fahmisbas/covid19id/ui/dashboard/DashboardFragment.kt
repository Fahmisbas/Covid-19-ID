package com.fahmisbas.covid19id.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.Indonesia
import com.fahmisbas.covid19id.databinding.FragmentDashboardBinding
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.gone
import com.fahmisbas.covid19id.util.invisible
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.util.visible
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideViews()

        refreshLayout()
        btnActions()

        viewModel.refresh()
    }

    private fun hideViews() {
        titleTotalCase.invisible()
        titleDeath.invisible()
        titlePositive.invisible()
        titleRecovered.invisible()
    }


    private fun refreshLayout() {
        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun observeChanges() {
        observe(viewModel.indonesia, ::updateViews)
        observe(viewModel.error,::displayError)
        observe(viewModel.error, ::updateViews)
    }

    private fun updateViews(indonesia: Indonesia) {
        indonesia.let { indonesiaData ->
            binding.indonesia = indonesiaData
            refreshLayout.isRefreshing = false
            progress.gone()
            showViews()
        }
    }

    private fun updateViews(isError: Boolean) {
        if (isError) {
            refreshLayout.isRefreshing = false
            progress.gone()
        }
    }

    private fun showViews() {
        titleRecovered.visible()
        titlePositive.visible()
        titleDeath.visible()
        titleTotalCase.visible()
    }

    private fun btnActions() {
        mapCardView.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToMapFragment()
            Navigation.findNavController(it).navigate(action)
        }

        qandaCardView.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToQandAFragment()
            Navigation.findNavController(it).navigate(action)
        }

        infographicCardView.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToInfographicFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    override fun getViewModel() = DashboardViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_dashboard
}
