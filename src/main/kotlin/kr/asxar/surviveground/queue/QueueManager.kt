package kr.asxar.surviveground.queue

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.game.GameStatus
import org.bukkit.GameMode
import org.bukkit.OfflinePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

class QueueManager {
    fun addPlayer(player: OfflinePlayer): Boolean {
        val result = queuePlayers.add(player.uniqueId)
        val gameManager = SurviveGround.gameManager

        val players = queuePlayers.size
        val needed = gameManager.numOfNeedPlayer()

        return result
    }

    fun removePlayer(player: OfflinePlayer) = queuePlayers.remove(player.uniqueId)

    var queuePlayers = mutableListOf<UUID>()
}

class QueueListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (SurviveGround.gameManager.gameStatus == GameStatus.WAITING)
            SurviveGround.queueManager.addPlayer(event.player)
        else if (!SurviveGround.gameManager.isPlayer(event.player))
            event.player.gameMode = GameMode.SPECTATOR
    }
}