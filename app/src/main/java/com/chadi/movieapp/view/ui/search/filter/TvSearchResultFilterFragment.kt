package com.chadi.movieapp.view.ui.search.filter

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chadi.movieapp.R
import com.chadi.movieapp.binding.FragmentDataBindingComponent
import com.chadi.movieapp.di.Injectable
import com.chadi.movieapp.models.Status
import com.chadi.movieapp.utils.autoCleared
import com.chadi.movieapp.view.adapter.TvSearchListAdapter
import com.chadi.movieapp.view.ui.common.AppExecutors
import com.chadi.movieapp.view.ui.common.RetryCallback
import javax.inject.Inject

class TvSearchResultFilterFragment :
    SearchResultFilterFragmentBase(R.layout.fragment_search_result_filter), Injectable,
    androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<TvSearchFilterViewModel> { viewModelFactory }
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private var tvsAdapter by autoCleared<TvSearchListAdapter>()


    override fun getFilterMap(): HashMap<String, ArrayList<String>>? {
        @Suppress("UNCHECKED_CAST")
        return arguments?.getSerializable("key") as HashMap<String, ArrayList<String>>?
    }

    override fun setBindingVariables() {
        with(binding) {
            lifecycleOwner = this@TvSearchResultFilterFragment.viewLifecycleOwner
            totalFilterResult = viewModel.totalTvsCount
            selectedFilters = setSelectedFilters()
            callback = object : RetryCallback {
                override fun retry() {
                    viewModel.refresh()
                }
            }
        }
    }

    override fun observeSubscribers() {
        viewModel.searchTvListFilterLiveData.observe(viewLifecycleOwner) {
            binding.resource = viewModel.searchTvListFilterLiveData.value
            if (!it.data.isNullOrEmpty()) {
                tvsAdapter.submitList(it.data)
            }
        }
    }

    override fun setRecyclerViewAdapter() {
        tvsAdapter = TvSearchListAdapter(
            appExecutors,
            dataBindingComponent
        ) {
            findNavController().navigate(
                TvSearchResultFilterFragmentDirections.actionTvSearchFragmentResultFilterToTvDetail(
                    it
                )
            )
        }

        binding.filteredItemsRecyclerView.apply {
            adapter = tvsAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun loadMoreFilters() {
        viewModel.loadMoreFilters()
    }

    override fun isLoading(): Boolean {
        return viewModel.searchTvListFilterLiveData.value?.status == Status.LOADING
    }

    override fun navigateFromSearchResultFilterFragmentToSearchFragment() {
        findNavController().navigate(
            TvSearchResultFilterFragmentDirections.actionTvSearchFragmentResultFilterToTvSearchFragment()
        )
    }


    override fun hasNextPage(): Boolean {
        viewModel.searchTvListFilterLiveData.value?.let {
            return it.hasNextPage
        }
        return false
    }


    override fun resetAndLoadFiltersSortedBy(order: String) {
        viewModel.resetFilters()
        viewModel.setFilters(
            getFilterData(),
            1
        )
    }
}