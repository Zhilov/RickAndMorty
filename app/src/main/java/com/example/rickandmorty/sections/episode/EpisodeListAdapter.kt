package com.example.rickandmorty.sections.episode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R

class EpisodeListAdapter(
    private val clickListener: (Episode) -> Unit,
) : RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>() {

    private val episodeList = ArrayList<Episode>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_episode_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodeList[position])
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    fun updateList(list: List<Episode>) {
        this.episodeList.clear()
        this.episodeList.addAll(list)

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        private val name: TextView = v.findViewById(R.id.text_episode_name)
        private val episode: TextView = v.findViewById(R.id.text_episode_episode)
        private val airDate: TextView = v.findViewById(R.id.text_episode_air_date)

        fun bind(episode: Episode) {
            name.text = episode.name
            episode.episode = episode.episode.replace("S", "Season: ")
            episode.episode = episode.episode.replace("E", "\n Episode: ")
            this.episode.text = episode.episode
            airDate.text = episode.air_date

            v.setOnClickListener {
                adapterPosition
                clickListener.invoke(episodeList[adapterPosition])
            }
        }
    }
}