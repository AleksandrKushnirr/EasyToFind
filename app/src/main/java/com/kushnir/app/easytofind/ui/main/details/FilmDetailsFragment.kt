package com.kushnir.app.easytofind.ui.main.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.FragmentFilmDetailsBinding

class FilmDetailsFragment : Fragment(R.layout.fragment_film_details) {

    private val viewBinding: FragmentFilmDetailsBinding by viewBinding()
    private val navArgs: FilmDetailsFragmentArgs by navArgs()

    private val options: RequestOptions = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.place_holder_top_film_image)
            .error(R.drawable.place_holder_top_film_image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewsTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        viewBinding.apply {
            Glide.with(this@FilmDetailsFragment)
                    .load(navArgs.filmPreview)
                    .apply(options)
                    .into(ivPreview)

            tvTitle.text = navArgs.filmName
        }
    }

    private fun initViewsTransition() {
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }
}