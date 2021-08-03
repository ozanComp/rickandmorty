package com.sol.obss_rm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sol.obss_rm.R
import com.sol.obss_rm.model.Character
import com.sol.obss_rm.ui.fragment.ListFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private var listCharacters = emptyList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(listCharacters[position])

        holder.itemView.setOnClickListener { view ->
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(listCharacters[position])
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return listCharacters.size
    }

    fun setCharacters(characters: List<Character>){
        listCharacters = characters
        notifyDataSetChanged()
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var isFavorite:Boolean = false
        var image = itemView.img_character
        var name = itemView.txt_name_character
        var statusType = itemView.txt_status
        var species = itemView.txt_species
        var favorite = itemView.img_favorite

        fun bind(character: Character?) {
            favorite.setBackgroundResource(R.drawable.ic_unfavorite)
            Picasso.get().load(character?.image).into(image)
            statusType.text = character?.status
            name.text = character?.name
            species.text = character?.species

            favorite.setOnClickListener {
                isFavorite = !isFavorite
                if(isFavorite)
                    favorite.setBackgroundResource(R.drawable.ic_favorite)
                else
                    favorite.setBackgroundResource(R.drawable.ic_unfavorite)
            }
        }
    }
}