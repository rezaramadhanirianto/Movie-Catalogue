package com.example.asus.finalsubmissionr2.fragment


import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asus.finalsubmissionr2.DetailMovieTv

import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.adapter.MovieTvShowAdapter
import com.example.asus.finalsubmissionr2.db.DatabaseContract
import com.example.asus.finalsubmissionr2.db.DatabaseContract.Movie.Companion.CONTENT_URI
import com.example.asus.finalsubmissionr2.db.MovieHelper
import com.example.asus.finalsubmissionr2.db.TvShowHelper
import com.example.asus.finalsubmissionr2.helper.MappingMovieHelper
import com.example.asus.finalsubmissionr2.helper.MappingTvHelper
import com.example.asus.finalsubmissionr2.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.progressBar
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavorite : Fragment() {
    private lateinit var adapter: MovieTvShowAdapter
    private lateinit var tvShowHelper: TvShowHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowHelper = TvShowHelper.getInstance(requireContext())
        adapter = MovieTvShowAdapter()
        loadNotesAsync()
    }
    private fun loadNotesAsync() {
        showLoading(true)
        val resolver = activity?.contentResolver
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = resolver?.query(CONTENT_URI, null, null, null, null) as Cursor
                MappingTvHelper.mapCursorToArrayList(cursor)
            }
            showLoading(false)
            val movies = deferredNotes.await()
            if (movies.size > 0) {
                adapter.notifyDataSetChanged()
                listTv.layoutManager = LinearLayoutManager(context)
                listTv.adapter = adapter
                adapter.setData(movies)
                adapter.setOnItemClickCallback(object : MovieTvShowAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: Movie) {
                        val moveWithObjectIntent = Intent(context, DetailMovieTv::class.java)
                        moveWithObjectIntent.putExtra(DetailMovieTv.EXTRA_FILM_TV, data)
                        startActivity(moveWithObjectIntent)
                    }
                })
            } else {
                nodata.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
