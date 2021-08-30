package com.kushnir.app.easytofind.ui.main.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushnir.app.easytofind.R
import com.kushnir.app.easytofind.databinding.ItemSmallBinding
import com.kushnir.app.easytofind.domain.models.CastModel

class CastAdapter: RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private val items: MutableList<CastModel> = mutableListOf()

    fun setItems(items: MutableList<CastModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemSmallBinding.inflate(layoutInflater, parent, false)

        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item = items[position]

        holder.bindTo(item)
    }

    override fun getItemCount(): Int = items.count()

    inner class CastViewHolder(val viewBinding: ItemSmallBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        private val options: RequestOptions = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.place_holder_cast)
            .error(R.drawable.place_holder_cast)

        fun bindTo(item: CastModel) {

            viewBinding.apply {

                Glide.with(root).clear(ivFilmMainImage)
                Glide.with(root)
                    .load(item.posterUrl)
                    .apply(options)
                    .into(ivFilmMainImage)

                tvFilmName.text = item.name
                tvShortInfo.text = item.role
            }
        }
    }
}