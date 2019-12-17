package com.example.asus.favoriter2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asus.favoriter2.R
import com.example.asus.favoriter2.model.Movie
import kotlinx.android.synthetic.main.item_movie_tv.view.*

class MovieTvShowAdapter : RecyclerView.Adapter<MovieTvShowAdapter.MovieTvShowViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val mData = ArrayList<Movie>()
    fun setData(items: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int) {
        this.mData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.mData.size)
    }
    inner class MovieTvShowViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView){
                title.text = movie.title
                date.text = movie.date
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185"+movie.img)
                    .into(img_item)

            }
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieTvShowViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie_tv, viewGroup, false)
        return MovieTvShowViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MovieTvShowViewHolder, position: Int) {
        holder.bind(mData[position])
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}