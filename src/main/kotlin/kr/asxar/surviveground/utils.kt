package kr.asxar.surviveground

import org.bukkit.Bukkit

fun runTask(run: Runnable) {
    Bukkit.getScheduler().runTask(SurviveGround.instance, run)
}