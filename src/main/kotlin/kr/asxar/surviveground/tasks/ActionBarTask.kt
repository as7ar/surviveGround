package kr.asxar.surviveground.tasks

import kr.asxar.surviveground.game.GameManager
import kr.asxar.surviveground.game.GameStatus
import kr.asxar.surviveground.queue.QueueManager
import kr.asxar.surviveground.runTask
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import java.util.function.Consumer

private var count: Int = 10
class ActionBarTask: Consumer<BukkitTask> {
    override fun accept(task: BukkitTask) {
        val status = GameManager().gameStatus
        val needed = GameManager().numOfNeedPlayer()
        val players = QueueManager().queuePlayers

        if (status == GameStatus.WAITING) {
            runTask {
                Bukkit.getOnlinePlayers().forEachIndexed { index, player ->
                    player.sendActionBar(MiniMessage.miniMessage().deserialize(
                        "<#F5EFE1>${players.size}<#A8BBA3>/<#91AC67>${needed}"
                    ))
                }
            }
        }

        if (status == GameStatus.STARTING) {
            if (count==0) {
                runTask {
                    GameManager().start(players)
                }
                count = 10
            }

            if (players.size < needed) {
                runTask {
                    Bukkit.getOnlinePlayers().forEachIndexed { index, player ->
                        player.sendActionBar(Component.text("플레이어가 부족합니다!").color(TextColor.color(0xA14646)))
                    }
                }
            }
            Bukkit.getOnlinePlayers().forEachIndexed { index, player ->
                val color = when(count) {
                    3 -> 0xEB895B
                    2 -> 0xDA6556
                    1 -> 0xA14646
                    else -> 0xFDB773
                }
                runTask {
                    player.sendActionBar(Component.text("$count").color(TextColor.color(color)))
                }
            }
            count-=1
        }

        if (status == GameStatus.PLAYING) {
            Bukkit.getOnlinePlayers().forEachIndexed { index, player ->
                player.sendActionBar(MiniMessage.miniMessage().deserialize(
                    "K/D/A"
                ))
            }
        }
    }
}