package com.example.rickandmorty.sections.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.squareup.picasso.Picasso

class CharacterListAdapter(
    private val clickListener: (Character) -> Unit,
) : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    private val characterList = ArrayList<Character>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_character_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun updateList(list: List<Character>) {
        this.characterList.clear()
        this.characterList.addAll(list)

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        private val image: ImageView = v.findViewById(R.id.image_character)
        private val name: TextView = v.findViewById(R.id.text_character_name)
        private val species: TextView = v.findViewById(R.id.text_character_species)
        private val status: TextView = v.findViewById(R.id.text_character_status)
        private val gender: TextView = v.findViewById(R.id.text_character_gender)

        fun bind(character: Character) {
            Picasso.get().load(character.image).into(image)
            name.text = character.name
            species.text =
                itemView.resources.getString(R.string.character_adapter_species, character.species)
            status.text =
                itemView.resources.getString(R.string.character_adapter_status, character.status)
            gender.text =
                itemView.resources.getString(R.string.character_adapter_gender, character.gender)

            v.setOnClickListener {
                clickListener.invoke(characterList[adapterPosition])
            }
        }
    }
}