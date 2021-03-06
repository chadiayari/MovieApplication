package com.chadi.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.entity.Person
import com.chadi.movieapp.repository.PeopleRepository
import com.chadi.movieapp.utils.MockTestUtil.Companion.mockPerson
import com.chadi.movieapp.view.ui.person.celebrities.CelebritiesListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt

@RunWith(JUnit4::class)
class CelebritiesListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CelebritiesListViewModel
    private val repository = mock<PeopleRepository>()

    @Before
    fun init() {
        viewModel = CelebritiesListViewModel(repository)
    }


    @Test
    fun testLoadPeople() {
        val observer = mock<Observer<Resource<List<Person>>>>()
        val person = mockPerson()
        val resourceData = Resource.success(listOf(person), true)
        val peopleLiveData = MutableLiveData<Resource<List<Person>>>()
        `when`(repository.loadPeople(anyInt())).thenReturn(peopleLiveData)
        viewModel.peopleLiveData.observeForever(observer)
        peopleLiveData.postValue(resourceData)
        verify(repository).loadPeople(1)
        verify(observer).onChanged(resourceData)
    }

}