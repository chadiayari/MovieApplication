package com.chadi.movieapp.view.ui.search.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chadi.movieapp.models.FilterData
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.entity.Movie
import com.chadi.movieapp.repository.DiscoverRepository
import com.chadi.movieapp.testing.OpenForTesting
import com.chadi.movieapp.utils.AbsentLiveData
import javax.inject.Inject

@OpenForTesting
class MovieSearchFilterViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {

    private var pageFiltersNumber = 1

    private val _totalMoviesCount = MutableLiveData<String>()
    val totalMoviesCount: LiveData<String> get() = _totalMoviesCount

    private var filterData = FilterData()

    private val searchMovieFilterPageLiveData: MutableLiveData<Int> = MutableLiveData()

    val searchMovieListFilterLiveData: LiveData<Resource<List<Movie>>> = Transformations
        .switchMap(searchMovieFilterPageLiveData) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                discoverRepository.loadFilteredMovies(
                    filterData,
                    it
                ) { totalCount ->
                    _totalMoviesCount.postValue(totalCount.toString())
                }
            }
        }

    fun setFilters(
        filterData: FilterData,
        page: Int,
        order: String
    ) {
        this.filterData = filterData.also {
            it.sort = order
        }
        searchMovieFilterPageLiveData.value = page
    }

    fun setPage(page: Int?) {
        searchMovieFilterPageLiveData.value = page
    }

    fun loadMoreFilters() {
        pageFiltersNumber++
        searchMovieFilterPageLiveData.value = pageFiltersNumber
    }

    fun resetFilterValues() {
        filterData = FilterData()
        this.pageFiltersNumber = 1
    }

    fun refresh() {
        searchMovieFilterPageLiveData.value?.let {
            searchMovieFilterPageLiveData.value = it
        }
    }

}