package com.chadi.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.chadi.movieapp.R
import com.chadi.movieapp.databinding.ItemMovieSearchBinding
import com.chadi.movieapp.models.entity.Movie
import com.chadi.movieapp.view.ui.common.AppExecutors
import com.chadi.movieapp.view.ui.common.DataBoundListAdapter

class MovieSearchListAdapter(
    appExecutors: AppExecutors,
    private val dataBindingComponent: DataBindingComponent,
    private val movieOnClickCallback: ((Movie) -> Unit)?
) : DataBoundListAdapter<Movie, ItemMovieSearchBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == (newItem)
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemMovieSearchBinding {
        val binding = DataBindingUtil.inflate<ItemMovieSearchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_movie_search,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.movie?.let {
                movieOnClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemMovieSearchBinding, item: Movie) {
        binding.movie = item
    }
}