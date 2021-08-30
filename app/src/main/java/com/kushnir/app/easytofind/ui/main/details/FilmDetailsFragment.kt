package com.kushnir.app.easytofind.ui.main.details

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.databinding.FragmentFilmDetailsBinding
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.CastModel
import com.kushnir.app.easytofind.domain.models.FilmDetailsModel
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import com.kushnir.app.easytofind.domain.models.SimilarFilmModel
import com.kushnir.app.easytofind.ui.main.details.adapters.CastAdapter
import com.kushnir.app.easytofind.ui.main.details.adapters.SimilarFilmsAdapter
import com.kushnir.app.easytofind.ui.main.details.image_pager.ImagePagerAdapter
import com.kushnir.app.easytofind.utils.checkError
import com.kushnir.app.easytofind.utils.shareUrl
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmDetailsFragment : Fragment(R.layout.fragment_film_details) {

    private val viewBinding: FragmentFilmDetailsBinding by viewBinding()
    private val navArgs: FilmDetailsFragmentArgs by navArgs()
    private val viewModel: FilmDetailsViewModel by viewModel()

    private var viewPagerAdapter: ImagePagerAdapter? = null
    private var castAdapter: CastAdapter? = null
    private var similarFilmsAdapter: SimilarFilmsAdapter? = null

    private var filmModel: FilmDetailsModel? = null
    private var isFragmentStopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getFilmDetailsData(navArgs.filmId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        setObservers()
        configureAdapters()
    }

    override fun onStop() {
        super.onStop()

        isFragmentStopped = true
    }

    private fun initViews() {
        viewBinding.apply {
            GravitySnapHelper(Gravity.START).apply {
                maxFlingSizeFraction = 2f
                attachToRecyclerView(recyclerViewCast)
            }
            GravitySnapHelper(Gravity.START).apply {
                maxFlingSizeFraction = 2f
                attachToRecyclerView(recyclerViewSimilar)
            }

            recyclerViewCast.isNestedScrollingEnabled = false
            recyclerViewSimilar.isNestedScrollingEnabled = false
        }
    }

    private fun setListeners() {
        viewBinding.apply {
            imageBtnBack.setOnClickListener { findNavController().popBackStack() }
            imageBtnShare.setOnClickListener {
                filmModel?.let {
                    shareUrl(it.webUrl, it.name)
                }
            }
            viewPagerFilmImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewBinding.pageIndicatorView.setSelected(position)
                }
            })
            checkboxLike.setOnCheckedChangeListener { _, b ->
                filmModel?.let {
                    it.isLiked = b
                    if (b) {
                        viewModel.likeFilm(
                                FilmShortModel(
                                        id = it.filmId,
                                        name = it.name,
                                        year = it.year,
                                        filmLength = it.filmLength,
                                        countries = it.countries,
                                        genres = it.genres,
                                        rating = it.rating.toString(),
                                        poster = it.poster,
                                        ratingColor = it.ratingColor,
                                        isLiked = true
                                )
                        )
                    } else {
                        viewModel.removeLike(it.filmId)
                    }
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.filmDetailsLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    if (!isFragmentStopped) {
                        filmModel = it.value
                    } else {
                        isFragmentStopped = false
                    }
                    filmModel?.let { filmNotNul ->
                        setFilmDetailsInfo(filmNotNul)
                    }
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.filmImagesLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    viewPagerAdapter?.setData(it.value)
                    viewBinding.pageIndicatorView.count = it.value.size
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.filmCastLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    if (it.value.isNotEmpty()) {
                        castAdapter?.setItems(it.value as MutableList<CastModel>)
                    } else {
                        viewBinding.recyclerViewCast.visibility = View.GONE
                        viewBinding.tvCast.visibility = View.GONE
                    }
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.filmSimilarFilmsLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    if (it.value.isNotEmpty()) {
                        similarFilmsAdapter?.setItems(it.value as MutableList<SimilarFilmModel>)
                    } else {
                        viewBinding.recyclerViewSimilar.visibility = View.GONE
                        viewBinding.tvSimilar.visibility = View.GONE
                    }
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.loadingStateLiveData.observe(viewLifecycleOwner, {
            setLoading(it)
        })
    }

    private fun configureAdapters() {
        viewPagerAdapter = ImagePagerAdapter(this)
        viewBinding.viewPagerFilmImages.adapter = viewPagerAdapter

        castAdapter = CastAdapter()
        viewBinding.recyclerViewCast.adapter = castAdapter

        similarFilmsAdapter = SimilarFilmsAdapter(
            { handleClickToSimilarFilm(it) },
            { handleClickToLike(it) }
        )
        viewBinding.recyclerViewSimilar.adapter = similarFilmsAdapter
    }

    private fun setFilmDetailsInfo(model: FilmDetailsModel) {
        viewBinding.apply {
            tvFilmName.text = model.name
            tvYearValue.text = model.year
            if (model.filmLength != null) {
                tvLengthValue.text = model.filmLength
            } else {
                tvLength.visibility = View.GONE
                tvLengthValue.visibility = View.GONE
            }

            var countries = ""
            for (country in model.countries) {
                countries += "$country, "
            }
            tvCountryValue.text = if (countries.length > 2) countries.subSequence(0, countries.length - 2).toString() else ""

            var genres = ""
            for (genre in model.genres) {
                genres += "$genre, "
            }
            tvGenresValue.text = if (genres.length > 2) genres.subSequence(0, genres.length - 2).toString() else ""

            if (model.description != null) {
                tvDetailsValue.text = model.description
            } else {
                tvDetailsValue.visibility = View.GONE
            }

            cvRatingHolder.apply {
                when(model.ratingColor) {
                    RatingColor.GREEN -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_green))
                    RatingColor.LIME -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_lime))
                    RatingColor.YELLOW -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_yellow))
                    RatingColor.ORANGE -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_orange))
                    RatingColor.RED -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_red))
                }
            }
            tvRating.text = model.rating.toString()

            viewPagerAdapter?.setMainPoster(model.poster)

            checkboxLike.isChecked = model.isLiked
        }
    }

    private fun handleClickToSimilarFilm(model: SimilarFilmModel) {
        findNavController().navigate(
            R.id.action_filmDetailsFragment_self,
            bundleOf("film_id" to model.filmId)
        )
    }

    private fun handleClickToLike(id: Int) {
        // todo Лайкинг
    }

    private fun setLoading(isLoading: Boolean) {
        viewBinding.shimmerLoading.visibility =
            if (isLoading) View.VISIBLE else View.GONE
        viewBinding.layoutContent.visibility =
            if (isLoading) View.GONE else View.VISIBLE
    }
}