
package com.chadi.movieapp

import android.app.Application

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [com.chadi.movieapp.util.MovieGuideTestRunner].
 */
class TestMovieApp : Application()
