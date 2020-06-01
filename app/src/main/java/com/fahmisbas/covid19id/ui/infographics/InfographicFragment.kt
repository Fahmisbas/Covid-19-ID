package com.fahmisbas.covid19id.ui.infographics

import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.FragmentInfographicsBinding
import com.fahmisbas.covid19id.ui.base.BaseFragment


class InfographicFragment : BaseFragment<InfrographicViewModel, FragmentInfographicsBinding>() {



    override fun getViewModel(): Class<InfrographicViewModel> = InfrographicViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_infographics

    override fun observeChanges() {

    }


}
