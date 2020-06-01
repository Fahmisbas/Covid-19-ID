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
import com.fahmisbas.covid19id.data.ProvinceCases
import com.fahmisbas.covid19id.data.ProvinceLocation
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

    private var provinceAdapter = ProvinceAdapter(arrayListOf())

    private var sortedCasesList = arrayListOf<ProvinceCases>()
    private var sortedLocationList = arrayListOf<ProvinceLocation>()

    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetch()



        initViews()
        btnAction()
        searchBar()
        btnExpand()
    }

    override fun onMapReady(p0: GoogleMap?) {
        MapsInitializer.initialize(context)
        googleMap = p0

        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map)
        googleMap?.setMapStyle(mapStyleOptions)
        val indonesia = LatLng(-0.789275,113.9213257)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia,4.0f))


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

    private fun initViews() {
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

    private fun btnExpand() {
        btnExpand.setOnClickListener {
            isPressed = if (isPressed) {
                map.gone()
                btnToDashboard.gone()
                search.visible()
                btnExpand.setBackgroundResource(R.drawable.ic_expand_more)
                false
            } else {
                map.visible()
                btnToDashboard.visible()
                search.gone()
                btnExpand.setBackgroundResource(R.drawable.ic_expand_less)
                true
            }
        }
    }

    override fun observeChanges() {
        observe(viewModel.provinceCases,::updateProvinceCasesRecyclerView)
        observe(viewModel.error,::displayError)

        observe(viewModel.provinceCases, ::sortCasesList)
        observe(viewModel.locations, ::sortLocationsList)
    }

    private fun updateProvinceCasesRecyclerView(provinceCasesCases: List<ProvinceCases>) {
        provinceAdapter.updateProvinceCasesList(provinceCasesCases)
        progress.gone()
    }


    private fun sortCasesList(cases: List<ProvinceCases>) {
        sortedCasesList.clear()
        sortedCasesList.addAll(cases)
        sortedCasesList.sort()
    }

    private fun sortLocationsList(locations: List<ProvinceLocation>) {
        sortedLocationList.clear()
        sortedLocationList.addAll(locations)
        sortedLocationList.sort()

        for (position in 0 until sortedLocationList.size - 1) {
            if (sortedCasesList.size > 0 && sortedLocationList.size > 0) {
                markMap(
                    lat = sortedLocationList[position].lat,
                    lng = sortedLocationList[position].lng,
                    provinceName = sortedCasesList[position].provinceName,
                    positive = sortedCasesList[position].positive,
                    recovered = sortedCasesList[position].recovered,
                    death = sortedCasesList[position].death
                )
            }
        }
    }


    private fun markMap(lat: String, lng: String,provinceName : String,positive : String,recovered : String,death : String) {

        googleMap?.uiSettings?.isMapToolbarEnabled = false
        val latLng = LatLng(lat.toDouble(), lng.toDouble())

        googleMap?.addMarker(
            MarkerOptions().position(latLng).title("($provinceName) Positif : $positive " +
                    "Sembuh : $recovered Meninggal : $death"
            ).icon(
                bitmapDescriptorFromVector(
                    requireContext(), R.drawable.ic_red_circle
                )
            )
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

