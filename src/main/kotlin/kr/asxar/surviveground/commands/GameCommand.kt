package kr.asxar.surviveground.commands

import org.bukkit.command.CommandSender

class GameCommand: BaseCommand(
    "게임", listOf(), "", "kr.asxar.sg.game"
) {
    override fun execute(
        sender: CommandSender,
        args: Array<out String>
    ): Boolean {

        return true
    }

    override fun tabComplete(
        sender: CommandSender,
        args: Array<out String>
    ): List<String?> {
        return mutableListOf()
    }
}