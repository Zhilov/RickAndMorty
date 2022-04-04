package com.example.rickandmorty.sections.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R

class LocationListAdapter(
    private val clickListener: (Location) -> Unit
) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    private val locationList = ArrayList<Location>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_location_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    fun updateList(list : List<Location>){
        this.locationList.clear()
        this.locationList.addAll(list)

        notifyDataSetChanged()
    }

    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {

        private val name: TextView = v.findViewById(R.id.text_location_name)
        private val type: TextView = v.findViewById(R.id.text_location_type)
        private val dimension: TextView = v.findViewById(R.id.text_location_dimension)

        fun bind(location: Location) {
            name.text = location.name
            type.text = location.type
            dimension.text = location.dimension

            v.setOnClickListener {adapterPosition
                clickListener.invoke(locationList[adapterPosition])
            }
        }
    }
}