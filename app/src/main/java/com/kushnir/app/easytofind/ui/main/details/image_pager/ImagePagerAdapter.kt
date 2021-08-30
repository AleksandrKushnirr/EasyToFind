package com.kushnir.app.easytofind.ui.main.details.image_pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.kushnir.app.easytofind.domain.models.ImageModel

class ImagePagerAdapter(
    fragment: Fragment,
    private var data: MutableList<ImageModel> = mutableListOf()
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = data.count()

    override fun createFragment(position: Int): Fragment {
        return ImagePageFragment.newInstance(data[position])
    }

    fun setData(data: List<ImageModel>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setMainPoster(image: ImageModel) {
        this.data.add(0, image)
        notifyDataSetChanged()
    }
}