package com.sol.obss_rm.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.sol.obss_rm.R
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.model.Character
import com.sol.obss_rm.ui.view.SViewModel
import com.sol.obss_rm.ui.view.SViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.txt_status

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var isFavorite:Boolean = false
    private val viewModel: SViewModel by activityViewModels{ SViewModelFactory(Repository()) }
    private val args: DetailFragmentArgs by navArgs()
    lateinit var character:Character

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        character = args.character

        initObserver()

        initOnClick()

        getLastEpisode()

        loadCharacterInfo()
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver(){
        viewModel.lastEpisode.observe(viewLifecycleOwner, {
            txt_last_episode_info.text= it.name + " - " + it.air_date
        })
    }

    private fun initOnClick(){
        img_favorite.setOnClickListener {
            isFavorite = !isFavorite
            if(isFavorite)
                img_favorite.setBackgroundResource(R.drawable.ic_favorite)
            else
                img_favorite.setBackgroundResource(R.drawable.ic_unfavorite)
        }
    }

    private fun loadCharacterInfo(){
        txt_status.text= character.status
        Picasso.get().load(character.image).into(img_character)
        txt_name.text= character.name
        txt_specie.text = character.species
        txt_gender.text = character.gender
        txt_number_of_episodes.text = character.episode.size.toString()
        txt_origin_location_info.text= character.origin.name
        txt_last_location_info.text= character.location.name
    }

    private fun getLastEpisode(){
        viewModel.getEpisode(character.episode.last().substringAfterLast("/episode/").toInt())
    }
}