package com.example.asus.finalsubmissionr2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.asus.finalsubmissionr2.db.DatabaseContract.Movie.Companion.TABLE_NAME
import com.example.asus.finalsubmissionr2.db.DatabaseContract.Movie.Companion._ID
import com.example.asus.finalsubmissionr2.db.DatabaseContract.TvShow.Companion.TABLE_NAME_TV
import com.example.asus.finalsubmissionr2.model.Movie
import java.sql.SQLException

class MovieHelper(context: Context){
    private val dataBaseHelper: Databasehelper = Databasehelper(context)

    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private const val DATABASE_TABLE_TV = TABLE_NAME_TV
        private var INSTANCE: MovieHelper? = null

        fun getInstance(context: Context): MovieHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }
    fun getAll(): ArrayList<Movie>{
        var movies = ArrayList<Movie>()
        var cursor = database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
        cursor.moveToFirst()
        var movie: Movie
        if (cursor.getCount() > 0) {
            do {
                movie = Movie()
                movie.id = (cursor.getInt(0))
                movie.desc = (cursor.getString(2).toString())
                movie.date = (cursor.getString(3).toString())
                movie.title = (cursor.getString(1).toString())
                movie.img = (cursor.getString(4).toString())
                movies.add(movie)
                cursor.moveToNext()
            } while (!cursor.isAfterLast())
        }
        cursor.close()
        return movies
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }
    fun queryById(id: String): Cursor {
        val cursor = database.query(DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null)
        return cursor
    }

    fun insert(values: ContentValues?): Long {
        Log.d("Coba", "KE Insert = true")
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

}