package com.chadi.movieapp.fragment.movie

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.chadi.movieapp.R
import com.chadi.movieapp.models.Keyword
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.Video
import com.chadi.movieapp.models.entity.Movie
import com.chadi.movieapp.util.*
import com.chadi.movieapp.utils.MockTestUtil
import com.chadi.movieapp.view.ui.movies.moviedetail.MovieDetailFragment
import com.chadi.movieapp.view.ui.movies.moviedetail.MovieDetailFragmentArgs
import com.chadi.movieapp.view.ui.movies.moviedetail.MovieDetailViewModel
import com.nhaarman.mockitokotlin2.whenever
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.anyOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MovieDetailFragmentTest {

    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private val keywordsLiveData = MutableLiveData<Resource<List<Keyword>>>()
    private val videosLiveData = MutableLiveData<Resource<List<Video>>>()
    private lateinit var viewModel: MovieDetailViewModel


    @Before
    fun init() {
        viewModel = mock(MovieDetailViewModel::class.java)

        whenever(viewModel.keywordListLiveData).thenReturn(keywordsLiveData)
        whenever(viewModel.videoListLiveData).thenReturn(videosLiveData)

        val bundle = MovieDetailFragmentArgs(Movie(1)).toBundle()

        val scenario = launchFragmentInContainer(
            bundle, themeResId = R.style.AppTheme
        ) {
            MovieDetailFragment().apply {
                viewModelFactory = ViewModelUtil.createFor(viewModel)
            }
        }

        dataBindingIdlingResourceRule.monitorFragment(scenario)

        runOnUiThread {
            navController.setGraph(R.navigation.movie)
        }
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
            fragment.disableProgressBarAnimations()
        }
    }

    @Test
    fun testMovieDetail() {
        // Given
        val keywords = MockTestUtil.mockKeywordList()
        val videos = MockTestUtil.mockVideoList()

        // Action
        keywordsLiveData.postValue(Resource.success(keywords, false))
        videosLiveData.postValue(Resource.success(videos, false))

        onView(listMatcherVideos().atPosition(0)).check(matches(isDisplayed()))
        onView(listMatcherVideos().atPosition(0)).check(matches(hasDescendant(withText("video0"))))

        //Keywords
        onView(withId(R.id.detail_body_tags)).check(matches(isDisplayed()))

    }


    private fun listMatcherVideos(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.detail_body_recyclerView_trailers)
    }

    @Suppress("unused")
    class NestedScrollViewExtension(scrolltoAction: ViewAction = ViewActions.scrollTo()) :
        ViewAction by scrolltoAction {
        override fun getConstraints(): Matcher<View> {
            return Matchers.allOf(
                withEffectiveVisibility(Visibility.VISIBLE),
                isDescendantOfA(
                    anyOf(
                        isAssignableFrom(NestedScrollView::class.java),
                        isAssignableFrom(ScrollView::class.java),
                        isAssignableFrom(HorizontalScrollView::class.java),
                        isAssignableFrom(ListView::class.java)
                    )
                )
            )
        }
    }
}