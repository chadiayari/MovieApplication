
package com.chadi.movieapp.models.network

import com.chadi.movieapp.models.NetworkResponseModel
import com.chadi.movieapp.models.Video

data class VideoListResponse(
  val id: Int,
  val results: List<Video>
) : NetworkResponseModel
