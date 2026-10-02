package kr.asxar.surviveground.tasks

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.game.GameStatus
import kr.asxar.surviveground.toMiniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import java.util.function.Consumer

private var count = 10

class ActionBarTask : Consumer<BukkitTask> {
    override fun accept(task: BukkitTask) {
        val gameManager = SurviveGround.gameManager
        val status = gameManager.gameStatus
        val needed = gameManager.numOfNeedPlayer()
        val players = SurviveGround.queueManager.queuePlayers

        if (status == GameStatus.WAITING) {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.sendActionBar(
                    MiniMessage.miniMessage().deserialize(
                        "<#F5EFE1>${players.size}<#A8BBA3>/<#91AC67>${needed}"
                    )
                )
            }
        }

        if (status == GameStatus.STARTING) {
            if (count <= 0) {
                gameManager.start(players)
                count = 10
                return
            }

            if (players.size < needed) {
                gameManager.gameStatus = GameStatus.WAITING
                count = 10

                Bukkit.getOnlinePlayers().forEach { player ->
                    player.sendActionBar(
                        Component.text("플레이어가 부족합니다!")
                            .color(TextColor.color(0xA14646))
                    )
                }

                return
            }
        }

        if (players.size == needed && gameManager.gameStatus != GameStatus.PLAYING) {
            gameManager.gameStatus = GameStatus.STARTING

            val color = when (count) {
                3 -> 0xEB895B
                2 -> 0xDA6556
                1 -> 0xA14646
                else -> 0xFDB773
            }

            Bukkit.getOnlinePlayers().forEach { player ->
                player.sendActionBar(
                    Component.text("$count")
                        .color(TextColor.color(color))
                )
            }

            count--
        }

        if (status == GameStatus.PLAYING) {
            Bukkit.getOnlinePlayers().forEach { player ->
                val stats = SurviveGround.playerData.getStats(player.uniqueId)

                player.sendActionBar(
                    "<#830000>${stats.kills}<#AEC4D4>/<#EB895B>${stats.deaths}<#AEC4D4>/<#76C0EC>${stats.assists}".toMiniMessage()
                )
            }
        }
    }
}