package com.example.pennydrop.types

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.pennydrop.game.AI

@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true) var playerId:Long = 0,
    val playerName: String="",
    val isHuman: Boolean = true,
    val selectedAI: AI?=null
){
    @Ignore
    var pennies: Int = defaultPenniesCount
    @Ignore
    var isRolling: Boolean = false

    @Ignore
    var gamePlayerNumber: Int = -1


    fun penniesLeft(subtractPenny:Boolean =false) = (pennies - (if(subtractPenny) 1 else 0)) > 0

    companion object{
        const val defaultPenniesCount = 10
    }
}