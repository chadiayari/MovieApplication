
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.PersonDetail

class PersonDetailPagingChecker : NetworkPagingChecker<PersonDetail> {
  override fun hasNextPage(response: PersonDetail): Boolean {
    return false
  }
}
