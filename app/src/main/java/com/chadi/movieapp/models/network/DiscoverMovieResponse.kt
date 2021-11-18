
package com.chadi.movieapp.models.network

import com.chadi.movieapp.models.NetworkResponseModel
import com.chadi.movieapp.models.entity.Movie

data class DiscoverMovieResponse(
  val page: Int,
  val results: List<Movie>,
  val total_results: Int,
  val total_pages: Int
) : NetworkResponseModel
