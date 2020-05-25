package com.fahmisbas.covid19id.view.qa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.view.base.BaseFragment


class QandAFragment : BaseFragment<QandAViewModel>() {
    

    override fun getViewModel() = QandAViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_q_and_a

    override fun observeChanges() {
    }


}
