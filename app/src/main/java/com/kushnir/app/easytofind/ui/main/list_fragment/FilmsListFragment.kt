package com.kushnir.app.easytofind.ui.main.list_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.FragmentListBinding

class FilmsListFragment : Fragment(R.layout.fragment_list)  {

    companion object {
        const val BEST_FILMS_LIST_TYPE = "best_films_list_type"
        const val POPULAR_FILMS_LIST_TYPE = "popular_films_list_type"
        const val AWAIT_FILMS_LIST_TYPE = "await_films_list_type"
    }

    private val viewBinding: FragmentListBinding by viewBinding()
    private val args: FilmsListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}