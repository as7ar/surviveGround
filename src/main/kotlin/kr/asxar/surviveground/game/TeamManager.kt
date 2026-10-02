package kr.asxar.surviveground.game

import kr.asxar.surviveground.SurviveGround
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team
import java.util.*

class TeamManager {
    private val gameManager = SurviveGround.gameManager
    private val teamColors = listOf(
        NamedTextColor.RED,
        NamedTextColor.BLUE,
        NamedTextColor.YELLOW
    )

    private val scoreboard = Bukkit.getScoreboardManager().mainScoreboard

    var teams = mutableListOf<Team>()

    fun init() {
        teams.forEach { team ->
            team.players.toList().forEach(team::removePlayer)
            team.unregister()
        }

        teams.clear()

        repeat(gameManager.numOfTeam()) { index ->
            val color = teamColors[index]
            val team = scoreboard.getTeam("sg_${color}")
                ?: scoreboard.registerNewTeam("sg_${color}")

            team.color(color)
            team.prefix(Component.text("●").color(color))

            teams.add(team)
        }
    }

    fun setupTeams(players: List<UUID>) {
        val playerNum = gameManager.numOfTeamPlayer()
        val shuffled = players.shuffled()

        teams.forEachIndexed { index, team ->
            val start = index * playerNum
            val end = start + playerNum

            shuffled.subList(start, end).forEach { uuid ->
                val player = Bukkit.getPlayer(uuid) ?: return@forEach
                team.addPlayer(player)
            }
        }
    }
}