package kr.asxar.surviveground.events

import kr.asxar.surviveground.SurviveGround
import kr.asxar.surviveground.players.PlayerData
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
import java.util.UUID

class PlayerEvents: Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.showDeathMessages=false
    }

    @EventHandler
    fun onPlayerKill(event: EntityDeathEvent) {
        if (event.entity.killer !is Player || event.entity !is Player) return
        val killer = event.entity.killer ?: return
        val victim = event.entity as Player

        Bukkit.broadcast(Component.text("${killer.name} -💀> ${victim.name}").color(TextColor.color(0xD45060)))

        val playerData = SurviveGround.playerData
        playerData.addKill(killer.uniqueId)
        playerData.addDeath(victim.uniqueId)

        for (assistance in playerData.totalDamage[victim.uniqueId] ?: return) {
            if (assistance==killer.uniqueId) continue
            playerData.addAssi(assistance)
        }
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        event.player.gameMode= GameMode.SPECTATOR
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player || event.entity !is Player) return
        val attackers = SurviveGround.playerData.totalDamage[event.entity.uniqueId] ?: mutableListOf()
        attackers.add(event.damager.uniqueId)
        SurviveGround.playerData.totalDamage[event.entity.uniqueId]=attackers
    }
}