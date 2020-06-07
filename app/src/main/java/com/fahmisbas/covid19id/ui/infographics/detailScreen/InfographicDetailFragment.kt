package com.fahmisbas.covid19id.ui.infographics.detailScreen

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.fahmisbas.covid19id.R
import com.fahmisbas.covid19id.data.model.ColorPalette
import com.fahmisbas.covid19id.databinding.FragmentInfographicDetailBinding
import com.fahmisbas.covid19id.ui.base.BaseFragment
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import com.fahmisbas.covid19id.util.gone
import kotlinx.android.synthetic.main.fragment_infographic_detail.*
import kotlinx.android.synthetic.main.toolbar.*


class InfographicDetailFragment :
    BaseFragment<InfographicDetailFragment.InfographicDetailViewModel, FragmentInfographicDetailBinding>() {

    private lateinit var url: String
    private lateinit var title: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendImage()
    }
    override fun getViewModel() = InfographicDetailViewModel::class.java
    override fun getFragmentView() = R.layout.fragment_infographic_detail
    override fun observeChanges() {
        arguments?.let {
            url = InfographicDetailFragmentArgs.fromBundle(it).itemUrl
            title = InfographicDetailFragmentArgs.fromBundle(it).itemTitle
            binding.infographicUrl = url
            binding.title = title
            setupBackgroundColor(url)
            progress.gone()
        }
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate {
                        val intColor = it?.darkVibrantSwatch?.rgb ?: 0
                        val bgPalette = ColorPalette(intColor)
                        binding.palette = bgPalette
                    }
                }
            })
    }

    override fun navigationButton() {
        btnBack.setOnClickListener {
            val action =
                InfographicDetailFragmentDirections.actionInfographicDetailFragmentToInfographicFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun sendImage() {
        send.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            context?.startActivity(Intent.createChooser(shareIntent, "Share to"))
        }
    }

    override fun initViews() {
        toolbarTitle.text = getString(R.string.title_detail_infographic)
    }

    class InfographicDetailViewModel(application: Application) : BaseViewModel(application) {
        override fun fetch() {}
    }
}

