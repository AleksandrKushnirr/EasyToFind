package com.kushnir.app.easytofind.ui.main.details.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.ItemSmallBinding
import com.kushnir.app.easytofind.domain.models.SimilarFilmModel

class SimilarFilmsAdapter(
    private val clickToFilm: (SimilarFilmModel) -> Unit,
    private val clickToLike: (Int) -> Unit
): RecyclerView.Adapter<SimilarFilmsAdapter.SimilarFilmsViewHolder>() {

    private val items: MutableList<SimilarFilmModel> = mutableListOf()

    fun setItems(items: MutableList<SimilarFilmModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarFilmsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemSmallBinding.inflate(layoutInflater, parent, false)

        return SimilarFilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarFilmsViewHolder, position: Int) {
        val item = items[position]

        holder.bindTo(item)
    }

    override fun getItemCount(): Int = items.count()

    inner class SimilarFilmsViewHolder(val viewBinding: ItemSmallBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        private val options: RequestOptions = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.place_holder_top_film_image)
            .error(R.drawable.place_holder_top_film_image)

        fun bindTo(item: SimilarFilmModel) {

            viewBinding.apply {

                Glide.with(root).clear(ivFilmMainImage)
                Glide.with(root)
                    .load(item.image.image)
                    .thumbnail(Glide.with(root).load(item.image.preview))
                    .apply(options)
                    .into(ivFilmMainImage)

                tvFilmName.text = item.name


                tvShortInfo.visibility = View.GONE

                ivFilmMainImage.setOnClickListener { clickToFilm(item) }
                // TODO сделать клик по лайку
            }
        }
    }
}