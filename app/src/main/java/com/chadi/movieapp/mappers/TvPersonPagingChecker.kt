
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.TvPersonResponse

class TvPersonPagingChecker : NetworkPagingChecker<TvPersonResponse> {
  override fun hasNextPage(response: TvPersonResponse): Boolean {
    return false
  }
}
