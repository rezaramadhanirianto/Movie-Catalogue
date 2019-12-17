package com.example.asus.finalsubmissionr2.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asus.finalsubmissionr2.DetailMovieTv
import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.adapter.MovieTvShowAdapter
import com.example.asus.finalsubmissionr2.model.Movie
import com.example.asus.finalsubmissionr2.viewModel.MovieTvViewModel
import com.example.asus.finalsubmissionr2.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_search_fragment_tv.*
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.android.synthetic.main.fragment_search_movie.search
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragmentMovie : Fragment() {
    private lateinit var adapter: MovieTvShowAdapter
    private lateinit var mainViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                progressBarMovie.visibility = View.VISIBLE
                loadMovie(query)
                return true
            }
        })
    }

    private fun loadMovie(query: String?){

        var language = Locale.getDefault().getDisplayLanguage().toString()
        if(language == "Bahasa Indonesia"){
            language = "id"
        }else{
            language = "en-US"
        }


        adapter = MovieTvShowAdapter()
        adapter.notifyDataSetChanged()

        listMovieSearch.layoutManager = LinearLayoutManager(context)
        listMovieSearch.adapter = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SearchViewModel::class.java)
        mainViewModel.setMovie("movie",language, query)

        mainViewModel.getMovies().observe(this, androidx.lifecycle.Observer {
            movieItems ->
                if (movieItems != null) {
                    progressBarMovie.visibility = View.GONE
                    adapter.setData(movieItems)
                    adapter.setOnItemClickCallback(object : MovieTvShowAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: Movie) {

                            val moveWithObjectIntent = Intent(context, DetailMovieTv::class.java)
                            moveWithObjectIntent.putExtra(DetailMovieTv.EXTRA_FILM_TV, data)
                            startActivity(moveWithObjectIntent)
                        }
                    })
                }
            })
        }


}
