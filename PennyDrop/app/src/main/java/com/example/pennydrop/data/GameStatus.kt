package com.example.pennydrop.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.pennydrop.types.Player

@Entity(tableName = "game_status",
primaryKeys = ["gameId", "playerId"],
foreignKeys = [ForeignKey(
    entity = Game::class,
    parentColumns = ["gameId"],
    childColumns = ["gameId"]
), ForeignKey(
    entity = Player::class,
    parentColumns = ["playerId"],
    childColumns = ["playerId"]
)])

data class GameStatus(
    val gameId: Long,
    @ColumnInfo(index = true) val playerId: Long,
    val gamePlayerNumber: Int,
    val isRolling: Boolean = false,
    val pennies: Int = Player.defaultPenniesCount
)
