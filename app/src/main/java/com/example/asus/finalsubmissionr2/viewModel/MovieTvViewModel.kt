package com.example.asus.finalsubmissionr2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asus.finalsubmissionr2.model.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MovieTvViewModel : ViewModel() {
    companion object{
        private const val API_KEY = "6e4227ca7e0d74b4abb39cf05b473946"
    }
    val listMovies = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovie(type: String, lang: String){
        val client  = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "http://api.themoviedb.org/3/discover/$type?api_key=$API_KEY&language=$lang"
Log.d("COBA", "URL $url")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                if(type == "movie"){
                    try{
                        val result = String(responseBody)
                        val responseObject = JSONObject(result)
                        val list = responseObject.getJSONArray("results")
                        for (i in 1 until list.length()) {
                            val movie = list.getJSONObject(i)
                            val movieItems = Movie()
                            movieItems.id = movie.getInt("id")
                            movieItems.title = movie.getString("title")
                            movieItems.desc = movie.getString("overview")
                            movieItems.date = movie.getString("release_date")
                            movieItems.img = movie.getString("poster_path")
                            listItems.add(movieItems)
                        }
                        listMovies.postValue(listItems)
                    }catch (e: Exception){
                        Log.d("GAGAL", e.message.toString())
                    }
                }else{
                    try{
                        val result = String(responseBody)
                        val responseObject = JSONObject(result)
                        val list = responseObject.getJSONArray("results")
                        for (i in 0 until list.length()) {
                            if(i == 10){

                            }else{
                                val movie = list.getJSONObject(i)
                                val movieItems = Movie()
                                movieItems.id = movie.getInt("id")
                                movieItems.title = movie.getString("name")
                                movieItems.desc = movie.getString("overview")
                                movieItems.date = " "
                                movieItems.img = movie.getString("poster_path")
                                listItems.add(movieItems)
                            }

                        }
                        listMovies.postValue(listItems)
                    }catch (e: Exception){
                        Log.d("GAGAL", e.message.toString())
                    }
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                if (error != null) {
                    Log.d("onFailure", error.message.toString())
                }
            }
        })

    }
    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}