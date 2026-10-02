package kr.asxar.surviveground.game

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.queue.GameMode
import java.util.*

class GameManager {
    private val plugin = SurviveGround.instance
    var gameMode: GameMode = GameMode.PLAYERS5vs5
    var gameStatus: GameStatus = GameStatus.WAITING

    fun start(players: List<UUID>) {
        gameStatus= GameStatus.PLAYING
    }

    fun cancel() {}

    fun end(winner: UUID) {}

    fun setGameMode(mode: GameMode) {
        gameMode = mode
    }

    fun numOfNeedPlayer() = when(gameMode) {
        GameMode.PLAYERS3vs3vs3 -> 9
        GameMode.PLAYERS5vs5 -> 10
        GameMode.PLAYERS3vs3 -> 6
        GameMode.PLAYERS1vs1 -> 2
    }
}