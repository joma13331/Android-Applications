package com.example.pennydrop.data

import com.example.pennydrop.types.Player

class PennyDropRepository(private val pennyDropDao: PennyDropDao) {

    fun getCurrentGameWithPlayers() = pennyDropDao.getCurrentGameWithPlayers()

    fun getCurrentGameStatuses() = pennyDropDao.getCurrentGameStatuses()

    suspend fun startGame(players:List<Player>, pennyCount: Int?) = pennyDropDao.startGame(players, pennyCount)

    suspend fun updateGameAndStatuses(game: Game, statuses: List<GameStatus>) = pennyDropDao.updateGameAndStatuses(game, statuses)

    fun getCompletedGameStatusesWithPlayers(
        finishedGameState: GameState = GameState.Finished,
            ) = pennyDropDao.getCompletedGameStatusesWithPlayers()

    companion object{

        @Volatile
        private var instance:PennyDropRepository? = null

        fun getInstance(pennyDropDao: PennyDropDao) =
            this.instance?: synchronized(this){
                instance?:PennyDropRepository(pennyDropDao).also {
                    instance=it
                }

            }

    }
}