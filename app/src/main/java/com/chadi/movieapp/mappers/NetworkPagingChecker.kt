
package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.NetworkResponseModel

interface NetworkPagingChecker<in FROM : NetworkResponseModel> {
  fun hasNextPage(response: FROM): Boolean
}
