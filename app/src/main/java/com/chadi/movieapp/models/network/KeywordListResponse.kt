
package com.chadi.movieapp.models.network

import com.chadi.movieapp.models.Keyword
import com.chadi.movieapp.models.NetworkResponseModel

data class KeywordListResponse(
  val id: Int,
  val keywords: List<Keyword>
) : NetworkResponseModel
