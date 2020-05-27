package com.fahmisbas.covid19id.view.map

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.databinding.FragmentMapBinding
import com.fahmisbas.covid19id.model.ProvinceResult
import com.fahmisbas.covid19id.util.gone
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.util.visible
import com.fahmisbas.covid19id.view.adapter.ProvinceAdapter
import com.fahmisbas.covid19id.view.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BaseFragment<MapViewModel, FragmentMapBinding>(), OnMapReadyCallback {

    private var isPressed = false;

    private var provinceAdapter =
        ProvinceAdapter(
            arrayListOf()
        )

    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search.visibility = View.GONE
        viewModel.fetch()

        initViews()
        btnAction()
        btnExpand()

    }

    private fun initViews(){
        map?.onCreate(null)
        map?.onResume()
        map?.getMapAsync(this)

        rvProvinces.apply {
            adapter = provinceAdapter
        }
    }

    private fun btnAction() {
        btnToDashboard.setOnClickListener {
            val action = MapFragmentDirections.actionMapFragmentToDashboardFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun btnExpand(){
        btnExpand.setOnClickListener {
            isPressed = if (isPressed) {
                map.gone()
                btnToDashboard.gone()
                search.visible()
                btnExpand.setBackgroundResource(R.drawable.ic_expand_more)
                false
            }else {
                map.visible()
                btnToDashboard.visible()
                search.gone()
                btnExpand.setBackgroundResource(R.drawable.ic_expand_less)
                true
            }
        }
    }

    override fun observeChanges() {
        observe(viewModel.province,::updateData)
        observe(viewModel.error,::displayError)
    }

    private fun updateData(provinceResult: ProvinceResult) {
        provinceAdapter.updateProvinceList(provinceResult.provinceList)
        updateView()
    }

    private fun updateView() {
        progress.gone()
    }

    override fun onMapReady(p0: GoogleMap?) {
        MapsInitializer.initialize(context)
        googleMap = p0

        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,R.raw.style_map)
        googleMap?.setMapStyle(mapStyleOptions)
    }

    override fun getViewModel() = MapViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_map
}
