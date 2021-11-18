
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.KeywordListResponse

class KeywordPagingChecker : NetworkPagingChecker<KeywordListResponse> {
  override fun hasNextPage(response: KeywordListResponse): Boolean {
    return false
  }
}
