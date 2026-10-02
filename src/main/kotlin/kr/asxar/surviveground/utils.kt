package kr.asxar.surviveground

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

fun runTask(run: Runnable) {
    Bukkit.getScheduler().runTask(SurviveGround.instance, run)
}

fun String.toMiniMessage(): Component = MiniMessage.miniMessage().deserialize(this)