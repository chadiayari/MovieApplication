package com.chadi.movieapp.view.ui.movies.movielist

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chadi.movieapp.R
import com.chadi.movieapp.binding.FragmentDataBindingComponent
import com.chadi.movieapp.databinding.FragmentMoviesBinding
import com.chadi.movieapp.di.Injectable
import com.chadi.movieapp.extension.visible
import com.chadi.movieapp.models.Status
import com.chadi.movieapp.utils.InfinitePager
import com.chadi.movieapp.utils.autoCleared
import com.chadi.movieapp.view.adapter.MovieListAdapter
import com.chadi.movieapp.view.ui.common.AppExecutors
import com.chadi.movieapp.view.ui.common.RetryCallback
import com.chadi.movieapp.view.ui.main.MainActivity
import javax.inject.Inject

class MovieListFragment : Fragment(R.layout.fragment_movies), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<MovieListViewModel> { viewModelFactory }


    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<FragmentMoviesBinding>()

    private var moviesAdapter by autoCleared<MovieListAdapter>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentMoviesBinding.bind(view)

        with(binding) {
            lifecycleOwner = this@MovieListFragment.viewLifecycleOwner
            searchResult = viewModel.movieListLiveData
            callback = object : RetryCallback {
                override fun retry() {
                    viewModel.refresh()
                }
            }
        }

        subscribers()
        initializeUI()
    }

    private fun initializeUI() {

        intiToolbar(getString(R.string.fragment_movies))
        showBottomNavigationView()

        moviesAdapter = MovieListAdapter(appExecutors, dataBindingComponent) {
            findNavController().navigate(
                MovieListFragmentDirections.actionMoviesFragmentToMovieDetail(
                    it
                )
            )
        }

        with(binding.recyclerViewListMovies) {
            setHasFixedSize(true)
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(context, 3)
            addOnScrollListener(object : InfinitePager(moviesAdapter) {
                override fun loadMoreCondition(): Boolean {
                    viewModel.movieListLiveData.value?.let { resource ->
                        return resource.hasNextPage && resource.status != Status.LOADING
                    }
                    return false
                }

                override fun loadMore() {
                    viewModel.loadMore()
                }
            })
        }
    }

    private fun subscribers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner) {
            if (!it.data.isNullOrEmpty()) {
                moviesAdapter.submitList(it.data)
            }
        }
    }


    /**
     * Init the toolbar
     * @param titleIn
     */
    private fun intiToolbar(titleIn: String) {

        binding.toolbarSearch.toolbarTitle.text = titleIn

        binding.toolbarSearch.searchIcon.setOnClickListener {
            findNavController().navigate(
                MovieListFragmentDirections
                    .actionMoviesFragmentToMovieSearchFragment()
            )
        }
    }

    private fun showBottomNavigationView() {
        (activity as MainActivity).binding.bottomNavigation.visible()
    }

}