package com.fahmisbas.covid19id.view.qa

import android.os.Bundle
import android.view.View
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.FragmentQAndABinding
import com.fahmisbas.covid19id.model.QandA
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.view.adapter.QandAdapter
import com.fahmisbas.covid19id.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_q_and_a.*


class QandAFragment : BaseFragment<QandAViewModel, FragmentQAndABinding>() {

    private var adapter = QandAdapter(arrayListOf())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch()

        initViews()
    }

    private fun initViews() {
        rvQandA.adapter = adapter
    }

    override fun observeChanges() {
        observe(viewModel.qandA, ::updateData)
        observe(viewModel.error, ::displayError)
    }

    private fun updateData(list: List<QandA>) {
        adapter.updateProvinceList(list)
    }

    override fun getViewModel() = QandAViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_q_and_a


}
