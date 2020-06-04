package com.fahmisbas.covid19id.ui.infographics

import android.os.Bundle
import android.view.View
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.FragmentInfographicDetailBinding
import com.fahmisbas.covid19id.ui.base.BaseFragment


class infographicDetailFragment :
    BaseFragment<InfographicDetailViewModel, FragmentInfographicDetailBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getViewModel() = InfographicDetailViewModel::class.java

    override fun getFragmentView(): = R.layout.fragment_infographic_detail

    override fun observeChanges() {

    }


}
