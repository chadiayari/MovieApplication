package com.chadi.movieapp.view.ui.person.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.chadi.movieapp.R
import com.chadi.movieapp.binding.FragmentDataBindingComponent
import com.chadi.movieapp.databinding.FragmentTvCelebrityDetailBinding
import com.chadi.movieapp.di.Injectable
import com.chadi.movieapp.models.entity.TvPerson
import com.chadi.movieapp.utils.autoCleared

class TvPersonDetailFragment : Fragment(R.layout.fragment_tv_celebrity_detail), Injectable {

    private var binding by autoCleared<FragmentTvCelebrityDetailBinding>()

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = DataBindingUtil.bind(view, dataBindingComponent)!!

        with(binding) {
            tv = getTvFromIntent()
            detailHeader.tv = getTvFromIntent()
        }
    }

    private fun getTvFromIntent(): TvPerson {
        return TvPersonDetailFragmentArgs.fromBundle(
            requireArguments()
        ).tv
    }
}