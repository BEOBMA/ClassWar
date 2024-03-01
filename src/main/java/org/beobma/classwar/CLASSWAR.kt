@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.GameManager.Companion.reset
import org.beobma.classwar.classlist.*
import org.beobma.classwar.util.Particle
import org.beobma.classwar.util.Skill
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class CLASSWAR : JavaPlugin(), Listener {

    companion object {
        lateinit var instance: CLASSWAR
    }


    override fun onEnable() {
        server.getPluginCommand("classwar")?.setExecutor(GameManager())
        server.pluginManager.registerEvents(GameManager(), this)
        server.pluginManager.registerEvents(Skill(), this)
        server.pluginManager.registerEvents(Particle(), this)
        server.pluginManager.registerEvents(SkillUse(), this)

        server.pluginManager.registerEvents(BERSERKER(), this)
        server.pluginManager.registerEvents(ARCHER(), this)
        server.pluginManager.registerEvents(FIREWIZARD(), this)
        server.pluginManager.registerEvents(WATERWIZARD(), this)
        server.pluginManager.registerEvents(TIMEMANIPULATOR(), this)
        server.pluginManager.registerEvents(LANDWIZARD(), this)
        server.pluginManager.registerEvents(WINDWIZARD(), this)
        server.pluginManager.registerEvents(GRAVITATIONALMANIPULATOR(), this)
        server.pluginManager.registerEvents(GAMBLER(), this)
        server.pluginManager.registerEvents(KNIGHT(), this)
        server.pluginManager.registerEvents(SPACEOPERATOR(), this)
        server.pluginManager.registerEvents(LIGHTNINGWIZARD(), this)
        server.pluginManager.registerEvents(LIGHTWIZARD(), this)
        server.pluginManager.registerEvents(DARKWIZARD(), this)
        server.pluginManager.registerEvents(PRIESTS(), this)
        server.pluginManager.registerEvents(WARLOCK(), this)
        server.pluginManager.registerEvents(MATHEMATICIAN(), this)

        instance = this
        logger.info("클래스 대전 시스템 활성화")
    }

    override fun onDisable() {
        logger.info("클래스 대전 시스템 비활성화")
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (GameManager.onlinePlayers.contains(player)) {
            player.scoreboard.teams.clear()
            player.closeInventory()
            player.setPlayerListName(player.name)
            player.scoreboardTags.clear()
            player.gameMode = GameMode.ADVENTURE
            Bukkit.getOnlinePlayers().forEach { players ->
                players.closeInventory()
            }
            reset()
            Bukkit.broadcastMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 게임 진행 도중 게임을 종료한 플레이어가 존재하여 게임이 중지되었습니다.")

        }
    }
}
