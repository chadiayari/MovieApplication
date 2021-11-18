package com.chadi.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.chadi.movieapp.R
import com.chadi.movieapp.databinding.ItemTvForCelebrityBinding
import com.chadi.movieapp.models.entity.TvPerson
import com.chadi.movieapp.view.ui.common.AppExecutors
import com.chadi.movieapp.view.ui.common.DataBoundListAdapter

class TvPersonListAdapter(
    appExecutors: AppExecutors,
    private val dataBindingComponent: DataBindingComponent,
    private val movieOnClickCallback: ((TvPerson) -> Unit)?
) : DataBoundListAdapter<TvPerson, ItemTvForCelebrityBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<TvPerson>() {
        override fun areItemsTheSame(oldItem: TvPerson, newItem: TvPerson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvPerson, newItem: TvPerson): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemTvForCelebrityBinding {
        val binding = DataBindingUtil.inflate<ItemTvForCelebrityBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_tv_for_celebrity,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.tv?.let {
                movieOnClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: ItemTvForCelebrityBinding, item: TvPerson) {
        binding.tv = item
    }
}