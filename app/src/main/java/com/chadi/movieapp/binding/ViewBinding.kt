package com.chadi.movieapp.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import com.chadi.movieapp.extension.bindResource
import com.chadi.movieapp.extension.visible
import com.chadi.movieapp.models.Keyword
import com.chadi.movieapp.models.Resource
import com.chadi.movieapp.models.Video
import com.chadi.movieapp.models.entity.Movie
import com.chadi.movieapp.models.entity.MoviePerson
import com.chadi.movieapp.models.entity.Tv
import com.chadi.movieapp.models.entity.TvPerson
import com.chadi.movieapp.models.network.PersonDetail
import com.chadi.movieapp.utils.KeywordListMapper
import com.chadi.movieapp.utils.StringUtils
import com.chadi.movieapp.view.adapter.VideoListAdapter

@BindingAdapter("visibilityByResource")
fun bindVisibilityByResource(view: View, resource: Resource<List<Any>>?) {
    view.bindResource(resource) {
        if (resource?.data?.isNotEmpty()!!) {
            view.visible()
        }
    }
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}


@BindingAdapter("mapKeywordList")
fun bindMapKeywordList(view: TagContainerLayout, resource: Resource<List<Keyword>>?) {
    resource?.let {
        view.bindResource(resource) {
            if (it.data != null) {
                view.tags = KeywordListMapper.mapToStringList(it.data)
                if (it.data.isNotEmpty()) {
                    view.visible()
                }
            }
        }
    }
}

@BindingAdapter("biography")
fun bindBiography(view: TextView, resource: Resource<PersonDetail>?) {
    view.bindResource(resource) {
        view.text = resource?.data?.biography
    }
}

@BindingAdapter("nameTags")
fun bindTags(view: TagContainerLayout, resource: Resource<PersonDetail>?) {
    view.bindResource(resource) {
        view.tags = resource?.data?.also_known_as
        if (resource?.data?.also_known_as?.isNotEmpty()!!) {
            view.visible()
        }
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindAirDate")
fun bindAirDateForTvPerson(view: TextView, tv: TvPerson) {
    tv.first_air_date?.let { view.text = "First Air Date: ${tv.first_air_date}" }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindReleaseDate")
fun bindReleaseDate(view: TextView, movie: Movie) {
    view.text = "Release Date: ${movie.release_date}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindReleaseDate")
fun bindReleaseDateForMoviePerson(view: TextView, movie: MoviePerson) {
    view.text = "Release Date: ${movie.release_date}"
}


@SuppressLint("SetTextI18n")
@BindingAdapter("bindMovieGenre")
fun bindMovieGenre(view: TextView, movie: Movie) {
    view.text = "Genre: ${StringUtils.getMovieGenresById(movie.genre_ids)}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindMovieGenreForMoviePerson")
fun bindMovieGenreForMoviePerson(view: TextView, movie: MoviePerson) {
    view.text = "Genre: ${StringUtils.getMovieGenresById(movie.genre_ids)}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindAirDate")
fun bindAirDate(view: TextView, tv: Tv) {
    tv.first_air_date?.let { view.text = "First Air Date: ${tv.first_air_date}" }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindTvGenre")
fun bindTvGenre(view: TextView, tv: Tv) {
    view.text = "Genre: ${StringUtils.getTvGenresById(tv.genre_ids)}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindTvGenreForTvPerson")
fun bindTvGenreForTvPerson(view: TextView, tv: TvPerson) {
    view.text = "Genre: ${StringUtils.getTvGenresById(tv.genre_ids)}"
}


@BindingAdapter("setCharacterForTvPerson")
fun setCharacterForTv(textView: TextView, tv: TvPerson) {
    textView.text = tv.let {
        if (tv.character.isNotEmpty()) "Ch.: ${tv.character}"
        else {
            textView.visibility = View.GONE
            ""
        }
    }
}

@BindingAdapter("setCharacterForMoviePerson")
fun setCharacterForMovie(textView: TextView, movie: MoviePerson) {
    textView.text = movie.let {
        if (movie.character.isNotEmpty()) "Ch.: ${movie.character}"
        else {
            textView.visibility = View.GONE
            ""
        }
    }
}


@BindingAdapter("adapterVideoList")
fun bindAdapterVideoList(view: RecyclerView, resource: Resource<List<Video>>?) {
    view.bindResource(resource) {
        if (resource != null) {
            val adapter = view.adapter as? VideoListAdapter
            adapter?.submitList(resource.data)
            if (resource.data?.isNotEmpty()!!) {
                view.visible()
            }
        }
    }
}
