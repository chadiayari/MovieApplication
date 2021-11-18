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
import com.chadi.movieapp.view.adapter.TvSearchListAdapter
import com.chadi.movieapp.view.ui.common.AppExecutors
import com.chadi.movieapp.view.ui.search.base.SearchFragmentBase

import javax.inject.Inject


class TvSearchFragment : SearchFragmentBase(R.layout.fragment_search), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<TvSearchViewModel> { viewModelFactory }
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private var tvAdapter by autoCleared<TvSearchListAdapter>()


    override fun setSearchViewHint() {
        searchBinding.toolbarSearch.searchView.queryHint = "Search Series"
    }

    override fun setFilterTabName(tab: TabLayout.Tab?) {
        tab?.text = getString(R.string.filter_series_tab_name)
    }

    override fun setBindingVariables() {/*Do nothing*/
    }

    override fun navigateFromSearchFragmentToSearchFragmentResultFilter(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_tvSearchFragment_to_tvSearchFragmentResultFilter,
            bundle
        )
    }

    override fun navigateFromSearchFragmentToSearchFragmentResult(query: String) {
        findNavController().navigate(
            TvSearchFragmentDirections.actionTvSearchFragmentToTvSearchFragmentResult(
                query
            )
        )
    }

    override fun navigateFromSearchFragmentToListItemsFragment() {
        findNavController().navigate(
            TvSearchFragmentDirections.actionTvSearchFragmentToTvsFragment()
        )
    }

    override fun observeSuggestions() {

        viewModel.tvSuggestions.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) tvAdapter.submitList(it)
        }
    }

    override fun setRecyclerViewAdapter() {
        tvAdapter = TvSearchListAdapter(
            appExecutors,
            dataBindingComponent
        ) {
            findNavController().navigate(
                TvSearchFragmentDirections.actionTvSearchFragmentToTvDetail(
                    it
                )
            )
        }

        searchBinding.recyclerViewSuggestion.apply {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    override fun observeAndSetRecentQueries() {
        viewModel.getTvRecentQueries().observe(viewLifecycleOwner) { it ->
            if (!it.isNullOrEmpty()) {
                val queries = it.mapNotNull { it.query }.filter { it.isNotEmpty() }
                if (queries.isNotEmpty()) setListViewOfRecentQueries(queries)
            }
        }
    }

    override fun deleteAllRecentQueries() {
        viewModel.deleteAllTvRecentQueries()
    }

    override fun setSuggestionsQuery(newText: String?) {
        newText?.let { text ->
            if (isRecentTabSelected() && !isEmptyOrBlank(text)) {
                showSuggestionViewAndHideRecentSearches()
                viewModel.setTvSuggestionsQuery(text)
            }

            if (isRecentTabSelected() && isEmptyOrBlank(text)) {
                hideSuggestionViewAndShowRecentSearches()
                tvAdapter.submitList(null)
            }
        }
    }

}