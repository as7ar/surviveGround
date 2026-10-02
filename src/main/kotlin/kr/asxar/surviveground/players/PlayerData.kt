package kr.asxar.surviveground.players

import kr.asxar.surviveground.data.PlayerStats
import java.util.*

class PlayerData {
    val stats = mutableMapOf<UUID, PlayerStats>()
    val totalDamage = mutableMapOf<UUID, MutableSet<UUID>>()

    fun getStats(player: UUID): PlayerStats {
        return stats.getOrPut(player) { PlayerStats() }
    }

    fun addKill(player: UUID) {
        getStats(player).kills++
    }

    fun addDeath(player: UUID) {
        getStats(player).deaths++
    }

    fun addAssist(player: UUID) {
        getStats(player).assists++
    }
}