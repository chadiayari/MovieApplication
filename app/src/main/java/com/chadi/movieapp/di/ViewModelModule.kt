package com.chadi.movieapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chadi.movieapp.factory.AppViewModelFactory
import com.chadi.movieapp.view.ui.movies.moviedetail.MovieDetailViewModel
import com.chadi.movieapp.view.ui.movies.movielist.MovieListViewModel
import com.chadi.movieapp.view.ui.person.celebrities.CelebritiesListViewModel
import com.chadi.movieapp.view.ui.person.detail.PersonDetailViewModel
import com.chadi.movieapp.view.ui.person.search.SearchCelebritiesResultViewModel
import com.chadi.movieapp.view.ui.search.MovieSearchViewModel
import com.chadi.movieapp.view.ui.search.TvSearchViewModel
import com.chadi.movieapp.view.ui.search.filter.MovieSearchFilterViewModel
import com.chadi.movieapp.view.ui.search.filter.TvSearchFilterViewModel
import com.chadi.movieapp.view.ui.tv.tvdetail.TvDetailViewModel
import com.chadi.movieapp.view.ui.tv.tvlist.TvListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieListFragmentViewModel(movieListViewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvDetailViewModel::class)
    abstract fun bindTvDetailViewModel(tvDetailViewModel: TvDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CelebritiesListViewModel::class)
    abstract fun bindCelebritiesListViewModel(celebritiesListViewModel: CelebritiesListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TvListViewModel::class)
    abstract fun bindTvListViewModel(tvListViewModel: TvListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonDetailViewModel::class)
    abstract fun bindPersonDetailViewModel(personDetailViewModel: PersonDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel::class)
    abstract fun bindMovieSearchViewModel(movieSearchViewModel: MovieSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvSearchViewModel::class)
    abstract fun bindTvSearchViewModel(tvSearchViewModel: TvSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchFilterViewModel::class)
    abstract fun bindMovieSearchFilterViewModel(movieSearchFilterViewModel: MovieSearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvSearchFilterViewModel::class)
    abstract fun bindTvSearchFilterViewModel(tvSearchFilterViewModel: TvSearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchCelebritiesResultViewModel::class)
    abstract fun bindSearchCelebritiesResultViewModel(searchCelebritiesResultViewModel: SearchCelebritiesResultViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}