package com.chadi.movieapp.api

import com.chadi.movieapp.utils.LiveDataTestUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(JUnit4::class)
class MovieServiceTest : ApiHelperAbstract<MovieService>() {

    private lateinit var service: MovieService

    @Before
    fun initService() {
        this.service = createService(MovieService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieKeywordsTest() {
        enqueueResponse("/keywords.json")
        val keywordResponse = getValue(service.fetchKeywords(1)) as ApiSuccessResponse

        assertRequestPath("/3/movie/1/keywords")
        assertThat(keywordResponse.body.id, `is`(100))
        assertThat(keywordResponse.body.keywords[0].id, `is`(1992))
        assertThat(keywordResponse.body.keywords[0].name, `is`("super hero"))
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieVideosTest() {
        enqueueResponse("/movie_videos.json")
        val movieVideosResponse = getValue(service.fetchVideos(1)) as ApiSuccessResponse
        assertThat(movieVideosResponse.body.id, `is`(550))
        assertThat(movieVideosResponse.body.results[0].id, `is`("1"))
        assertThat(movieVideosResponse.body.results[0].key, `is`("key"))
    }
}
