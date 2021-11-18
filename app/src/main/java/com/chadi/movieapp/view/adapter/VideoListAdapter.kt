package com.chadi.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.chadi.movieapp.R
import com.chadi.movieapp.databinding.ItemVideoBinding
import com.chadi.movieapp.models.Video
import com.chadi.movieapp.view.ui.common.RecyclerViewBase


class VideoListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val videoOnClickCallback: (Video) -> Unit
) : RecyclerViewBase<Video, ItemVideoBinding>() {

    override fun createBinding(parent: ViewGroup): ItemVideoBinding {
        val binding: ItemVideoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_video,
            parent,
            false,
            dataBindingComponent
        )

        binding.root.setOnClickListener {
            binding.video?.let {
                videoOnClickCallback.invoke(it)
            }
        }

        return binding

    }

    override fun bind(binding: ItemVideoBinding, item: Video) {
        binding.video = item
    }

}
