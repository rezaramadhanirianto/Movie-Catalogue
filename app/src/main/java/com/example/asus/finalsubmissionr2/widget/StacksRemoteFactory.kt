package com.example.asus.finalsubmissionr2.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.asus.finalsubmissionr2.R
import com.example.asus.finalsubmissionr2.db.MovieHelper
import com.example.asus.finalsubmissionr2.model.Movie
import java.lang.Exception

internal class StacksRemoteFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory{
    private lateinit var favoriteHelper: MovieHelper
    private var list = ArrayList<Movie>()

    override fun onDataSetChanged() {
        favoriteHelper = MovieHelper(mContext)
        favoriteHelper.open()
        list = java.util.ArrayList<Movie>()
        list.addAll(favoriteHelper.getAll())
    }
    override fun onCreate() {

    }

    override fun getViewAt(p0: Int): RemoteViews {
        Log.d("COBA", list[p0].img.toString())
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        var bmp : Bitmap? = null
        try {
            bmp = Glide.with(mContext)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185${list[p0].img}")
                .error(ColorDrawable(mContext.resources.getColor(R.color.colorPrimary)))
                .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                .get() as Bitmap
        } catch (e : Exception) {
            e.printStackTrace()
        }

        rv.setImageViewBitmap(R.id.imageView, bmp)
        rv.setTextViewText(R.id.banner_text, list[p0].title)
        val extras = bundleOf(
            StackWidget.EXTRA_ITEM to p0
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int = list.size

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false



    override fun onDestroy() {

    }

}