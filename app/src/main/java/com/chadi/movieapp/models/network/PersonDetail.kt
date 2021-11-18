
package com.chadi.movieapp.models.network

import android.os.Parcelable
import com.chadi.movieapp.models.NetworkResponseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonDetail(
  val birthday: String?,
  val known_for_department: String,
  val place_of_birth: String?,
  val also_known_as: List<String>,
  val biography: String
) : Parcelable, NetworkResponseModel
