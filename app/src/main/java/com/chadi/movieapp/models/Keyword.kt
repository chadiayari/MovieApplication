
package com.chadi.movieapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Keyword(
  val id: Int,
  val name: String
) : Parcelable
