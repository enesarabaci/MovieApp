package com.example.movieapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Model.Result
import com.example.movieapp.R
import com.example.movieapp.Util.Util
import com.example.movieapp.Util.loadImage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRecyclerAdapter @Inject constructor() : RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>() {

    private var onItemClickListener: ((Result) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesRecyclerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_trends, parent, false)
        return ViewHolder(view)
    }

    fun setOnItemClickListener(listener: ((Result) -> Unit)) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MoviesRecyclerAdapter.ViewHolder, position: Int) {
        val currentItem = list.get(position)

        val image = holder.view.findViewById<ImageView>(R.id.movie_image)
        val title = holder.view.findViewById<TextView>(R.id.movie_title)

        holder.view.apply {
            image.loadImage("${Util.IMAGE_BASE_URL}${currentItem.poster_path}")
            title.text = currentItem.original_title
            setOnClickListener {
                onItemClickListener?.let {
                    it(currentItem)
                }
            }
        }
    }

    private var list = ArrayList<Result>()

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<Result>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}