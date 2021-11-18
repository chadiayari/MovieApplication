
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.DiscoverMovieResponse

class MoviePagingChecker : NetworkPagingChecker<DiscoverMovieResponse> {
  override fun hasNextPage(response: DiscoverMovieResponse): Boolean {
    return response.page < response.total_pages
  }
}
