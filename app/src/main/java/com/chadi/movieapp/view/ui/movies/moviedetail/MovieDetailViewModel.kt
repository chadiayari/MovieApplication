package com.chadi.movieapp.view.ui.movies.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.chadi.movieapp.repository.MovieRepository
import com.chadi.movieapp.testing.OpenForTesting
import javax.inject.Inject


@OpenForTesting
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val movieIdLiveData: MutableLiveData<Int> = MutableLiveData()

    val keywordListLiveData = movieIdLiveData.switchMap { id ->
        repository.loadKeywordList(id)
    }

    val videoListLiveData = movieIdLiveData.switchMap { id ->
        repository.loadVideoList(id)
    }


    fun setMovieId(id: Int) {
        movieIdLiveData.postValue(id)
    }

}
