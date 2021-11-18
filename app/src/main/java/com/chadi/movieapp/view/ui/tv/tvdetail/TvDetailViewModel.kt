package com.chadi.movieapp.view.ui.tv.tvdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.chadi.movieapp.repository.TvRepository
import com.chadi.movieapp.testing.OpenForTesting
import javax.inject.Inject


@OpenForTesting
class TvDetailViewModel @Inject constructor(
    private val repository: TvRepository
) : ViewModel() {

    private val tvIdLiveData: MutableLiveData<Int> = MutableLiveData()

    val keywordListLiveData = tvIdLiveData.switchMap { id ->
        repository.loadKeywordList(id)
    }

    val videoListLiveData = tvIdLiveData.switchMap { id ->
        repository.loadVideoList(id)
    }

    fun setTvId(id: Int) = tvIdLiveData.postValue(id)
}
