package com.example.pennydrop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pennydrop.R
import com.example.pennydrop.databinding.FragmentPickPlayersBinding
import com.example.pennydrop.viewmodels.GameViewModel
import com.example.pennydrop.viewmodels.PickPlayersViewModel
import kotlinx.coroutines.launch


class PickPlayersFragment : Fragment() {

    private val pickPlayersViewModel by activityViewModels<PickPlayersViewModel>()

    private val gameViewModel by activityViewModels<GameViewModel>()


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            // Inflate the layout for this fragment
            val binding = FragmentPickPlayersBinding.inflate(inflater, container, false).apply {
                this.vm = pickPlayersViewModel

                this.buttonPlayGame.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        gameViewModel.startGame(
                            pickPlayersViewModel.players.value
                                ?.filter { newPlayer ->
                                    newPlayer.isIncluded.get()
                                }?.map { newPlayer ->
                                    newPlayer.toPlayer()
                                } ?: emptyList()
                        )

                        findNavController().navigate(R.id.gameFragment)
                    }
                }
            }

            return binding.root
    }


}