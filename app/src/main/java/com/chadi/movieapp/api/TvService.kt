
package com.chadi.movieapp.api

import androidx.lifecycle.LiveData
import com.chadi.movieapp.models.network.KeywordListResponse
import com.chadi.movieapp.models.network.VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvService {

  @GET("/3/tv/{tv_id}/keywords")
  fun fetchKeywords(@Path("tv_id") id: Int): LiveData<ApiResponse<KeywordListResponse>>

  @GET("/3/tv/{tv_id}/videos")
  fun fetchVideos(@Path("tv_id") id: Int): LiveData<ApiResponse<VideoListResponse>>
}
