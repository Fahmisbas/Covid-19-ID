package com.fahmisbas.covid19id.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment <VM : ViewModel> : Fragment() {

    protected lateinit var viewModel : VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(getViewModel())
        return inflater.inflate(getFragmentView(),container,false)
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
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}