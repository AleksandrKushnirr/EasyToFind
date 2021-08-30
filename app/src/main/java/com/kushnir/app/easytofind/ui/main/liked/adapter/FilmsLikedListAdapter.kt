package com.kushnir.app.easytofind.ui.main.liked.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.ItemFilmLargeBinding
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.FilmShortModel

class FilmsLikedListAdapter(
        private val clickToFilm: (Int) -> Unit,
        private val clickToLike: (FilmShortModel, Boolean) -> Unit
): RecyclerView.Adapter<FilmsLikedListAdapter.FilmsLikedListViewHolder>() {

    private val items: MutableList<FilmShortModel> = mutableListOf()

    fun setItems(items: MutableList<FilmShortModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsLikedListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = ItemFilmLargeBinding.inflate(layoutInflater, parent, false)
        return FilmsLikedListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsLikedListViewHolder, position: Int) {
        val item = items[position]

        holder.bindTo(item)
    }

    override fun getItemCount(): Int = items.count()

    inner class FilmsLikedListViewHolder(val viewBinding: ItemFilmLargeBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        private val options: RequestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.place_holder_top_film_image)
                .error(R.drawable.place_holder_top_film_image)

        fun bindTo(item: FilmShortModel) {

            viewBinding.apply {

                Glide.with(root).clear(ivPreview)
                Glide.with(root)
                        .load(item.poster.image)
                        .thumbnail(Glide.with(root).load(item.poster.preview))
                        .apply(options)
                        .into(ivPreview)

                tvFilmName.text = item.name

                var countriesInfo = ""
                for (country in item.countries) {
                    countriesInfo += "$country, "
                }
                tvCountries.text = if (countriesInfo.isNotEmpty()) countriesInfo.substring(0, countriesInfo.length - 2) else ""

                var genresInfo = ""
                for (genre in item.genres) {
                    genresInfo += "$genre, "
                }
                tvGenres.text = if (genresInfo.isNotEmpty()) genresInfo.substring(0, genresInfo.length - 2) else ""

                tvYear.text = item.year

                cvRatingHolder.apply {
                    when(item.ratingColor) {
                        RatingColor.GREEN -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_green))
                        RatingColor.LIME -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_lime))
                        RatingColor.YELLOW -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_yellow))
                        RatingColor.ORANGE -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_orange))
                        RatingColor.RED -> setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.rating_red))
                    }
                }
                tvRating.text = item.rating

                checkboxLike.isChecked = item.isLiked

                root.setOnClickListener { clickToFilm(item.id) }
                checkboxLike.setOnCheckedChangeListener { _, b -> clickToLike(item, b) }

            }
        }
    }
}