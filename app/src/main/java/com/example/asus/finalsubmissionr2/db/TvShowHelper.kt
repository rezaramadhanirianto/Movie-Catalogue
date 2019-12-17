package com.example.asus.finalsubmissionr2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.asus.finalsubmissionr2.db.DatabaseContract.TvShow.Companion.TABLE_NAME_TV
import com.example.asus.finalsubmissionr2.db.DatabaseContract.TvShow.Companion._ID
import java.sql.SQLException

class TvShowHelper(context: Context){
    private val dataBaseHelper: Databasehelper = Databasehelper(context)

    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME_TV
        private var INSTANCE: TvShowHelper? = null

        fun getInstance(context: Context): TvShowHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TvShowHelper(context)
                    }
                }
            }
            return INSTANCE as TvShowHelper
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