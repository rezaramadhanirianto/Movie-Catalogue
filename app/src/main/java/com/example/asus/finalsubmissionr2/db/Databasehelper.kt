package com.example.asus.finalsubmissionr2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Databasehelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "moviecatalogue"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE ${DatabaseContract.Movie.TABLE_NAME}" +
                " (${DatabaseContract.Movie._ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.Movie.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.Movie.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.Movie.DATE} TEXT NOT NULL," +
                " ${DatabaseContract.Movie.IMG} TEXT NOT NULL)"
        private val SQL_CREATE_TABLE_TV = "CREATE TABLE ${DatabaseContract.TvShow.TABLE_NAME_TV}" +
                " (${DatabaseContract.TvShow._ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.TvShow.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.TvShow.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.TvShow.DATE} TEXT NOT NULL," +
                " ${DatabaseContract.TvShow.IMG} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_TV)
        db.execSQL(SQL_CREATE_TABLE_MOVIE)

    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

            p0.execSQL("Drop table if exists ${DatabaseContract.Movie.TABLE_NAME}")
            p0.execSQL("Drop table if exists ${DatabaseContract.TvShow.TABLE_NAME_TV}")
            onCreate(p0)

    }
}