package com.chadi.movieapp.view.ui.movies.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.entity.Movie
import com.chadi.movieapp.repository.DiscoverRepository
import com.chadi.movieapp.testing.OpenForTesting
import com.chadi.movieapp.utils.AbsentLiveData
import com.chadi.movieapp.view.ui.common.AppExecutors
import javax.inject.Inject

@OpenForTesting
class MovieListViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {

    private var pageNumber = 1
    private val moviePageLiveData: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var appExecutors: AppExecutors

    val movieListLiveData: LiveData<Resource<List<Movie>>> = Transformations
        .switchMap(moviePageLiveData) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                discoverRepository.loadMovies(it)
            }
        }

    init {
        moviePageLiveData.value = 1
    }

    fun setMoviePage(page: Int) {
        moviePageLiveData.value = page
    }

    fun loadMore() {
        pageNumber++
        moviePageLiveData.value = pageNumber
    }

    fun refresh() {
        moviePageLiveData.value?.let {
            moviePageLiveData.value = it
        }
    }

}
