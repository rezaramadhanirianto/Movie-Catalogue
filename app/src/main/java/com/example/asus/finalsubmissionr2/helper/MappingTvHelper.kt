package com.example.asus.finalsubmissionr2.helper

import android.database.Cursor
import com.example.asus.finalsubmissionr2.db.DatabaseContract
import com.example.asus.finalsubmissionr2.model.Movie


object MappingTvHelper {

    fun mapCursorToArrayList(notesCursor: Cursor): ArrayList<Movie> {
        val notesList = ArrayList<Movie>()
        while (notesCursor.moveToNext()) {
            val id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.Movie._ID))
            val title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.Movie.TITLE))
            val description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.Movie.DESCRIPTION))
            val date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.Movie.DATE))
            val img = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.Movie.IMG))
            if(date == "-"){
                notesList.add(Movie(id, title, description, date, img))
            }
        }
        return notesList
    }
}