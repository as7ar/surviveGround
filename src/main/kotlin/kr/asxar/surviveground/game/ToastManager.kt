package kr.asxar.surviveground.game

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class ToastManager(
    private val plugin: JavaPlugin
) {

    fun showKill(killer: Player, victim: Player) {
        val key = NamespacedKey(
            plugin,
            "kill_${UUID.randomUUID().toString().replace("-", "")}"
        )

        try {
            plugin.server.unsafe.loadAdvancement(
                key,
                createAdvancement(killer, victim)
            )

            val advancement = Bukkit.getAdvancement(key) ?: return
            val progress = killer.getAdvancementProgress(advancement)

            progress.awardCriteria("kill")

            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                progress.revokeCriteria("kill")
                plugin.server.unsafe.removeAdvancement(key)
            }, 20L)
        } catch (e: Exception) {
            plugin.server.unsafe.removeAdvancement(key)
            e.printStackTrace()
        }
    }

    private fun createAdvancement(
        killer: Player,
        victim: Player
    ): String {
        val root = JsonObject()

        root.add(
            "display",
            JsonObject().apply {
                add(
                    "icon",
                    JsonObject().apply {
                        addProperty("id", "minecraft:player_head")

                        add(
                            "components",
                            JsonObject().apply {
                                add(
                                    "minecraft:profile",
                                    JsonObject().apply {
                                        addProperty("name", victim.name)
                                    }
                                )
                            }
                        )
                    }
                )

                add(
                    "title",
                    JsonObject().apply {
                        addProperty(
                            "text",
                            "${killer.name} -💀> ${victim.name}"
                        )
                    }
                )

                addProperty("description", "")
                addProperty("frame", "task")
                addProperty("show_toast", true)
                addProperty("announce_to_chat", false)
                addProperty("hidden", true)
            }
        )

        root.add(
            "criteria",
            JsonObject().apply {
                add(
                    "kill",
                    JsonObject().apply {
                        addProperty(
                            "trigger",
                            "minecraft:impossible"
                        )
                    }
                )
            }
        )

        root.add(
            "requirements",
            JsonArray().apply {
                add(
                    JsonArray().apply {
                        add("kill")
                    }
                )
            }
        )

        return root.toString()
    }
}