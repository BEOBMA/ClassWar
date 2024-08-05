@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.GameManager.Companion.isGaming
import org.beobma.classwar.GameManager.Companion.isStarting
import org.beobma.classwar.GameManager.Companion.reset
import org.beobma.classwar.classlist.*
import org.beobma.classwar.util.Particle
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.isBattlefield
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.SkillEventList
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Marker
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
        server.pluginManager.registerEvents(SkillEventList(), this)

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
        server.pluginManager.registerEvents(PHYSICIST(), this)
        server.pluginManager.registerEvents(PALADIN(), this)
        server.pluginManager.registerEvents(BARD(), this)
        server.pluginManager.registerEvents(JUDGE(), this)
        server.pluginManager.registerEvents(DUELIST(), this)
        server.pluginManager.registerEvents(ASTRONOMER(), this)
        server.pluginManager.registerEvents(ASSASSIN(), this)

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
            if (isStarting) {
                player.scoreboard.teams.clear()
                player.closeInventory()
                player.setPlayerListName(player.name)
                player.scoreboardTags.clear()
                player.gameMode = GameMode.ADVENTURE
                Bukkit.getOnlinePlayers().forEach { players ->
                    players.closeInventory()
                }
                reset()
                Bukkit.broadcastMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 게임 진행 준비 도중 게임을 종료한 플레이어가 존재하여 게임이 중지되었습니다.")
            } else if (isGaming) {
                player.scoreboard.teams.clear()
                player.closeInventory()
                player.setPlayerListName(player.name)
                player.scoreboardTags.clear()
                player.gameMode = GameMode.ADVENTURE

                if (player.isTeam("RedTeam")) {
                    if (GameManager.teams["RedTeam"]?.players?.size == 1) {
                        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                            onlinePlayer.sendTitle(
                                "${ChatColor.BOLD}${ChatColor.DARK_BLUE}블루팀 승리",
                                "${ChatColor.BOLD}상대를 전멸시켜 승리했습니다.",
                                20,
                                40,
                                20
                            )
                        }
                        instance.server.scheduler.cancelTasks(instance)
                        val marker = Bukkit.getWorld("world")?.entities?.filterIsInstance<Marker>()?.toList()
                        val armorStands = Bukkit.getWorld("world")?.entities?.filterIsInstance<ArmorStand>()?.toList()
                        marker?.forEach { it.remove() }
                        armorStands?.forEach { it.remove() }
                        for ((_, team) in GameManager.teams) {
                            team?.entries?.forEach { entry ->
                                team.removeEntry(entry)
                            }
                        }

                        Bukkit.getOnlinePlayers().forEach { player1 ->
                            player1.activePotionEffects.forEach { effect ->
                                player1.removePotionEffect(effect.type)
                            }
                            val playerTags = player1.scoreboardTags.toList()
                            for (tag in playerTags) {
                                player1.removeScoreboardTag(tag)
                            }
                            player1.setPlayerListName(player1.name)
                            player1.inventory.clear()
                            player1.health = player1.maxHealth
                            player1.fireTicks = 0

                            if (isInsideRegion(
                                    player1.location,
                                    Location(Bukkit.getWorld("world"), -5.0, -61.0, -19.0),
                                    Location(Bukkit.getWorld("world"), 7.0, -54.0, -8.0)
                                ) || isInsideRegion(
                                    player1.location,
                                    Location(Bukkit.getWorld("world"), 31.0, -64.0, -58.0),
                                    Location(Bukkit.getWorld("world"), 63.0, -44.0, -26.0)
                                )
                            ) {
                                player1.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
                            }

                            if (player1.isBattlefield()) {
                                player1.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
                            }
                            player1.gameMode = GameMode.ADVENTURE
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard players reset @a")
                        reset()
                    } else {
                        GameManager.teams["RedTeam"]?.removePlayer(player)
                        val playerTags = player.scoreboardTags.toList()
                        for (tag in playerTags) {
                            player.removeScoreboardTag(tag)
                        }
                        player.setPlayerListName(player.name)
                        player.inventory.clear()
                        player.health = player.maxHealth
                        player.fireTicks = 0
                        player.gameMode = GameMode.ADVENTURE
                    }
                } else if (player.isTeam("BlueTeam")) {
                    if (GameManager.teams["BlueTeam"]?.players?.size == 1) {
                        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                            onlinePlayer.sendTitle(
                                "${ChatColor.BOLD}${ChatColor.DARK_RED}레드팀 승리",
                                "${ChatColor.BOLD}상대를 전멸시켜 승리했습니다.",
                                20,
                                40,
                                20
                            )
                        }
                        instance.server.scheduler.cancelTasks(instance)
                        val marker = Bukkit.getWorld("world")?.entities?.filterIsInstance<Marker>()?.toList()
                        val armorStands = Bukkit.getWorld("world")?.entities?.filterIsInstance<ArmorStand>()?.toList()
                        marker?.forEach { it.remove() }
                        armorStands?.forEach { it.remove() }
                        for ((_, team) in GameManager.teams) {
                            team?.entries?.forEach { entry ->
                                team.removeEntry(entry)
                            }
                        }

                        Bukkit.getOnlinePlayers().forEach { player1 ->
                            player1.activePotionEffects.forEach { effect ->
                                player1.removePotionEffect(effect.type)
                            }
                            val playerTags = player1.scoreboardTags.toList()
                            for (tag in playerTags) {
                                player1.removeScoreboardTag(tag)
                            }
                            player1.setPlayerListName(player1.name)
                            player1.inventory.clear()
                            player1.health = player1.maxHealth
                            player1.fireTicks = 0

                            if (isInsideRegion(
                                    player1.location,
                                    Location(Bukkit.getWorld("world"), -5.0, -61.0, -19.0),
                                    Location(Bukkit.getWorld("world"), 7.0, -54.0, -8.0)
                                ) || isInsideRegion(
                                    player1.location,
                                    Location(Bukkit.getWorld("world"), 31.0, -64.0, -58.0),
                                    Location(Bukkit.getWorld("world"), 63.0, -44.0, -26.0)
                                )
                            ) {
                                player1.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
                            }

                            if (player1.isBattlefield()) {
                                player1.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
                            }
                            player1.gameMode = GameMode.ADVENTURE
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard players reset @a")
                        reset()
                    } else {
                        GameManager.teams["BlueTeam"]?.removePlayer(player)
                        val playerTags = player.scoreboardTags.toList()
                        for (tag in playerTags) {
                            player.removeScoreboardTag(tag)
                        }
                        player.setPlayerListName(player.name)
                        player.inventory.clear()
                        player.health = player.maxHealth
                        player.fireTicks = 0
                        player.gameMode = GameMode.SPECTATOR
                        player.teleport(Location(Bukkit.getWorld("world"), 12.0, -50.0, -42.0, 0F, 90F))
                    }
                }
            }
        }
    }

    private fun isInsideRegion(location: Location, minPoint: Location, maxPoint: Location): Boolean {
        return (location.x >= minPoint.x && location.x <= maxPoint.x && location.y >= minPoint.y && location.y <= maxPoint.y && location.z >= minPoint.z && location.z <= maxPoint.z)
    }
}
