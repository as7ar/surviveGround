package kr.asxar.surviveground.game

import org.bukkit.Bukkit

class BorderManager {
    companion object {
        private const val INITIAL_SIZE = 2048.0
        private const val INITIAL_DURATION = 10L
        private const val SHRINK_FACTOR = 3.0
        private const val SHRINK_DURATION = 200L
    }

    fun init() {
        Bukkit.getWorlds().forEach { world ->
            world.worldBorder.changeSize(INITIAL_SIZE, INITIAL_DURATION)
        }
    }

    fun makeSmaller() {
        Bukkit.getWorlds().forEach { world ->
            val border = world.worldBorder
            val newSize = maxOf(border.size / 3.0, 1.0)

            border.changeSize(newSize, 200L)
        }
    }
}