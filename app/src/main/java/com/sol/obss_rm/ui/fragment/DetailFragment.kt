package com.sol.obss_rm.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.sol.obss_rm.R
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.ui.view.SViewModel
import com.sol.obss_rm.ui.view.SViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.txt_status

@ExperimentalPagingApi
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: SViewModel by activityViewModels{ SViewModelFactory(Repository()) }
    private val args: DetailFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = args.character

        viewModel.getEpisode(character.episode.last().substringAfterLast("/episode/").toInt())

        viewModel.lastEpisode.observe(viewLifecycleOwner, {
            txt_last_episode_info.text= it.name + " - " + it.air_date
        })

        txt_status.text= character.status
        Picasso.get().load(character.image).into(img_character)
        txt_name.text= character.name
        txt_specie.text = character.species
        txt_gender.text = character.gender
        txt_number_of_episodes.text = character.episode.size.toString()
        txt_origin_location_info.text= character.origin.name
        txt_last_location_info.text= character.location.name
    }
}