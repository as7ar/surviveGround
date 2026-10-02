package kr.asxar.surviveground.game

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.queue.GameMode
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

class GameManager {
    private val plugin = SurviveGround.instance
    var gameMode: GameMode = GameMode.PLAYERS5vs5
    var gameStatus: GameStatus = GameStatus.WAITING

    val startLocation = mutableMapOf<String, Location>()
    // todo: 팀, 팀별 시작 위치, 자기장, 승자 표시, 후원API,

    fun start(players: List<UUID>) {
        gameStatus= GameStatus.PLAYING

        for (player in Bukkit.getOnlinePlayers()) {
            player.inventory.clear()
        }
    }

    fun cancel() {}

    fun end(winner: UUID) {
        for(player in Bukkit.getOnlinePlayers()) {
            player.inventory.clear()

            val targetWorld = Bukkit.getWorld("world")
            if (targetWorld != null) {
                val highestY = targetWorld.getHighestBlockYAt(0, 0)
                val loc = Location(targetWorld, 0.5, highestY + 1.0, 0.5)
                player.teleport(loc)
            }

            player.gameMode = org.bukkit.GameMode.SURVIVAL
        }
        gameStatus= GameStatus.WAITING
    }

    fun setGameMode(mode: GameMode) {
        gameMode = mode
    }

    fun numOfNeedPlayer() = when(gameMode) {
        GameMode.PLAYERS3vs3vs3 -> 9
        GameMode.PLAYERS5vs5 -> 10
        GameMode.PLAYERS3vs3 -> 6
        GameMode.PLAYERS1vs1 -> 2
    }

    fun numOfTeam() = when(gameMode) {
        GameMode.PLAYERS3vs3vs3 -> 3
        else -> 2
    }

    fun numOfTeamPlayer() = when(gameMode) {
        GameMode.PLAYERS3vs3vs3, GameMode.PLAYERS3vs3 -> 3
        GameMode.PLAYERS1vs1 -> 1
        GameMode.PLAYERS5vs5 -> 5
    }
}