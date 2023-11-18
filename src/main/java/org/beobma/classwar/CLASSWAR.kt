@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.GAMEMANAGER.Companion.reset
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class CLASSWAR : JavaPlugin(), Listener {


    companion object {
        lateinit var instance: CLASSWAR
    }


    override fun onEnable() {
        server.getPluginCommand("classwar")?.setExecutor(GAMEMANAGER())
        server.pluginManager.registerEvents(GAMEMANAGER(), this)
        instance = this
        logger.info("클래스 대전 시스템 활성화")
    }

    override fun onDisable() {
        logger.info("클래스 대전 시스템 비활성화")
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (GAMEMANAGER.onlinePlayers.contains(player)) {
            player.scoreboard.teams.clear()
            player.closeInventory()
            player.scoreboardTags.clear()
            reset()
            val quitMessage = "${ChatColor.RED}${ChatColor.BOLD}[!] 게임 진행 도중 게임을 종료한 플레이어가 존재하여 게임이 중지되었습니다."
            Bukkit.broadcastMessage(quitMessage)
        }
    }

}
