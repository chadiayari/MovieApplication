package com.chadi.movieapp.view.ui.search

import android.os.Bundle
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.chadi.movieapp.R
import com.chadi.movieapp.binding.FragmentDataBindingComponent
import com.chadi.movieapp.di.Injectable
import com.chadi.movieapp.extension.isEmptyOrBlank
import com.chadi.movieapp.utils.autoCleared
import com.chadi.movieapp.view.adapter.MovieSearchListAdapter
import com.chadi.movieapp.view.ui.common.AppExecutors
import com.chadi.movieapp.view.ui.search.base.SearchFragmentBase
import javax.inject.Inject

class MovieSearchFragment : SearchFragmentBase(R.layout.fragment_search), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<MovieSearchViewModel> { viewModelFactory }

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var movieAdapter by autoCleared<MovieSearchListAdapter>()


    override fun setSearchViewHint() {
        searchBinding.toolbarSearch.searchView.queryHint = "Search Movies"
    }

    override fun observeSuggestions() {
        viewModel.movieSuggestions.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) movieAdapter.submitList(it)
        }
    }

    override fun setFilterTabName(tab: TabLayout.Tab?) {
        tab?.text = getString(R.string.filter_movies_tab_name)
    }

    override fun setBindingVariables() {/*DO nothing*/
    }

    override fun navigateFromSearchFragmentToSearchFragmentResultFilter(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_movieSearchFragment_to_movieSearchFragmentResultFilter,
            bundle
        )
    }

    override fun navigateFromSearchFragmentToSearchFragmentResult(query: String) {
        findNavController().navigate(
            MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieSearchFragmentResult(
                query
            )
        )
    }

    override fun navigateFromSearchFragmentToListItemsFragment() {
        findNavController().navigate(
            MovieSearchFragmentDirections.actionMovieSearchFragmentToMoviesFragment()
        )
    }


    override fun setRecyclerViewAdapter() {
        movieAdapter = MovieSearchListAdapter(
            appExecutors,
            dataBindingComponent
        ) {
            findNavController().navigate(
                MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetail(
                    it
                )
            )
        }
        searchBinding.recyclerViewSuggestion.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    override fun observeAndSetRecentQueries() {
        viewModel.getMovieRecentQueries().observe(viewLifecycleOwner) { it ->
            if (!it.isNullOrEmpty()) {
                val queries = it.mapNotNull { it.query }.filter { it.isNotEmpty() }
                if (queries.isNotEmpty()) setListViewOfRecentQueries(queries)
            }
        }
    }

    override fun deleteAllRecentQueries() {
        viewModel.deleteAllMovieRecentQueries()
    }

    override fun setSuggestionsQuery(newText: String?) {
        newText?.let { text ->
            if (isRecentTabSelected() && !isEmptyOrBlank(text)) {
                showSuggestionViewAndHideRecentSearches()
                viewModel.setMovieSuggestionsQuery(text)
            }

            if (isRecentTabSelected() && isEmptyOrBlank(text)) {
                hideSuggestionViewAndShowRecentSearches()
                movieAdapter.submitList(null)
            }
        }
    }

}


