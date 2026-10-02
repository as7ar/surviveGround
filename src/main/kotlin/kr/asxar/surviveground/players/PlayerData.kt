package kr.asxar.surviveground.players

import java.util.*

class PlayerData {
    val kdaData = mutableMapOf<UUID, MutableList<Int>>()
    val totalDamage = mutableMapOf<UUID, MutableList<UUID>>()

    fun addKill(player: UUID) {
        val kda = kdaData[player] ?: mutableListOf(0,0,0)
        kda[0]+=1
        kdaData[player]=kda
    }
    fun addDeath(player: UUID) {
        val kda = kdaData[player] ?: mutableListOf(0,0,0)
        kda[1]+=1
        kdaData[player]=kda
    }
    fun addAssi(player: UUID) {
        val kda = kdaData[player] ?: mutableListOf(0,0,0)
        kda[2]+=1
        kdaData[player]=kda
    }
}