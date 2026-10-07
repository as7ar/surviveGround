package kr.asxar.surviveground.events

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.game.ToastManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerEvents: Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        SurviveGround.playerData.totalDamage[event.player.uniqueId]?.clear()
        event.showDeathMessages=false
    }

    @EventHandler
    fun onPlayerKill(event: EntityDeathEvent) {
        if (event.entity !is Player) return
        val killer = event.entity.killer ?: return
        Bukkit.broadcast(
            Component.text("${killer.name} -💀> ${event.entity.name}")
                .color(TextColor.color(0xD45060))
        )

        ToastManager(SurviveGround.instance).showKill(
            killer, event.entity as Player
        ) // todo: TEST

        val playerData = SurviveGround.playerData
        val victim = event.entity.uniqueId

        playerData.addKill(killer.uniqueId)
        playerData.addDeath(victim)

        val attackers = playerData.totalDamage.remove(victim) ?: return
        attackers.forEach { attacker ->
            if (attacker == killer.uniqueId) return@forEach
            playerData.addAssist(attacker)
        }

        val gameManager = SurviveGround.gameManager

    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        event.player.gameMode= GameMode.SPECTATOR
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (event.isCancelled) return
        if (event.damager !is Player || event.entity !is Player) return
        if (event.finalDamage <= 0) return

        val victim = event.entity.uniqueId
        val attacker = event.damager.uniqueId

        SurviveGround.playerData.totalDamage
            .getOrPut(victim) { mutableSetOf() }
            .add(attacker)
    }
}