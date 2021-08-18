package com.dhorowitz.openmovie.discover.presentation.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhorowitz.openmovie.discover.R
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity
import com.squareup.picasso.Picasso

class DiscoverAdapter() :
    ListAdapter<DiscoverViewEntity, RecyclerView.ViewHolder>(ListItemCallback()) {

    class ListItemCallback : DiffUtil.ItemCallback<DiscoverViewEntity>() {
        override fun areItemsTheSame(oldItem: DiscoverViewEntity, newItem: DiscoverViewEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DiscoverViewEntity, newItem: DiscoverViewEntity) =
            oldItem.imageUrl == newItem.imageUrl && oldItem.title == newItem.title

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as DiscoverViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.discover_list_item, parent, false)
        return DiscoverViewHolder(view)
    }

    inner class DiscoverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView by lazy { itemView.findViewById(R.id.posterImageView) }

        fun bind(item: DiscoverViewEntity) {
            Picasso.get().load(item.imageUrl).into(posterImageView)
        }
    }
}