package com.example.asus.finalsubmissionr2.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asus.finalsubmissionr2.DetailMovieTv

import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.adapter.MovieTvShowAdapter
import com.example.asus.finalsubmissionr2.model.Movie
import com.example.asus.finalsubmissionr2.viewModel.MovieTvViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {
        private lateinit var adapter: MovieTvShowAdapter
        private lateinit var mainViewModel: MovieTvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var language = Locale.getDefault().getDisplayLanguage().toString()
        if(language == "Bahasa Indonesia"){
            language = "id"
        }else{
            language = "en-US"
        }


        adapter = MovieTvShowAdapter()
        adapter.notifyDataSetChanged()

        listMovie.layoutManager = LinearLayoutManager(context)
        listMovie.adapter = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MovieTvViewModel::class.java)
        mainViewModel.setMovie("movie",language)
        showLoading(true)

        mainViewModel.getMovies().observe(this, Observer { movieItems ->
            if (movieItems != null) {
                adapter.setData(movieItems)
                showLoading(false)
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
    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }



}
