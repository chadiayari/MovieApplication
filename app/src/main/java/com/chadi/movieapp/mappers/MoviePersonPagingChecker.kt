
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.MoviePersonResponse

class MoviePersonPagingChecker : NetworkPagingChecker<MoviePersonResponse> {
  override fun hasNextPage(response: MoviePersonResponse): Boolean {
    return false
  }
}
