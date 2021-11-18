
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.PeopleResponse

class PeoplePagingChecker : NetworkPagingChecker<PeopleResponse> {
  override fun hasNextPage(response: PeopleResponse): Boolean {
    return response.page < response.total_pages
  }
}
