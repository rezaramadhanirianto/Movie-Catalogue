package com.example.asus.finalsubmissionr2.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract{
    const val AUTHORITY = "com.example.asus.finalsubmissionr2"
    const val SCHEME ="content"
    internal class TvShow: BaseColumns{
        companion object{
            const val TABLE_NAME_TV = "tv"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
            const val IMG = "img"
            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_TV)
                .build()
        }
    }
    internal class Movie: BaseColumns{
        companion object{
            const val TABLE_NAME = "movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
            const val IMG = "img"
            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}