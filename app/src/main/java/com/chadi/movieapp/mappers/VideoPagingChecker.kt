package com.chadi.movieapp.mappers

import com.chadi.movieapp.models.network.VideoListResponse

class VideoPagingChecker : NetworkPagingChecker<VideoListResponse> {
  override fun hasNextPage(response: VideoListResponse): Boolean {
    return false
  }
}
