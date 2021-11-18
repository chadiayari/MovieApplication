package com.chadi.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chadi.movieapp.api.ApiResponse
import com.chadi.movieapp.api.MovieService
import com.chadi.movieapp.mappers.KeywordPagingChecker
import com.chadi.movieapp.mappers.VideoPagingChecker
import com.chadi.movieapp.models.Keyword
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.Video
import com.chadi.movieapp.models.network.KeywordListResponse
import com.chadi.movieapp.models.network.VideoListResponse
import com.chadi.movieapp.room.MovieDao
import com.chadi.movieapp.testing.OpenForTesting
import com.chadi.movieapp.view.ui.common.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Original Source https://github.com/skydoves/TheMovies
 */
@OpenForTesting
@Singleton
class MovieRepository @Inject constructor(
        private val service: MovieService,
        private val movieDao: MovieDao,
        private val appExecutors: AppExecutors
)  {

    fun loadKeywordList(id: Int): LiveData<Resource<List<Keyword>>> {
        return object : NetworkBoundResource<
                List<Keyword>,
                KeywordListResponse,
                KeywordPagingChecker
                >(appExecutors) {
            override fun saveCallResult(items: KeywordListResponse) {
                val movie = movieDao.getMovie(id_ = id)
                movie.keywords = items.keywords
                movieDao.updateMovie(movie = movie)
            }

            override fun shouldFetch(data: List<Keyword>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Keyword>> {
                val movie = movieDao.getMovie(id_ = id)
                val data: MutableLiveData<List<Keyword>> = MutableLiveData()
                data.value = movie.keywords
                return data
            }

            override fun pageChecker(): KeywordPagingChecker {
                return KeywordPagingChecker()
            }

            override fun createCall(): LiveData<ApiResponse<KeywordListResponse>> {
                return service.fetchKeywords(id = id)
            }
        }.asLiveData()
    }

    fun loadVideoList(id: Int): LiveData<Resource<List<Video>>> {
        return object : NetworkBoundResource<
                List<Video>,
                VideoListResponse,
                VideoPagingChecker
        >(appExecutors) {
            override fun saveCallResult(items: VideoListResponse) {
                val movie = movieDao.getMovie(id_ = id)
                movie.videos = items.results
                movieDao.updateMovie(movie = movie)
            }

            override fun shouldFetch(data: List<Video>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Video>> {
                val movie = movieDao.getMovie(id_ = id)
                val data: MutableLiveData<List<Video>> = MutableLiveData()
                data.value = movie.videos
                return data
            }

            override fun pageChecker(): VideoPagingChecker {
                return VideoPagingChecker()
            }
            override fun createCall(): LiveData<ApiResponse<VideoListResponse>> {
                return service.fetchVideos(id = id)
            }
        }.asLiveData()
    }
}
