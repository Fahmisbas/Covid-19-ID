package com.fahmisbas.covid19id.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.ProvinceData
import com.fahmisbas.covid19id.databinding.FragmentMapBinding
import com.fahmisbas.covid19id.ui.adapter.ProvinceAdapter
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.util.gone
import com.fahmisbas.covid19id.util.observe
import com.fahmisbas.covid19id.util.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BaseFragment<MapViewModel, FragmentMapBinding>(), OnMapReadyCallback {

    private var isPressed = false;
    val province = arrayListOf<ProvinceData>()

    private var provinceAdapter = ProvinceAdapter(arrayListOf())
    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetch()
        initViews()
    }

    override fun onMapReady(p0: GoogleMap?) {
        MapsInitializer.initialize(context)
        googleMap = p0
        googleMap?.uiSettings?.isMapToolbarEnabled = false
        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map)
        googleMap?.setMapStyle(mapStyleOptions)

        val indonesia = LatLng(-0.789275, 113.9213257)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 3.7f))

    }

    private fun initViews() {
        map?.onCreate(null)
        map?.onResume()
        map?.getMapAsync(this)

        rvProvinces.apply {
            adapter = provinceAdapter
        }

        btnAction()
        searchBar()
        btnExpand()
    }

    private fun searchBar() {
        search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                provinceAdapter.getFiler().filter(newText)
                return false
            }
        })
    }

    private fun btnAction() {
        btnToDashboard.setOnClickListener {
            val action = MapFragmentDirections.actionMapFragmentToDashboardFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun btnExpand() {
        btnExpand.setOnClickListener {
            isPressed = if (isPressed) {
                map.gone()
                btnToDashboard.gone()
                backButtonBackground.gone()
                search.visible()
                btnExpand.setBackgroundResource(R.drawable.ic_expand_more)
                false
            } else {
                map.visible()
                btnToDashboard.visible()
                backButtonBackground.visible()
                search.gone()
                btnExpand.setBackgroundResource(R.drawable.ic_expand_less)
                true
            }
        }
    }

    override fun observeChanges() {
        observe(viewModel.provinceData, ::updateRecyclerViewList)
        observe(viewModel.error, ::displayError)

        observe(viewModel.provinceData, ::setMapData)

    }

    private fun updateRecyclerViewList(provinceCasesCases: List<ProvinceData>) {
        provinceAdapter.updateProvinceCasesList(provinceCasesCases)
        progress.gone()
    }

    private fun setMapData(list: List<ProvinceData>) {
        for (provinceData in list) {
            if (list.isNotEmpty()) {
                markMap(
                    lat = provinceData.lat,
                    lng = provinceData.lng,
                    provinceName = provinceData.provinceName,
                    positive = provinceData.positive,
                    recovered = provinceData.recovered,
                    death = provinceData.death
                )
            }
        }
    }

    private fun markMap(
        lat: String, lng: String, provinceName: String, positive: String,
        recovered: String,
        death: String
    ) {

        val location = LatLng(lat.toDouble(), lng.toDouble())

        googleMap?.addMarker(
            MarkerOptions().position(location).title(
                "($provinceName) " +
                        "Positif : $positive " +
                        "Sembuh : $recovered " +
                        "Meninggal : $death"
            ).icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_red_circle))
        )
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        return if (vectorDrawable != null) {
            vectorDrawable.setBounds(
                0, 0, vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight
            )
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bitmap)
        } else {
            null
        }
    }

    override fun getViewModel() = MapViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_map
}

