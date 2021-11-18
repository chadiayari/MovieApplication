package com.chadi.movieapp.fragment.people

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.chadi.movieapp.R
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.entity.MoviePerson
import com.chadi.movieapp.models.entity.TvPerson
import com.chadi.movieapp.models.network.PersonDetail
import com.chadi.movieapp.util.*
import com.chadi.movieapp.utils.MockTestUtil.Companion.mockMoviePerson
import com.chadi.movieapp.utils.MockTestUtil.Companion.mockPerson
import com.chadi.movieapp.utils.MockTestUtil.Companion.mockPersonDetail
import com.chadi.movieapp.utils.MockTestUtil.Companion.mockTvPerson
import com.chadi.movieapp.view.ui.person.detail.CelebrityDetailFragment
import com.chadi.movieapp.view.ui.person.detail.CelebrityDetailFragmentArgs
import com.chadi.movieapp.view.ui.person.detail.PersonDetailViewModel
import com.nhaarman.mockitokotlin2.whenever
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class CelebrityDetailFragmentTest {

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

    private lateinit var viewModel: PersonDetailViewModel

    private val personLiveData = MutableLiveData<Resource<PersonDetail>>()
    private val moviesForCelebrity = MutableLiveData<Resource<List<MoviePerson>>>()
    private val tvsForCelebrity = MutableLiveData<Resource<List<TvPerson>>>()

    @Before
    fun init() {
        viewModel = Mockito.mock(PersonDetailViewModel::class.java)

        whenever(viewModel.personLiveData).thenReturn(personLiveData)
        whenever(viewModel.moviesOfCelebrity).thenReturn(moviesForCelebrity)
        whenever(viewModel.tvsOfCelebrity).thenReturn(tvsForCelebrity)

        //Two ways to create a bundle
        /*IMPORTANT NOTE*/
        /*The key must be the same as android:name in the argument Tag in your navGraph.*/
        //1- val bundle = bundleOf("movie" to Movie(1))

        // 2-
        val bundle = CelebrityDetailFragmentArgs(mockPerson()).toBundle()

        val scenario = launchFragmentInContainer(
            bundle, themeResId = R.style.AppTheme
        ) {
            CelebrityDetailFragment().apply {
                viewModelFactory = ViewModelUtil.createFor(viewModel)
                appExecutors = countingAppExecutors.appExecutors
            }
        }

        dataBindingIdlingResourceRule.monitorFragment(scenario)
        runOnUiThread {
            navController.setGraph(R.navigation.star)
        }
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
            fragment.disableProgressBarAnimations()
        }
    }

    @Test
    fun testPersonDetail() {
        // Given
        val personDetail = mockPersonDetail()
        val tvsForPerson = listOf(mockTvPerson())
        val moviesForPerson = listOf(mockMoviePerson())

        // Action
        personLiveData.postValue(Resource.success(personDetail, false))
        moviesForCelebrity.postValue(Resource.success(moviesForPerson, false))
        tvsForCelebrity.postValue(Resource.success(tvsForPerson, false))

        // Assert ... Check
        onView(withId(R.id.person_detail_profile)).check(matches(isDisplayed()))
        onView(withId(R.id.person_detail_biography)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_person_tags)).check(matches(isDisplayed()))

        scrollTo(R.id.recycler_view_celebrity_movies)
        onView(listMatcherMoviesCelebrity().atPosition(0))
            .check(matches(isDisplayed()))

        scrollTo(R.id.recycler_view_celebrity_tvs)
        onView(listMatcherTvsCelebrity().atPosition(0))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCelebrityTvsList() {
        val tvsForPerson = listOf(mockTvPerson(), mockTvPerson(), mockTvPerson(), mockTvPerson())
        tvsForCelebrity.postValue(Resource.success(tvsForPerson, false))
        val action = RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3)
        onView(withId(R.id.recycler_view_celebrity_tvs)).perform(action)
        onView(listMatcherTvsCelebrity().atPosition(3)).check(
            matches(isDisplayed())
        )
        onView(listMatcherTvsCelebrity().atPosition(3)).check(
            matches(hasDescendant(withText("Ozark")))
        )
    }

    @Test
    fun testCelebrityMoviesList() {
        val moviesForPerson =
            listOf(mockMoviePerson(), mockMoviePerson(), mockMoviePerson(), mockMoviePerson())
        moviesForCelebrity.postValue(Resource.success(moviesForPerson, false))
        val action = RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3)
        onView(withId(R.id.recycler_view_celebrity_movies)).perform(action)
        onView(listMatcherMoviesCelebrity().atPosition(3)).check(
            matches(isDisplayed())
        )
        onView(listMatcherMoviesCelebrity().atPosition(3)).check(
            matches(hasDescendant(withText("Troy")))
        )
    }


    private fun listMatcherMoviesCelebrity(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recycler_view_celebrity_movies)
    }

    private fun listMatcherTvsCelebrity(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recycler_view_celebrity_tvs)
    }
}