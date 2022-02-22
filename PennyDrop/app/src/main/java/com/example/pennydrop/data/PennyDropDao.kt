package com.example.pennydrop.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pennydrop.types.Player
import java.time.OffsetDateTime

@Dao
abstract class PennyDropDao {

    @Query("SELECT * FROM players WHERE playerName = :playerName")
    abstract fun getPlayer(playerName: String):Player?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGame(game: Game): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPlayer(player: Player): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPlayers(players:List<Player>): List<Long>

    @Update
    abstract suspend fun updateGame(game: Game)

    @Transaction
    @Query("SELECT * FROM games ORDER BY startTime DESC LIMIT 1")
    abstract fun getCurrentGameWithPlayers(): LiveData<GameWithPlayers>

    @Transaction
    @Query(
        """SELECT * FROM game_status WHERE gameId = (
            SELECT gameId FROM games WHERE endTime IS NULL ORDER BY startTime DESC LIMIT 1)
            ORDER BY gamePlayerNumber
            """
    )
    abstract fun getCurrentGameStatuses():LiveData<List<GameStatus>>

    @Query("""
        UPDATE games SET endTime=:endDate, gameState=:gameState WHERE endTime IS NULL
    """)
    abstract suspend fun closeOpenGames(
        endDate:OffsetDateTime = OffsetDateTime.now(),
        gameState: GameState = GameState.Cancelled
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGameStatuses(gameStatus: List<GameStatus>)

    @Transaction
    open suspend fun startGame(players:List<Player>, pennyCount: Int?): Long {
        this.closeOpenGames()

        val gameId = this.insertGame(
            Game(
                gameState = GameState.Started,
                currentTurnText = "The game has begun!!\n",
                canRoll = true
            )
        )

        val playerIds = players.map { player ->
            getPlayer(player.playerName)?.playerId ?: insertPlayer(player)
        }

        this.insertGameStatuses(
            playerIds.mapIndexed { index, playerId ->
                GameStatus(
                    gameId,
                    playerId,
                    index,
                    index == 0,
                    pennyCount?:Player.defaultPenniesCount

                )
            }
        )

        return gameId
    }

    @Update
    abstract suspend fun updateGameStatuses(gamesStatuses:List<GameStatus>)

    @Transaction
    open suspend fun updateGameAndStatuses(game: Game, statuses:List<GameStatus>){
        this.updateGame(game)
        this.updateGameStatuses(statuses)
    }

    @Query("""
        SELECT * FROM game_status gs WHERE gs.gameId IN(SELECT gameId FROM games WHERE gameState="Finished") 
    """)
    abstract fun getCompletedGameStatusesWithPlayers(): LiveData<List<GameStatusWithPlayer>>

}