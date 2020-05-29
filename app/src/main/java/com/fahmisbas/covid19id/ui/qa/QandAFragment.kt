package com.fahmisbas.covid19id.ui.qa

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.MythBuster
import com.fahmisbas.covid19id.data.QandA
import com.fahmisbas.covid19id.databinding.FragmentQAndABinding
import com.fahmisbas.covid19id.ui.adapter.MythBusterAdapter
import com.fahmisbas.covid19id.ui.adapter.QandAdapter
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.observe
import kotlinx.android.synthetic.main.fragment_q_and_a.*


class QandAFragment : BaseFragment<QandAViewModel, FragmentQAndABinding>() {

    private var adapter = QandAdapter(arrayListOf())
    private var vpAdapter = MythBusterAdapter(arrayListOf())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch()

        initViews()
        btnAction()
    }

    private fun btnAction() {
        btnToDashboard.setOnClickListener {
            val action = QandAFragmentDirections.actionQandAFragmentToDashboardFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun initViews() {
        rvQandA.adapter = adapter
        viewPagerMythBuster.adapter = vpAdapter
        pageIndicator.setupWithViewPager(viewPagerMythBuster, true)
    }

    override fun observeChanges() {
        observe(viewModel.qandA, ::updateQandAData)
        observe(viewModel.mythBuster, ::updateMythBusterData)
        observe(viewModel.error, ::displayError)

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
