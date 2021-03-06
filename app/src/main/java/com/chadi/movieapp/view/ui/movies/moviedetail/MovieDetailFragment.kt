package com.chadi.movieapp.view.ui.movies.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.chadi.movieapp.R
import com.chadi.movieapp.api.Api
import com.chadi.movieapp.binding.FragmentDataBindingComponent
import com.chadi.movieapp.databinding.FragmentMovieDetailBinding
import com.chadi.movieapp.di.Injectable
import com.chadi.movieapp.models.entity.Movie
import com.chadi.movieapp.utils.autoCleared
import com.chadi.movieapp.view.adapter.VideoListAdapter
import javax.inject.Inject

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail),
    Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MovieDetailViewModel> { viewModelFactory }

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<FragmentMovieDetailBinding>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = DataBindingUtil.bind(view, dataBindingComponent)!!

        initializeUI()

        val movieArg = getMovieSafeArgs()

        with(binding) {
            lifecycleOwner = this@MovieDetailFragment.viewLifecycleOwner
            movie = movieArg
            detailBody.viewmodel = viewModel
        }

        viewModel.setMovieId(movieArg.id)

    }


    private fun initializeUI() {
        with(binding.detailBody) {
            detailBodyRecyclerViewTrailers.adapter =
                VideoListAdapter(dataBindingComponent) {
                    val playVideoIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(it.key)))
                    startActivity(playVideoIntent)
                }
        }
    }

    private fun getMovieSafeArgs(): Movie {
        val params =
            MovieDetailFragmentArgs.fromBundle(
                requireArguments()
            )
        return params.movie
    }


}
