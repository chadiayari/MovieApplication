package com.chadi.movieapp.models.network

import com.chadi.movieapp.models.NetworkResponseModel
import com.chadi.movieapp.models.entity.MoviePerson

class MoviePersonResponse(
    val cast: List<MoviePerson>,
    val id : Int
) : NetworkResponseModel