package com.kushnir.app.easytofind.ui.main.top.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.ItemFilmFewInfoBinding
import com.kushnir.app.easytofind.domain.models.FilmShortModel

class TopFilmsAdapter(
        private val clickToFilm: (FilmShortModel) -> Unit,
        private val clickToLike: (Int) -> Unit
): RecyclerView.Adapter<TopFilmsAdapter.TopFilmsViewHolder>() {

    private val items: MutableList<FilmShortModel> = mutableListOf()

    fun setItems(items: MutableList<FilmShortModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemFilmFewInfoBinding.inflate(layoutInflater, parent, false)

        return TopFilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val item = items[position]

        holder.bindTo(item)
    }

    override fun getItemCount(): Int = items.count()

    inner class TopFilmsViewHolder(val viewBinding: ItemFilmFewInfoBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        private val options: RequestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.place_holder_top_film_image)
                .error(R.drawable.place_holder_top_film_image)

        fun bindTo(item: FilmShortModel) {

            viewBinding.apply {

                Glide.with(root).clear(ivFilmMainImage)
                Glide.with(root)
                        .load(item.posterUrl)
                        .apply(options)
                        .into(ivFilmMainImage)

                tvFilmName.text = item.name

                var subtitleInfo = item.year
                for (genre in item.genres) {
                    subtitleInfo += ", $genre"
                }
                tvShortInfo.text = subtitleInfo

                ivFilmMainImage.setOnClickListener { clickToFilm(item) }
                // TODO сделать клик по лайку
            }
        }
    }
}