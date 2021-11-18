
package com.chadi.movieapp.models.network

import com.chadi.movieapp.models.NetworkResponseModel
import com.chadi.movieapp.models.entity.Person

data class PeopleResponse(
  val page: Int,
  val results: List<Person>,
  val total_results: Int,
  val total_pages: Int
) : NetworkResponseModel
