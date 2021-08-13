package com.kushnir.app.easytofind.ui.main.list_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.databinding.FragmentListBinding
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import com.kushnir.app.easytofind.ui.main.list_fragment.adapter.FilmsListAdapter
import com.kushnir.app.easytofind.utils.checkError
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsListFragment : Fragment(R.layout.fragment_list)  {

    companion object {
        const val BEST_FILMS_LIST_TYPE = "best_films_list_type"
        const val POPULAR_FILMS_LIST_TYPE = "popular_films_list_type"
        const val AWAIT_FILMS_LIST_TYPE = "await_films_list_type"
    }

    private val viewBinding: FragmentListBinding by viewBinding()
    private val viewModel: FilmsListViewModel by viewModel()
    private val args: FilmsListFragmentArgs by navArgs()

    private var adapter: FilmsListAdapter? = null

    private var currentPage = 0
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getFilms(args.listType, 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        configureAdapter()
        setObservers()
    }

    private fun initViews() {
        viewBinding.apply {
            tvTitle.text =
                    when (args.listType) {
                        BEST_FILMS_LIST_TYPE -> resources.getString(R.string.tv_top_best_films)
                        POPULAR_FILMS_LIST_TYPE -> resources.getString(R.string.tv_top_popular_films)
                        AWAIT_FILMS_LIST_TYPE -> resources.getString(R.string.tv_top_await_films)
                        else -> ""
                    }
        }
    }

    private fun setListeners() {
        viewBinding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setObservers() {
        viewModel.loadingStateLiveData.observe(viewLifecycleOwner, {
            setLoading(it)
        })

        viewModel.filmsLiveData.observe(viewLifecycleOwner, { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    currentPage++
                    totalPages = result.value.pagesCount
                    if (currentPage == 1) {
                        adapter?.setItems(result.value.films as MutableList<FilmShortModel>)
                    } else {
                        adapter?.addItems(result.value.films as MutableList<FilmShortModel>)
                    }
                }
                is ResultWrapper.Error -> checkError(result)
            }
        })
    }

    private fun configureAdapter() {
        adapter = FilmsListAdapter(
                { filmModel, transitionView ->
                    handleClickToFilm(filmModel, transitionView)
                },
                { handleClickToLikeFilm(it) },
                { handleLoadMore() }
        )
        viewBinding.recyclerView.adapter = adapter
    }

    private fun handleClickToFilm(model: FilmShortModel, transitionView: View) {
        findNavController().navigate(
                R.id.action_filmsListFragment_to_filmDetailsFragment,
                bundleOf(
                        "film_name" to model.name,
                        "film_id" to model.id,
                        "film_preview" to model.posterUrl
                ),
                null,
                FragmentNavigatorExtras(transitionView to resources.getString(R.string.transition_name_preview_to_details))
        )
    }

    private fun handleClickToLikeFilm(filmId: Int) {
        Toast.makeText(context, "handleClickToLikeFilm: $filmId", Toast.LENGTH_SHORT).show()
    }

    private fun handleLoadMore() {
        if (totalPages >= currentPage) {
            viewModel.getFilms(args.listType, currentPage + 1)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        viewBinding.shimmerLoading.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        viewBinding.layoutContent.visibility =
                if (isLoading) View.INVISIBLE else View.VISIBLE
    }
}