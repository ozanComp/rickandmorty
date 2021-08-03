package com.sol.obss_rm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sol.obss_rm.R
import com.sol.obss_rm.model.Character
import com.sol.obss_rm.ui.fragment.ListFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class CharacterPageAdapter : PagingDataAdapter<Character, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character: Character? = getItem(position)
        (holder as? CharacterViewHolder)?.bind(character)
        holder.itemView.setOnClickListener { view ->
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(character!!)
            view.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder.getInstance(parent)
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup): CharacterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_list, parent, false)
                return CharacterViewHolder(view)
            }
        }

        var image = itemView.character_img
        var name = itemView.txt_name_character
        var statusType = itemView.txt_status
        var species = itemView.txt_species

        fun bind(character: Character?) {
            Picasso.get().load(character?.image).into(image)
            statusType.text = character?.status
            name.text = character?.name
            species.text = character?.species
        }
    }
}