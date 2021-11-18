package com.chadi.movieapp.models.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Tv::class)
@Entity(tableName = "tvSuggestionsFts")
class TvSuggestionsFts (val id: Int, val name: String)