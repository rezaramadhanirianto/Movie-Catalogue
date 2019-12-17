package com.example.asus.favoriter2

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.asus.favoriter2.db.DatabaseContract
import com.example.asus.favoriter2.db.DatabaseContract.Movie.Companion.CONTENT_URI
import com.example.asus.favoriter2.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie_tv.*

class DetailMovieTv : AppCompatActivity(){
    private lateinit var uriWithId: Uri
    var exist = false
    companion object{
        const val EXTRA_FILM_TV = "extra_film_tv"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie_tv)
        val movie = intent.getParcelableExtra(EXTRA_FILM_TV) as Movie
        if(movie.date == "-"){
            uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)
            val a = contentResolver.query(uriWithId, null, null, null, null)
            if (a != null) {
                Log.d("Coba", "TV")
                val note = a.moveToFirst()
                a.close()
                if(note){
                    exist = true
                    favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp))
                }else{
                    Log.d("Coba", "GK ada")
                    exist = false
                    favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_red_24dp))
                }
            }else{
                exist = false
                favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_red_24dp))
            }
        }else{
            uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)
            val a = contentResolver?.query(uriWithId, null, null, null, null)

            if (a != null) {
                val note = a.moveToFirst()
                a.close()
                if(note){
                    exist = true
                    favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp))
                }else{
                    exist = false
                    favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_red_24dp))
                }
            }else{
                exist = false
                favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_red_24dp))
            }
        }

        if(movie.desc.toString() != ""){
            detail_desc.text = movie.desc.toString()
        }else{
            detail_desc.text = getString(R.string.check_desc)
        }

        detail_title.text = movie.title.toString()
        date.text = movie.date.toString()
        Glide.with(this).load("https://image.tmdb.org/t/p/w185${movie.img}").into(detail_photo);

        favorite.setOnClickListener(View.OnClickListener {
            if(exist == false){
                val values =ContentValues()
                if(movie.date != "-"){
                    values.put(DatabaseContract.Movie._ID, movie.id)
                    values.put(DatabaseContract.Movie.DESCRIPTION, movie.desc)
                    values.put(DatabaseContract.Movie.TITLE, movie.title)
                    values.put(DatabaseContract.Movie.IMG, movie.img)
                    values.put(DatabaseContract.Movie.DATE, movie.date)
                    contentResolver.insert(CONTENT_URI, values)
                }else{
                    values.put(DatabaseContract.Movie._ID, movie.id)
                    values.put(DatabaseContract.Movie.DESCRIPTION, movie.desc)
                    values.put(DatabaseContract.Movie.TITLE, movie.title)
                    values.put(DatabaseContract.Movie.IMG, movie.img)
                    values.put(DatabaseContract.Movie.DATE, movie.date)
                    contentResolver.insert(CONTENT_URI, values)
                }
                favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp))
            }else{
                var result = 0
                if(movie.date != "-") {
                    uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)
                    result = contentResolver.delete(uriWithId, null, null)

                }else{
                    uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)
                    result = contentResolver.delete(uriWithId, null, null)
                }
                favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_red_24dp))
                if (result > 0) {
                    Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Favorite::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}
