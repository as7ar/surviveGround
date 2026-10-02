package kr.asxar.surviveground.players

import kr.asxar.surviveground.toMiniMessage
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class HotBarManager {
    fun setup(player: Player) {
        val spectorToggle = ItemStack(Material.ENDER_EYE).apply {
            val meta = this.itemMeta
            meta.displayName("<#578EF5>관전하기".toMiniMessage())
            this.itemMeta = meta
        }

        player.inventory.addItem(spectorToggle)
    }

    class HotbarLogicListener: Listener {
        @EventHandler
        fun PlayerInteractEvent.onInteract() {
            val item = item ?: return
            if (!this.action.isRightClick) return

        }
    }
}