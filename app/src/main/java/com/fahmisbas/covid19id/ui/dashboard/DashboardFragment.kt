package com.fahmisbas.covid19id.ui.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.IndonesiaData
import com.fahmisbas.covid19id.databinding.FragmentDashboardBinding
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.gone
import com.fahmisbas.covid19id.util.invisible
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.util.visible
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        hideViews()

        refreshLayout()
        viewModel.fetch()
    }

    private fun hideViews() {
        titleTotalCase.invisible()
        titleDeath.invisible()
        titlePositive.invisible()
        titleRecovered.invisible()
    }

    private fun refreshLayout() {
        refreshLayout.setOnRefreshListener {
            viewModel.fetch()
        }
    }

    override fun observeChanges() {
        observe(viewModel.indonesia, ::updateViews)
        observe(viewModel.error,::displayError)
        observe(viewModel.error, ::updateViews)
    }

    private fun updateViews(indonesiaData: IndonesiaData) {
        indonesiaData.let { result ->
            binding.indonesia = result
            refreshLayout.isRefreshing = false
            progress.gone()
            initViews()
        }
    }

    private fun updateViews(isError: Boolean) {
        if (isError) {
            refreshLayout.isRefreshing = false
            progress.gone()
            errorMessage.text = getString(R.string.errorMessage)
        }
    }

    override fun initViews() {
        titleRecovered.visible()
        titlePositive.visible()
        titleDeath.visible()
        titleTotalCase.visible()
    }

    override fun navigationButton() {
        mapCardView.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToMapFragment()
            findNavController(it).navigate(action)
        }

        qandaCardView.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToQandAFragment()
            findNavController(it).navigate(action)
        }

        infographicCardView.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToInfographicFragment()
            findNavController(it).navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(requireActivity(), R.id.fragment))
                || super.onOptionsItemSelected(item)
    }

    override fun getViewModel() = DashboardViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_dashboard
}
