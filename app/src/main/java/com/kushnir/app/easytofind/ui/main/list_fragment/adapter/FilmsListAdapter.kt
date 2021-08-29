package com.kushnir.app.easytofind.ui.main.list_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.ItemFilmLargeBinding
import com.kushnir.app.easytofind.databinding.ItemLoadingBinding
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.FilmShortModel

class FilmsListAdapter(
        private val clickToFilm: (Int) -> Unit,
        private val clickToLike: (FilmShortModel, Boolean) -> Unit,
        private val loadMore: () -> Unit
): RecyclerView.Adapter<FilmsListAdapter.FilmsListViewHolder>() {

    private val items: MutableList<FilmShortModel?> = mutableListOf()
    private var isDataEnd: Boolean = false

    fun setItems(items: MutableList<FilmShortModel>) {
        this.items.clear()
        this.items.addAll(items)
        this.items.add(null)
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<FilmShortModel>) {
        if (items.isEmpty()) {
            isDataEnd = true
            if (this.items.isNotEmpty()) this.items.removeAt(itemCount - 1)
        } else {
            if (this.items.isNotEmpty()) this.items.removeAt(itemCount - 1)
            this.items.addAll(items)
            this.items.add(null)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            R.layout.item_film_large -> {
                val view = ItemFilmLargeBinding.inflate(layoutInflater, parent, false)
                FilmsViewHolder(view)
            }
            R.layout.item_loading -> {
                val view = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(view)
            }
            else -> {
                val view = ItemFilmLargeBinding.inflate(layoutInflater, parent, false)
                FilmsViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: FilmsListViewHolder, position: Int) {
        val item = items[position]

        holder.bindTo(item)
        if (position == itemCount - 1 && !isDataEnd) {
            loadMore()
        }
    }

    override fun getItemCount(): Int = items.count()

    override fun getItemViewType(position: Int) =
            if (items[position] != null) R.layout.item_film_large else R.layout.item_loading

    abstract inner class FilmsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindTo(item: FilmShortModel?)
    }

    inner class FilmsViewHolder(
            val viewBinding: ItemFilmLargeBinding
            ) : FilmsListViewHolder(viewBinding.root) {

        private val options: RequestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.place_holder_top_film_image)
                .error(R.drawable.place_holder_top_film_image)

        override fun bindTo(item: FilmShortModel?) {

            viewBinding.apply {
                item?.let {
                    Glide.with(root).clear(ivPreview)
                    Glide.with(root)
                            .load(it.poster.image)
                            .thumbnail(Glide.with(root).load(it.poster.preview))
                            .apply(options)
                            .into(ivPreview)

                    tvFilmName.text = it.name

                    var countriesInfo = ""
                    for (country in it.countries) {
                        countriesInfo += "$country, "
                    }
                    tvCountries.text = if (countriesInfo.isNotEmpty()) countriesInfo.substring(0, countriesInfo.length - 2) else ""

                    var genresInfo = ""
                    for (genre in it.genres) {
                        genresInfo += "$genre, "
                    }
                    tvGenres.text = if (genresInfo.isNotEmpty()) genresInfo.substring(0, genresInfo.length - 2) else ""

                    tvYear.text = it.year

                    cvRatingHolder.apply {
                        when(it.ratingColor) {
                            RatingColor.GREEN -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_green))
                            RatingColor.LIME -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_lime))
                            RatingColor.YELLOW -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_yellow))
                            RatingColor.ORANGE -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_orange))
                            RatingColor.RED -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_red))
                        }
                    }
                    tvRating.text = it.rating

                    checkboxLike.isChecked = it.isLiked

                    root.setOnClickListener { clickToFilm(item.id) }
                    checkboxLike.setOnCheckedChangeListener { _, b -> clickToLike(item, b) }
                }
            }
        }
    }

    inner class LoadingViewHolder(
            val viewBinding: ItemLoadingBinding
    ) : FilmsListViewHolder(viewBinding.root) {

        override fun bindTo(item: FilmShortModel?) {

        }
    }
}