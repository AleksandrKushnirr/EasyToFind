package com.kushnir.app.easytofind.ui.main.liked

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.databinding.FragmentListBinding
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import com.kushnir.app.easytofind.ui.main.liked.adapter.FilmsLikedListAdapter
import com.kushnir.app.easytofind.utils.checkError
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedFilmsFragment : Fragment(R.layout.fragment_list) {

    private val viewBinding: FragmentListBinding by viewBinding()
    private val viewModel: LikedFilmsViewModel by viewModel()
    private var adapter: FilmsLikedListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
        configureAdapter()

        viewModel.getLikedFilms()
    }

    private fun initViews() {
        viewBinding.apply {
            btnBack.visibility = View.INVISIBLE
            tvTitle.text = resources.getString(R.string.tv_my_favorite_films)
        }
    }

    private fun setObservers() {
        viewModel.loadingStateLiveData.observe(viewLifecycleOwner, {
            setLoading(it)
        })

        viewModel.filmLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultWrapper.Success -> adapter?.setItems(it.value as MutableList<FilmShortModel>)
                is ResultWrapper.Error -> checkError(it)
            }
        })
    }

    private fun configureAdapter() {
        adapter = FilmsLikedListAdapter(
                { handleClickToFilm(it) },
                { film, isLiked -> handleClickToLikeFilm(film, isLiked) }
        )
        viewBinding.recyclerView.adapter = adapter
    }

    private fun handleClickToFilm(id: Int) {
        findNavController().navigate(
                R.id.action_likedFilmsFragment_to_filmDetailsFragment,
                bundleOf("film_id" to id)
        )
    }

    private fun handleClickToLikeFilm(film: FilmShortModel, isLiked: Boolean) {
        if (isLiked) {
            viewModel.likeFilm(film)
        } else {
            viewModel.removeLike(film.id)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        viewBinding.shimmerLoading.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        viewBinding.layoutContent.visibility =
                if (isLoading) View.INVISIBLE else View.VISIBLE
    }

}