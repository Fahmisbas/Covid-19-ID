package com.fahmisbas.covid19id.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fahmisbas.covid19id.R
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.toolbar.*


class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.title_about_fragment)
        btnBack.setOnClickListener {
            val action = AboutFragmentDirections.actionAboutFragmentToDashboardFragment()
            Navigation.findNavController(it).navigate(action)
        }

        whoWebsite()
        dataSource()
    }

    private fun dataSource() {
        btnToDataSource.setOnClickListener {
            val toDataSource = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://indonesia-covid-19.mathdro.id/api")
            )
            startActivity(toDataSource)
        }
    }

    private fun whoWebsite() {
        btnToWhoWeb.setOnClickListener {
            val toWhoSite = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.who.int/indonesia/news/novel-coronavirus")
            )
            startActivity(toWhoSite)
        }
    }

}
