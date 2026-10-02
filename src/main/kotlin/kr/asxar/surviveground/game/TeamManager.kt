package kr.asxar.surviveground.game

import kr.asxar.surviveground.SurviveGround
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team
import java.util.UUID

class TeamManager {
    private val gameManager = SurviveGround.gameManager

    private val teamsId = listOf(NamedTextColor.RED, NamedTextColor.BLUE, NamedTextColor.YELLOW)
    private val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    var teams = mutableListOf<Team>()

    fun init() {
        teams.forEach {
            for (player in it.players) { it.removePlayer(player) }
            it.unregister()
        }
        teams.clear()

        val t = gameManager.numOfTeam()-1
        for(i in 0..t) {
            val team = scoreboard.getTeam(teamsId[i].toString().lowercase())
                ?: scoreboard.registerNewTeam(teamsId[i].toString().lowercase())
            team.color(teamsId[i])
            team.prefix(Component.text("●").color(teamsId[i]))
            teams.add(team)
        }
    }

    fun setupTeams(players: List<UUID>) {
        val mutable = players.toMutableList()

        val gameManager = SurviveGround.gameManager
        val playerNum = gameManager.numOfTeamPlayer()
        repeat(gameManager.numOfTeam()) {
            repeat(playerNum) {
                val uuid = mutable.random()
                mutable.remove(uuid)
                val player = Bukkit.getPlayer(uuid) ?: return@repeat
                addPlayer2Team(player)
            }
        }
    }

    private fun addPlayer2Team(player: OfflinePlayer) {
        val playerNum = gameManager.numOfTeamPlayer()

        val team = teams.random()
        if (team.players.size >= playerNum) {
            addPlayer2Team(player)
        } else {
            team.addPlayer(player)
        }
    }
}