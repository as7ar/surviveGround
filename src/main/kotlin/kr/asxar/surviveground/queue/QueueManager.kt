package kr.asxar.surviveground.queue

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.game.GameStatus
import org.bukkit.GameMode
import org.bukkit.OfflinePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID

class QueueManager {
    var queuePlayers = mutableListOf<UUID>()

    fun addPlayer(player: OfflinePlayer): Boolean {
        if (queuePlayers.size >= SurviveGround.gameManager.numOfNeedPlayer()) return false
        if (player.uniqueId in queuePlayers) return false

        return queuePlayers.add(player.uniqueId)
    }

    fun removePlayer(player: OfflinePlayer): Boolean {
        return queuePlayers.remove(player.uniqueId)
    }
}

class QueueListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val gameManager = SurviveGround.gameManager

        if (gameManager.gameStatus == GameStatus.WAITING) {
            if (!SurviveGround.queueManager.addPlayer(event.player)) {
                event.player.gameMode = GameMode.SPECTATOR
            }

            return
        }

        if (!gameManager.isPlayer(event.player)) {
            event.player.gameMode = GameMode.SPECTATOR
        }
    }
}