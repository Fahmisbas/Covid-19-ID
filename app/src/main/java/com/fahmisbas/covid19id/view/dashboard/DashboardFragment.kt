package com.fahmisbas.covid19id.view.dashboard

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.model.Indonesia
import com.fahmisbas.covid19id.util.gone
import com.fahmisbas.covid19id.util.invisible
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.util.visible
import com.fahmisbas.covid19id.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment<DashboardViewModel>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout()
        btnAction()

        hideViews()

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
    }

    private fun updateViews(indonesia: Indonesia) {
        refreshLayout.isRefreshing = false
        progress.gone()
        titleRecovered.visible()
        titlePositive.visible()
        titleDeath.visible()
        titleTotalCase.visible()

        tvTotalCases.text = indonesia.caseNumber
        tvRecovered.text = indonesia.recovered
        tvPositive.text = indonesia.positive
        tvDeath.text = indonesia.death
    }

    private fun btnAction() {
        btnToMap.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToMapFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getViewModel() = DashboardViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_dashboard
}
