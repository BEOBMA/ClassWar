@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.LOCALIZATION.Companion.archer_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_name
import org.beobma.classwar.LOCALIZATION.Companion.berserker_lore
import org.beobma.classwar.LOCALIZATION.Companion.berserker_name
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_lore
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_name
import org.beobma.classwar.LOCALIZATION.Companion.map_list
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.scoreboard.Team
import java.util.*

class GAMEMANAGER : Listener, CommandExecutor, TabCompleter {

    companion object {
        var onlinePlayers: MutableCollection<out Player> = Bukkit.getOnlinePlayers()
        private var shuffledPlayers = onlinePlayers.shuffled()
        private var currentIndex = 0
        private var isGaming = false
        private var menu: Inventory = Bukkit.createInventory(null, 27, "${ChatColor.BOLD}클래스 선택")

        private val teams = mutableMapOf(
            "RedTeam" to Bukkit.getScoreboardManager().mainScoreboard.getTeam("RedTeam"),
            "BlueTeam" to Bukkit.getScoreboardManager().mainScoreboard.getTeam("BlueTeam"),
            "SpectatorTeam" to Bukkit.getScoreboardManager().mainScoreboard.getTeam("SpectatorTeam")
        )
        private fun createMenuItem(material: Material, displayName: String, lore: List<String>): ItemStack {
            return ItemStack(material).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(displayName)
                    this.lore = lore
                }
            }
        }
        init {
            menu.setItem(0, createMenuItem(Material.IRON_AXE,berserker_name, berserker_lore))
            menu.setItem(1, createMenuItem(Material.BOW,archer_name, archer_lore))
            menu.setItem(2, createMenuItem(Material.FIRE,fire_wizard_name, fire_wizard_lore))
        }
        fun reset() {
            currentIndex = 1
            isGaming = false

            menu.setItem(0, createMenuItem(Material.IRON_AXE,berserker_name, berserker_lore))
            menu.setItem(1, createMenuItem(Material.BOW,archer_name, archer_lore))
            menu.setItem(2, createMenuItem(Material.FIRE,fire_wizard_name, fire_wizard_lore))
        }
    }
    private fun gamePick(player: Player) {
        onlinePlayers = Bukkit.getOnlinePlayers()
        shuffledPlayers = onlinePlayers.shuffled()
        isGaming = true
        Bukkit.broadcastMessage("${ChatColor.GREEN}${ChatColor.BOLD}[!] ${player.name}님이 게임을 시작했습니다.")

        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
            if (onlinePlayers.size <= 1) {
                Bukkit.broadcastMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 참가자가 1명 이하이므로 게임을 시작할 수 없습니다.")
                gameEnd()
                return@Runnable
            }

            Bukkit.broadcastMessage("${ChatColor.YELLOW}- 참가자 목록 -")
            onlinePlayers.forEachIndexed { index, onlinePlayer ->
                Bukkit.broadcastMessage("${ChatColor.YELLOW}${index + 1}. ${onlinePlayer.name}")
            }

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                Bukkit.broadcastMessage("${ChatColor.YELLOW}- 클래스 선택 순서 -")
                shuffledPlayers.forEach { Bukkit.broadcastMessage(it.name) }
                openMenuInventory(shuffledPlayers.firstOrNull()!!)
            }, 30L)

        }, 60L)
    }
    private fun teamPick(player: Player) {
        onlinePlayers.forEach { it.teleport(Location(Bukkit.getWorld("world"), 1.0, -60.0, -16.0, 0F, 0F)) }

        Bukkit.broadcastMessage("\n${ChatColor.YELLOW}20초 후, 플레이어가 위치한 양털의 색에 따라 팀이 등록됩니다.")
        Bukkit.broadcastMessage("${ChatColor.YELLOW}흰색의 경우 관전자입니다.")

        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
            val teamMapping = mapOf(
                Location(Bukkit.getWorld("world"), 6.0, -55.0, -8.0) to teams["RedTeam"],
                Location(Bukkit.getWorld("world"), -4.0, -55.0, -8.0) to teams["BlueTeam"],
                Location(Bukkit.getWorld("world"), 7.0, -54.0, -19.0) to teams["SpectatorTeam"]
            )

            onlinePlayers.forEach { explayer ->
                val matchingTeam = teamMapping.entries.firstOrNull { isInsideRegion(explayer.location, it.key, it.key.subtract(4.0, 6.0, 4.0)) }
                matchingTeam?.value?.addEntry(explayer.name)
            }

            Bukkit.broadcastMessage("\n${ChatColor.YELLOW}팀 등록이 완료되었습니다.")
            broadcastTeamPlayers(teams["RedTeam"], "레드")
            broadcastTeamPlayers(teams["BlueTeam"], "블루")
            broadcastTeamPlayers(teams["SpectatorTeam"], "관전자")

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                Bukkit.broadcastMessage("\n${ChatColor.YELLOW}잠시 후 맵이 랜덤으로 지정됩니다.")
                CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                    mapPick(player)
                }, 30L)
            }, 30L)
        }, 400L)
    }
    private fun broadcastTeamPlayers(team: Team?, teamName: String) {
        val playerNames = team?.entries?.joinToString(", ") ?: "없음"
        Bukkit.broadcastMessage("\n${ChatColor.WHITE}$teamName 팀: ${ChatColor.RESET}$playerNames")
    }
    private fun mapPick(player: Player) {
        val randomMap = map_list.random()
        val spectatorLocation = Location(Bukkit.getWorld("world"), 26.0, -57.0, -28.0, 135F, 0F)

        onlinePlayers.forEach { it.teleport(spectatorLocation) }

        var countdown = 25
        val delay = 4L

        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
            if (countdown > 0) {
                player.scoreboard.getObjective("map_list")?.getScore(player.name)?.score = randomMap["id"] as? Int ?: 0
                countdown--
            } else {
                CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
                Bukkit.broadcastMessage("${ChatColor.YELLOW}선택된 전장은 ${randomMap["value"]}입니다.")
                startGameCountdown(player)
            }
        }, 0L, delay)

        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
            onlinePlayers.forEach { it.teleport(spectatorLocation) }
        }, 0L, 1)
    }
    private fun startGameCountdown(player: Player) {
        var seconds = 5 // 초기 게임 시작 카운트다운 값

        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
            if (seconds > 0) {
                Bukkit.getServer().broadcastMessage("${ChatColor.YELLOW}${seconds}초 후 게임이 시작됩니다.")
                seconds--
                startGameCountdown(player)
            } else {
                gameStart()
            }
        }, 20L)
    }
    private fun gameStart(){
        for (player in onlinePlayers) {
            player.gameMode = GameMode.ADVENTURE
            player.sendTitle("${ChatColor.BOLD}${ChatColor.RED}Fight!", "${ChatColor.BOLD}무승부까지 3분", 20, 60, 20)
        }
    }
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (cmd.name.equals("classwar", ignoreCase = true) && args.isNotEmpty()) {
            when (args[0].lowercase(Locale.getDefault())) {
                "start" -> {
                    if (sender is Player && sender.isOp) {
                        if (!isGaming) {
                            gamePick(sender)
                        } else {
                            sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 이미 게임이 진행중입니다.")
                        }
                    } else {
                        sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 권한이 존재하지 않습니다.")
                    }
                    return true
                }
                "stop" -> {
                    if (sender.isOp) {
                        if (isGaming) {
                            Bukkit.broadcastMessage("${ChatColor.YELLOW}${ChatColor.BOLD}[!] ${sender.name}님이 게임을 종료했습니다.")
                            gameEnd()
                        } else {
                            sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 게임 진행중이 아닙니다.")
                        }
                    } else {
                        sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 권한이 존재하지 않습니다.")
                    }
                    return true
                }
                else -> {
                    sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 알 수 없는 명령어입니다.")
                    return true
                }
            }
        }
        return false
    }
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        if (command.name.equals("classwar", ignoreCase = true) && args.size == 1) {
            return listOf("start", "stop").filter { it.startsWith(args.last().lowercase(Locale.getDefault())) }
        }
        return emptyList()
    }
    private fun openMenuInventory(player: Player) {
        player.addScoreboardTag("Open_Menu")
        currentIndex += 1
        Bukkit.getServer().broadcastMessage("${player.name}님이 클래스 선택 중입니다.")
        player.openInventory(menu)
    }
    @EventHandler
    fun onEnhanceButtonClick(event: InventoryClickEvent) {
        val clickedItem = event.currentItem
        val player = event.whoClicked as? Player

        if (player?.scoreboardTags?.contains("Open_Menu") == true) {
            if (clickedItem != null) {
                when (val displayName = clickedItem.itemMeta?.displayName) {
                    berserker_name, archer_name, fire_wizard_name -> {
                        val selectedClassMessage = "${player.name}님이 선택한 클래스는 ${ChatColor.BOLD}$displayName${ChatColor.RESET}입니다."
                        Bukkit.broadcastMessage(selectedClassMessage)

                        player.removeScoreboardTag("Open_Menu")
                        player.addScoreboardTag(displayName)
                        event.currentItem = null
                        player.closeInventory()

                        if (currentIndex < shuffledPlayers.size) {
                            openMenuInventory(shuffledPlayers.elementAtOrNull(currentIndex)!!)
                        } else {
                            currentIndex = 0
                            val allPlayersSelectedMessage = "${ChatColor.YELLOW}모든 플레이어의 클래스 선택이 종료되었습니다."
                            Bukkit.broadcastMessage(allPlayersSelectedMessage)

                            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                                val moveTeamMessage = "${ChatColor.YELLOW}잠시 후 팀 등록 공간으로 이동합니다."
                                Bukkit.broadcastMessage(moveTeamMessage)

                                CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                                    teamPick(player)
                                }, 30L)
                            }, 30L)
                        }
                    }
                }
            }
            event.isCancelled = true
        }
    }
    private fun gameEnd() {
        CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
        reset()
        onlinePlayers.forEach { player ->
            player.closeInventory()
            player.scoreboard.teams.forEach { player.scoreboard.teams.remove(it) }
            val playerLocation = player.location
            if (isInsideRegion(playerLocation, Location(Bukkit.getWorld("world"), 7.0, -54.0, -19.0), Location(Bukkit.getWorld("world"), -5.0, -61.0, -7.0))) {
                player.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
            }
            player.scoreboardTags.clear()
        }
    }
    private fun isInsideRegion(location: Location, minPoint: Location, maxPoint: Location): Boolean {
        return (location.x >= minPoint.x && location.x <= maxPoint.x
                && location.y >= minPoint.y && location.y <= maxPoint.y
                && location.z >= minPoint.z && location.z <= maxPoint.z)
    }
}