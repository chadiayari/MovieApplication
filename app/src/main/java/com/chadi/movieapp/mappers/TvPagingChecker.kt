
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.DiscoverTvResponse

class TvPagingChecker : NetworkPagingChecker<DiscoverTvResponse> {
  override fun hasNextPage(response: DiscoverTvResponse): Boolean {
    return response.page < response.total_pages
  }
}
