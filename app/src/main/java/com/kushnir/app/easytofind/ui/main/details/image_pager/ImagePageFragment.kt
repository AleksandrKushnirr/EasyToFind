package com.kushnir.app.easytofind.ui.main.details.image_pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.PageFragmentFilmImageBinding
import com.kushnir.app.easytofind.domain.models.ImageModel

class ImagePageFragment : Fragment(R.layout.page_fragment_film_image) {

    companion object {
        private const val KEY_IMAGE_URL = "key_image_url"
        private const val KEY_PREVIEW_URL = "key_preview_url"

        fun newInstance(image: ImageModel) : ImagePageFragment {
            return ImagePageFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_IMAGE_URL, image.image)
                    putString(KEY_PREVIEW_URL, image.preview)
                }
            }
        }
    }

    private val options: RequestOptions = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .placeholder(R.drawable.place_holder_large_film_image)
        .error(R.drawable.place_holder_large_film_image)

    private val viewBinding: PageFragmentFilmImageBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = arguments?.getString(KEY_IMAGE_URL)
        val previewUrl = arguments?.getString(KEY_PREVIEW_URL)

        Glide.with(this)
            .load(imageUrl)
            .thumbnail(Glide.with(this).load(previewUrl))
            .apply(options)
            .into(viewBinding.ivFilmImage)
    }
}