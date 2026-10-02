package kr.asxar.surviveground.queue

import kr.asxar.surviveground.game.GameManager
import org.bukkit.OfflinePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID

class QueueManager {
    companion object {
        @JvmStatic
        fun addPlayer(player: OfflinePlayer): Boolean {
            val result = QueueManager().queuePlayers.add(player.uniqueId)

            val players = QueueManager().queuePlayers.size
            val needed = GameManager().numOfNeedPlayer()

            if (players == needed) {

            }
            if (players > needed) {

            }
            if (players < needed) {

            }

            return result
        }

        @JvmStatic
        fun removePlayer(player: OfflinePlayer) =
            QueueManager().queuePlayers.remove(player.uniqueId)
    }

    var queuePlayers = mutableListOf<UUID>()
}

class QueueListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        QueueManager.addPlayer(event.player)
    }
}