package com.chadi.movieapp.view.ui.person.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.entity.MoviePerson
import com.chadi.movieapp.models.entity.TvPerson
import com.chadi.movieapp.repository.PeopleRepository
import com.chadi.movieapp.testing.OpenForTesting
import com.chadi.movieapp.utils.AbsentLiveData
import javax.inject.Inject


@OpenForTesting
class PersonDetailViewModel @Inject constructor(
    private val repository: PeopleRepository,
) : ViewModel() {

    private val personId = MutableLiveData<Int>()

    val personLiveData = personId.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            repository.loadPersonDetail(it)
        }
    }
    val moviesOfCelebrity: LiveData<Resource<List<MoviePerson>>> = personId.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            repository.loadMoviesForPerson(personId = it)
        }
    }


    val tvsOfCelebrity: LiveData<Resource<List<TvPerson>>> = personId.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            repository.loadTvsForPerson(personId = it)
        }
    }

    fun setPersonId(id: Int) {
        personId.value = id
    }
}
