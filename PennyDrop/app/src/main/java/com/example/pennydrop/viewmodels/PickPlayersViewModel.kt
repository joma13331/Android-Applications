package com.example.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pennydrop.types.NewPlayer

class PickPlayersViewModel:ViewModel() {

    val players = MutableLiveData<List<NewPlayer>>().apply {
        this.value = (1..6).map {
            NewPlayer(
                canBeRemoved = it>2,
                canBeToggled = it>1
            )
        }
    }
}