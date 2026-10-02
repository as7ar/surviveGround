package kr.asxar.surviveground

import kr.asxar.surviveground.events.PlayerEvents
import kr.asxar.surviveground.game.GameManager
import kr.asxar.surviveground.players.PlayerData
import kr.asxar.surviveground.queue.QueueListener
import kr.asxar.surviveground.tasks.ActionBarTask
import org.bukkit.plugin.java.JavaPlugin

class SurviveGround : JavaPlugin() {
    companion object {
        lateinit var instance: SurviveGround
        lateinit var playerData: PlayerData
        lateinit var gameManager: GameManager
    }

    override fun onLoad() {
        instance = this
        playerData = PlayerData()
        gameManager = GameManager()
        saveDefaultConfig()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerEvents(), this)
        server.pluginManager.registerEvents(QueueListener(), this)

        server.scheduler.runTaskTimerAsynchronously(this, ActionBarTask(), 0, 20)
    }
}
