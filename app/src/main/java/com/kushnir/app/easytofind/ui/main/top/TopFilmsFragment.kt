package com.kushnir.app.easytofind.ui.main.top

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.databinding.FragmentTopFilmsBinding
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import com.kushnir.app.easytofind.ui.main.list_fragment.FilmsListFragment
import com.kushnir.app.easytofind.ui.main.top.adapter.TopFilmsAdapter
import com.kushnir.app.easytofind.utils.checkError
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopFilmsFragment : Fragment(R.layout.fragment_top_films) {

    private val viewBinding: FragmentTopFilmsBinding by viewBinding()
    private val viewModel: TopFilmsViewModel by viewModel()

    private var bestFilmsAdapter: TopFilmsAdapter? = null
    private var popularFilmsAdapter: TopFilmsAdapter? = null
    private var awaitFilmsAdapter: TopFilmsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        setObservers()
        configureAdapters()

        viewModel.getContent()
    }

    private fun initViews() {
        viewBinding.apply {
            GravitySnapHelper(Gravity.START).apply {
                maxFlingSizeFraction = 2f
                attachToRecyclerView(recyclerViewBestFilms)
            }
            GravitySnapHelper(Gravity.START).apply {
                maxFlingSizeFraction = 2f
                attachToRecyclerView(recyclerViewPopularFilms)
            }
            GravitySnapHelper(Gravity.START).apply {
                maxFlingSizeFraction = 2f
                attachToRecyclerView(recyclerViewAwaitFilms)
            }

            recyclerViewBestFilms.isNestedScrollingEnabled = false
            recyclerViewPopularFilms.isNestedScrollingEnabled = false
            recyclerViewAwaitFilms.isNestedScrollingEnabled = false
        }
    }

    private fun setListeners() {
        viewBinding.apply {
            btnViewMoreBest.setOnClickListener {
                findNavController().navigate(
                        R.id.action_topFilmsFragment_to_filmsListFragment,
                        bundleOf("list_type" to FilmsListFragment.BEST_FILMS_LIST_TYPE)
                )
            }

            btnViewMorePopular.setOnClickListener {
                findNavController().navigate(
                        R.id.action_topFilmsFragment_to_filmsListFragment,
                        bundleOf("list_type" to FilmsListFragment.POPULAR_FILMS_LIST_TYPE)
                )
            }

            btnViewMoreAwaite.setOnClickListener {
                findNavController().navigate(
                        R.id.action_topFilmsFragment_to_filmsListFragment,
                        bundleOf("list_type" to FilmsListFragment.AWAIT_FILMS_LIST_TYPE)
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.topBestFilmsLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    bestFilmsAdapter?.setItems(it.value as MutableList<FilmShortModel>)
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.topPopularFilmsLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    popularFilmsAdapter?.setItems(it.value as MutableList<FilmShortModel>)
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.topAwaitFilmsLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> {
                    awaitFilmsAdapter?.setItems(it.value as MutableList<FilmShortModel>)
                }
                is ResultWrapper.Error -> checkError(it)
            }
        })

        viewModel.loadingStateLiveData.observe(viewLifecycleOwner, {
            setLoading(it)
        })
    }

    private fun configureAdapters() {
        bestFilmsAdapter = TopFilmsAdapter(
                { handleClickToFilm(it) },
                { handleClickToLikeFilm(it) }
        )
        viewBinding.recyclerViewBestFilms.adapter = bestFilmsAdapter

        popularFilmsAdapter = TopFilmsAdapter(
                { handleClickToFilm(it) },
                { handleClickToLikeFilm(it) }
        )
        viewBinding.recyclerViewPopularFilms.adapter = popularFilmsAdapter

        awaitFilmsAdapter = TopFilmsAdapter(
                { handleClickToFilm(it) },
                { handleClickToLikeFilm(it) }
        )
        viewBinding.recyclerViewAwaitFilms.adapter = awaitFilmsAdapter
    }

    private fun handleClickToFilm(model: FilmShortModel) {
        Toast.makeText(context, "handleClickToFilm: $model", Toast.LENGTH_SHORT).show()
    }

    private fun handleClickToLikeFilm(filmId: Int) {
        Toast.makeText(context, "handleClickToLikeFilm: $filmId", Toast.LENGTH_SHORT).show()
    }

    private fun setLoading(isLoading: Boolean) {
        viewBinding.shimmerLoading.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        viewBinding.layoutContent.visibility =
                if (isLoading) View.INVISIBLE else View.VISIBLE
    }
}