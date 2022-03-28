package com.example.lesson10

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson10.AlbumListAdapter.AlbumListViewHolder
import com.example.lesson10.Album
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.lesson10.R
import com.squareup.picasso.Picasso
import android.widget.TextView

class AlbumListAdapter(private val context: Context) : RecyclerView.Adapter<AlbumListViewHolder>() {
    private var albumList: List<Album>? = null

    fun setData(list: List<Album>?) {
        albumList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_layout, parent, false)
        return AlbumListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        val album = albumList!![position]
        if (album == null) {
            return
        } else {
            val strId = album.id.toString()
            holder.tvId.text = strId
            holder.tvTitle.text = album.title
            Picasso.get().load(album.url).fit().into(holder.imgAlbum)
        }
    }

    override fun getItemCount(): Int {
        return if (albumList != null) {
            albumList!!.size
        } else {
            0
        }
    }

    inner class AlbumListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView
        val tvTitle: TextView
        val imgAlbum: ImageView

        init {
            imgAlbum = itemView.findViewById(R.id.img_album)
            tvId = itemView.findViewById(R.id.tv_id)
            tvTitle = itemView.findViewById(R.id.tv_title)
        }
    }
}