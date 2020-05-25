package com.fahmisbas.covid19id.view.qa

import android.os.Bundle
import android.view.View
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.model.QandA
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.view.base.BaseFragment


class QandAFragment : BaseFragment<QandAViewModel>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fecth()
    }


    override fun observeChanges() {
        observe(viewModel.qandA, ::updateData)
        observe(viewModel.error, ::displayError)
    }

    private fun updateData(list: List<QandA>) {

    }


    override fun getViewModel() = QandAViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_q_and_a


}
