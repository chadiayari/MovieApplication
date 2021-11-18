package com.chadi.movieapp.models.network

import com.chadi.movieapp.models.NetworkResponseModel
import com.chadi.movieapp.models.entity.TvPerson

class TvPersonResponse(
    val cast: List<TvPerson>,
    val id : Int
) : NetworkResponseModel