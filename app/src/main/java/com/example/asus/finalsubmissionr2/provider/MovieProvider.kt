package com.example.asus.finalsubmissionr2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.asus.finalsubmissionr2.db.DatabaseContract.AUTHORITY
import com.example.asus.finalsubmissionr2.db.DatabaseContract.Movie.Companion.CONTENT_URI
import com.example.asus.finalsubmissionr2.db.DatabaseContract.Movie.Companion.TABLE_NAME
import com.example.asus.finalsubmissionr2.db.DatabaseContract.TvShow.Companion.TABLE_NAME_TV
import com.example.asus.finalsubmissionr2.db.MovieHelper
import com.example.asus.finalsubmissionr2.db.TvShowHelper

class MovieProvider : ContentProvider() {

    companion object {
        private const val NOTE = 1
        private const val NOTE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: MovieHelper
        init {

            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE)

            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_NAME/#",
                NOTE_ID)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (NOTE_ID) {
            sUriMatcher.match(uri) -> {
                movieHelper.deleteById(uri.lastPathSegment.toString())
            }
            else -> {
                0
            }
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (NOTE) {
            sUriMatcher.match(uri) -> movieHelper.insert(values)
            else ->{
                Log.d("COBA", uri.toString())
                0

            }
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        movieHelper = MovieHelper.getInstance(context as Context)
        movieHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            NOTE -> cursor = movieHelper.queryAll()
            NOTE_ID -> {
                Log.d("coba", "BENER")
                cursor = movieHelper.queryById(uri.lastPathSegment.toString())
            }
            else -> cursor = null
        }
        return cursor
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
