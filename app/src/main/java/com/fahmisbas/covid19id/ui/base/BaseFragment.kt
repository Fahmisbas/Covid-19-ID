package com.fahmisbas.covid19id.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahmisbas.covid19id.util.makeToast

abstract class BaseFragment<VM : ViewModel, T : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel : VM
    protected lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getFragmentView(), container, false)
        viewModel = ViewModelProvider(this).get(getViewModel())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChanges()
    }

    abstract fun getViewModel() : Class<VM>

    abstract fun getFragmentView() : Int

    abstract fun observeChanges()

    fun displayError(isError : Boolean) {
        if (isError) {
            context?.makeToast("something went wrong")
        }
    }
}