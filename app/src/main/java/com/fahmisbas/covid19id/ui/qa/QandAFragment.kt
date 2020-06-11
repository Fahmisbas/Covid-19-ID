package com.fahmisbas.covid19id.ui.qa

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.MythBuster
import com.fahmisbas.covid19id.data.model.QandA
import com.fahmisbas.covid19id.databinding.FragmentQAndABinding
import com.fahmisbas.covid19id.ui.adapter.MythBusterAdapter
import com.fahmisbas.covid19id.ui.adapter.QandAdapter
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.observe
import kotlinx.android.synthetic.main.fragment_q_and_a.*
import kotlinx.android.synthetic.main.toolbar.*


class QandAFragment : BaseFragment<QandAViewModel, FragmentQAndABinding>() {

    private var adapter = QandAdapter()
    private var vpAdapter = MythBusterAdapter(arrayListOf())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch()
    }

    override fun navigationButton() {
        btnBack.setOnClickListener {
            val action = QandAFragmentDirections.actionQandAFragmentToDashboardFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun initViews() {
        toolbarTitle.text = getString(R.string.title_q_and_a_)
        rvQandA.adapter = adapter
        viewPagerMythBuster.adapter = vpAdapter
        pageIndicator.setupWithViewPager(viewPagerMythBuster, true)
    }

    override fun observeChanges() {
        observe(viewModel.qandA, ::updateQandAList)
        observe(viewModel.mythBuster, ::updateMythBusterList)
        observe(viewModel.error, ::displayError)

    }

    private fun updateMythBusterList(list: List<MythBuster>) {
        vpAdapter.updateMythBusterList(list)
    }

    private fun updateQandAList(list: List<QandA>) {
        adapter.updateItems(list)
    }

    override fun getViewModel() = QandAViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_q_and_a
}
