package com.example.pennydrop.types

data class PlayerSummary(
    val id: Long,
    val name: String,
    val gamesPlayed: Int = 0,
    val wins: Int = 0,
    val isHuman: Boolean =true,
    val winsString: String = wins.toString()
)
