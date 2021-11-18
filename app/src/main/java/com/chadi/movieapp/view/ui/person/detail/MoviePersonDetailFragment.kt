package com.chadi.movieapp.view.ui.person.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.chadi.movieapp.R
import com.chadi.movieapp.binding.FragmentDataBindingComponent
import com.chadi.movieapp.databinding.FragmentMovieCelebrityDetailBinding
import com.chadi.movieapp.di.Injectable
import com.chadi.movieapp.models.entity.MoviePerson
import com.chadi.movieapp.utils.autoCleared


class MoviePersonDetailFragment : Fragment(R.layout.fragment_movie_celebrity_detail), Injectable {

    private var binding by autoCleared<FragmentMovieCelebrityDetailBinding>()

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = DataBindingUtil.bind(view, dataBindingComponent)!!

        with(binding) {
            movie = getMovieFromIntent()
            detailHeader.movie = getMovieFromIntent()
        }
    }

    private fun getMovieFromIntent(): MoviePerson {
        return MoviePersonDetailFragmentArgs.fromBundle(
            requireArguments()
        ).movie
    }
}