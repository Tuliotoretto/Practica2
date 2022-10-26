package com.jglognemra.practica2.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jglognemra.practica2.R
import com.jglognemra.practica2.model.Song
import com.jglognemra.practica2.databinding.SongListRowBinding
import com.jglognemra.practica2.view.fragments.ListFragmentDirections

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var songList = emptyList<Song>()
    private lateinit var genres : Array<String>

    inner class ViewHolder(itemView: SongListRowBinding): RecyclerView.ViewHolder(itemView.root) {
        val rowLayout = itemView.rowLayout
        val tvTitle = itemView.tvTitle
        val tvBand = itemView.tvBand
        val ivImage = itemView.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SongListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        genres = parent.context.resources.getStringArray(R.array.genres_array)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSong = songList[position]

        holder.tvTitle.text = currentSong.title
        holder.tvBand.text = currentSong.band
        holder.ivImage.setImageResource(getIconFor(currentSong.genre))

        holder.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentSong)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(song: List<Song>) {
        songList = song
        notifyDataSetChanged()
    }

    private fun getIconFor(genre: String): Int {
        return when (genre) {
            genres[0] -> R.drawable.ic_pop
            genres[1] -> R.drawable.ic_rock
            genres[2] -> R.drawable.ic_hiphop
            genres[3] -> R.drawable.ic_jazz
            genres[4] -> R.drawable.ic_reggaeton
            else -> R.drawable.ic_add
        }
    }
}