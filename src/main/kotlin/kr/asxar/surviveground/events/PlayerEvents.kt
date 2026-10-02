package kr.asxar.surviveground.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerEvents: Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {}
}