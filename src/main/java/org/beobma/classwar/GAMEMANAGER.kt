@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.LOCALIZATION.Companion.archer_arrow_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_arrow_name
import org.beobma.classwar.LOCALIZATION.Companion.archer_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_name
import org.beobma.classwar.LOCALIZATION.Companion.archer_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.archer_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.archer_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.archer_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.archer_weapon_name
import org.beobma.classwar.LOCALIZATION.Companion.berserker_lore
import org.beobma.classwar.LOCALIZATION.Companion.berserker_name
import org.beobma.classwar.LOCALIZATION.Companion.berserker_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.berserker_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.berserker_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.berserker_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.berserker_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.berserker_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.berserker_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.berserker_weapon_name
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_lore
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_name
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.fire_wizard_weapon_name
import org.beobma.classwar.LOCALIZATION.Companion.map_list
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_lore
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_name
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.water_wizard_weapon_name
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_lore
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_name
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_skill3_lore
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_skill3_name
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.time_manipulator_weapon_name
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_lore
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_name
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_skill3_lore
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_skill3_name
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.land_wizard_weapon_name
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_lore
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_name
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_passiveskill_lore
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_passiveskill_name
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_skill1_lore
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_skill1_name
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_skill2_lore
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_skill2_name
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_skill3_lore
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_skill3_name
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_weapon_lore
import org.beobma.classwar.LOCALIZATION.Companion.wind_wizard_weapon_name
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.scoreboard.Team
import java.util.*


class GAMEMANAGER : Listener, CommandExecutor, TabCompleter {

    companion object {
        var onlinePlayers: MutableCollection<out Player> = Bukkit.getOnlinePlayers()
        var randomMap = map_list.random()
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
            menu.setItem(2, createMenuItem(Material.BLAZE_POWDER,fire_wizard_name, fire_wizard_lore))
            menu.setItem(3, createMenuItem(Material.WATER_BUCKET,water_wizard_name, water_wizard_lore))
            menu.setItem(4, createMenuItem(Material.CLOCK,time_manipulator_name, time_manipulator_lore))
            menu.setItem(5, createMenuItem(Material.SANDSTONE,land_wizard_name, land_wizard_lore))
            menu.setItem(6, createMenuItem(Material.WHITE_BANNER,wind_wizard_name, wind_wizard_lore))
        }
        fun reset() {
            currentIndex = 0
            isGaming = false

            menu.setItem(0, createMenuItem(Material.IRON_AXE,berserker_name, berserker_lore))
            menu.setItem(1, createMenuItem(Material.BOW,archer_name, archer_lore))
            menu.setItem(2, createMenuItem(Material.BLAZE_POWDER,fire_wizard_name, fire_wizard_lore))
            menu.setItem(3, createMenuItem(Material.WATER_BUCKET,water_wizard_name, water_wizard_lore))
            menu.setItem(4, createMenuItem(Material.CLOCK,time_manipulator_name, time_manipulator_lore))
            menu.setItem(5, createMenuItem(Material.SANDSTONE,land_wizard_name, land_wizard_lore))
            menu.setItem(6, createMenuItem(Material.WHITE_BANNER,wind_wizard_name, wind_wizard_lore))
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
    private fun teamPick(player: Player){
        for (explayer in onlinePlayers) {
            explayer.teleport(Location(Bukkit.getWorld("world"), 1.0, -60.0, -16.0,0F, 0F))
            explayer.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 2.0F)
        }

        Bukkit.getServer().broadcastMessage("\n${ChatColor.YELLOW}10초 후, 플레이어가 위치한 양털의 색에 따라 팀이 등록됩니다.")
        Bukkit.getServer().broadcastMessage("${ChatColor.YELLOW}흰색의 경우 관전자입니다.")
        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {

            for (explayer in onlinePlayers) {
                if (isInsideRegion(explayer.location, Location(Bukkit.getWorld("world"),
                        2.0, -61.0, -12.0), Location(Bukkit.getWorld("world"),
                        7.0, -55.0, -7.0))) {
                    //레드팀
                    teams["RedTeam"]?.addEntry(explayer.name)
                    val nametag = explayer.playerListName
                    explayer.setPlayerListName("${ChatColor.DARK_RED}$nametag")


                }
                else if (isInsideRegion(explayer.location, Location(Bukkit.getWorld("world"),
                        -4.0, -61.0, -12.0), Location(Bukkit.getWorld("world"),
                        1.0, -55.0, -7.0))){
                    //블루팀
                    teams["BlueTeam"]?.addEntry(explayer.name)
                    val nametag = explayer.playerListName
                    explayer.setPlayerListName("${ChatColor.DARK_BLUE}$nametag")

                }
                else if(isInsideRegion(explayer.location, Location(Bukkit.getWorld("world"),
                        -5.0, -61.0, -19.0), Location(Bukkit.getWorld("world"),
                        7.0, -54.0, -8.0))){
                    //관전자팀
                    teams["SpectatorTeam"]?.addEntry(explayer.name)

                }
            }
            Bukkit.getServer().broadcastMessage("\n${ChatColor.YELLOW}팀 등록이 완료되었습니다.")
            broadcastTeamPlayers(teams["RedTeam"], "레드")
            broadcastTeamPlayers(teams["BlueTeam"], "블루")
            broadcastTeamPlayers(teams["SpectatorTeam"], "관전자")

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                Bukkit.getServer().broadcastMessage("\n${ChatColor.YELLOW}잠시 후 맵이 랜덤으로 지정됩니다.")
                CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                    mapPick(player)
                }, 30L)
            }, 30L)
        }, 200L)
    }
    private fun broadcastTeamPlayers(team: Team?, teamName: String) {
        val playerNames = team?.entries?.joinToString(", ") ?: "없음"
        Bukkit.broadcastMessage("\n${ChatColor.WHITE}$teamName 팀: ${ChatColor.RESET}$playerNames")
    }
    private fun mapPick(player: Player) {
        onlinePlayers.forEach {
            it.teleport(Location(Bukkit.getWorld("world"), 26.0, -57.0, -28.0, 135F, 0F))
            it.gameMode = GameMode.SPECTATOR
            it.addScoreboardTag("game_player")
            it.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1.0F, 2.0F)
        }
        var countdown = 25
        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
            if (countdown > 0) {
                randomMap = map_list.random()
                player.scoreboard.getObjective("map_list")?.getScore(player.name)?.score = randomMap["id"] as? Int ?: 0
                countdown--
            } else {
                CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
                Bukkit.broadcastMessage("${ChatColor.YELLOW}선택된 전장은 ${randomMap["value"]}${ChatColor.RESET}입니다.")
                var seconds = 5 // 초기 게임 시작 카운트다운 값

                CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
                    if (seconds > 0) {
                        Bukkit.getServer().broadcastMessage("${ChatColor.YELLOW}${seconds}초 후 게임이 시작됩니다.")
                        onlinePlayers.forEach {
                            it.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F)
                        }
                        seconds--
                    } else {
                        gameStart()
                    }
                }, 20L, 20L)
            }
        }, 2L, 4L)

        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
            onlinePlayers.forEach { it.teleport(Location(Bukkit.getWorld("world"), 26.0, -57.0, -28.0, 135F, 0F)) }
        }, 0L, 1)
    }
    private fun gameStart(){


        onlinePlayers.forEach { player ->
            if (player.scoreboardTags.contains("game_player")) {
                when {
                    teams["RedTeam"]?.hasEntry(player.name) == true -> {
                        player.gameMode = GameMode.ADVENTURE
                        when {
                            randomMap["id"] == 1 || randomMap["id"] == 2 || randomMap["id"] == 3 || randomMap["id"] == 4 || randomMap["id"] == 5 || randomMap["id"] == 8 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), -2.0, -60.0, -28.0, -135F, 0F))
                            }
                            randomMap["id"] == 6 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), -2.0, -54.0, -28.0, -135F, 0F))
                            }
                            randomMap["id"] == 7 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), -2.0, -59.0, -28.0, -135F, 0F))
                            }
                            randomMap["id"] == 9 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 10.0, -55.0, -32.0, 180F, 0F))
                            }
                            randomMap["id"] == 10 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 2.0, -55.0, -32.0, -135F, 0F))
                            }
                        }

                    }
                    teams["BlueTeam"]?.hasEntry(player.name) == true -> {
                        player.gameMode = GameMode.ADVENTURE

                        when {
                            randomMap["id"] == 1 || randomMap["id"] == 2 || randomMap["id"] == 3 || randomMap["id"] == 4 || randomMap["id"] == 5 || randomMap["id"] == 8 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 26.0, -60.0, -56.0, 45F, 0F))
                            }
                            randomMap["id"] == 6 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 26.0, -54.0, -56.0, 45F, 0F))
                            }
                            randomMap["id"] == 7 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 26.0, -59.0, -56.0, 45F, 0F))
                            }
                            randomMap["id"] == 9 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 13.0, -55.0, -52.0, 0F, 0F))
                            }
                            randomMap["id"] == 10 -> {
                                player.teleport(Location(Bukkit.getWorld("world"), 22.0, -56.0, -52.0, 45F, 0F))
                            }
                        }

                    }
                    teams["SpectatorTeam"]?.hasEntry(player.name) == true -> {
                        player.teleport(Location(Bukkit.getWorld("world"), 12.0, -50.0, -42.0, 0F, 90F))

                    }
                }
                player.health = player.maxHealth
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F)
                player.sendTitle("${ChatColor.BOLD}${ChatColor.RED}Fight!", "${ChatColor.BOLD}무승부까지 3분", 20, 30, 20)
                player.removeScoreboardTag("game_player")
            }
            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player.sendTitle("${ChatColor.BOLD}무승부", "${ChatColor.BOLD}제한 시간 초과", 20, 40, 20)
                gameEnd()
            }, 3600L)


            when {
                player.scoreboardTags.contains(berserker_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.IRON_AXE,berserker_weapon_name, berserker_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,berserker_skill1_name, berserker_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.RED_DYE,berserker_skill2_name, berserker_skill2_lore))
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,berserker_passiveskill_name, berserker_passiveskill_lore))
                }
                player.scoreboardTags.contains(archer_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.BOW,archer_weapon_name, archer_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,archer_skill1_name, archer_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.RED_DYE,archer_skill2_name, archer_skill2_lore))
                    player.inventory.setItem(3,createMenuItem(Material.ARROW,archer_arrow_name,archer_arrow_lore).apply { amount = 30 })
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,archer_passiveskill_name, archer_passiveskill_lore))
                }
                player.scoreboardTags.contains(fire_wizard_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.WOODEN_SWORD,fire_wizard_weapon_name, fire_wizard_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,fire_wizard_skill1_name, fire_wizard_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.RED_DYE,fire_wizard_skill2_name, fire_wizard_skill2_lore))
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,fire_wizard_passiveskill_name, fire_wizard_passiveskill_lore))
                }
                player.scoreboardTags.contains(water_wizard_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.WOODEN_SWORD,water_wizard_weapon_name, water_wizard_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,water_wizard_skill1_name, water_wizard_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.RED_DYE,water_wizard_skill2_name, water_wizard_skill2_lore))
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,water_wizard_passiveskill_name, water_wizard_passiveskill_lore))
                }
                player.scoreboardTags.contains(time_manipulator_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.WOODEN_SWORD,time_manipulator_weapon_name, time_manipulator_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,time_manipulator_skill1_name, time_manipulator_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.ORANGE_DYE,time_manipulator_skill2_name, time_manipulator_skill2_lore))
                    player.inventory.setItem(3,createMenuItem(Material.RED_DYE,time_manipulator_skill3_name, time_manipulator_skill3_lore))
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,time_manipulator_passiveskill_name, time_manipulator_passiveskill_lore))
                }
                player.scoreboardTags.contains(land_wizard_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.WOODEN_SWORD,land_wizard_weapon_name, land_wizard_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,land_wizard_skill1_name, land_wizard_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.ORANGE_DYE,land_wizard_skill2_name, land_wizard_skill2_lore))
                    player.inventory.setItem(3,createMenuItem(Material.RED_DYE,land_wizard_skill3_name, land_wizard_skill3_lore))
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,land_wizard_passiveskill_name, land_wizard_passiveskill_lore))
                }
                player.scoreboardTags.contains(wind_wizard_name) -> {
                    player.inventory.setItem(0,createMenuItem(Material.WOODEN_SWORD,wind_wizard_weapon_name, wind_wizard_weapon_lore))
                    player.inventory.setItem(1,createMenuItem(Material.BLACK_DYE,wind_wizard_skill1_name, wind_wizard_skill1_lore))
                    player.inventory.setItem(2,createMenuItem(Material.ORANGE_DYE,wind_wizard_skill2_name, wind_wizard_skill2_lore))
                    player.inventory.setItem(3,createMenuItem(Material.RED_DYE,wind_wizard_skill3_name, wind_wizard_skill3_lore))
                    player.inventory.setItem(8,createMenuItem(Material.WHITE_DYE,wind_wizard_passiveskill_name, wind_wizard_passiveskill_lore))
                }
            }
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
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0F, 1.0F)
        player.openInventory(menu)
    }
    @EventHandler
    fun onEnhanceButtonClick(event: InventoryClickEvent) {
        val clickedItem = event.currentItem
        val player = event.whoClicked as? Player
        val inventory = event.inventory

        if (player?.scoreboardTags?.contains("Open_Menu") == true) {
            if (clickedItem != null) {
                when (val displayName = clickedItem.itemMeta?.displayName) {
                    berserker_name,
                    archer_name,
                    fire_wizard_name,
                    water_wizard_name,
                    time_manipulator_name,
                    land_wizard_name,
                    wind_wizard_name -> {
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 2.0F)
                        val selectedClassMessage = "${player.name}님이 선택한 클래스는 ${ChatColor.BOLD}$displayName${ChatColor.RESET}입니다."
                        player.setPlayerListName(player.name + " ${ChatColor.WHITE}${ChatColor.BOLD}[ $displayName ]")
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

                    else -> {player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 0.5F)}
                }
            }
            event.isCancelled = true
        }
        else if (inventory.type == InventoryType.BARREL) {
            event.isCancelled = true
        }
    }
    private fun gameEnd() {
        CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
        onlinePlayers.forEach { player ->

            player.scoreboardTags.forEach { tag ->
                player.removeScoreboardTag(tag)
            }
            player.setPlayerListName(player.name)
            player.closeInventory()
            player.inventory.clear()
            player.health = player.maxHealth

            for ((_, team) in teams) {
                team?.entries?.forEach { entry ->
                    team.removeEntry(entry)
                }
            }

            if (isInsideRegion(player.location, Location(Bukkit.getWorld("world"), -5.0, -61.0, -19.0), Location(Bukkit.getWorld("world"), 7.0, -54.0, -8.0))) {
                player.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
            }

            if (isInsideRegion(player.location, Location(Bukkit.getWorld("world"), -6.0, -64.0, -60.0), Location(Bukkit.getWorld("world"), 30.0, -44.0, -24.0))) {
                player.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
            }
            player.gameMode = GameMode.ADVENTURE
        }

        reset()
    }
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player

        if (player.scoreboardTags.contains("Open_Menu")){
            Bukkit.broadcastMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${player.name}님이 클래스 선택을 거부하여 게임이 종료됩니다.")
            gameEnd()
            return

        }
    }
    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager

        if (isGaming) {
            if (!isInsideRegion(damager.location, Location(Bukkit.getWorld("world"), -6.0, -64.0, -60.0), Location(Bukkit.getWorld("world"), 30.0, -44.0, -24.0))) {
                event.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        if (isGaming){
            event.isCancelled = true
        }
    }
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity // 사망한 플레이어

        if (isGaming){
            for ((_, team) in teams) {
                team?.removeEntry(player.name)
            }

            val emptyTeams = mutableListOf<String>()
            for ((teamName, team) in teams) {
                if (teamName != "SpectatorTeam" && team?.entries?.isEmpty() == true) {
                    emptyTeams.add(teamName)
                }
            }

            onlinePlayers.forEach { explayer ->
                if (teams["RedTeam"]?.players?.isEmpty() == true) {
                    explayer.sendTitle(
                        "${ChatColor.BOLD}${ChatColor.DARK_BLUE}블루팀 승리",
                        "${ChatColor.BOLD}상대를 전멸시켜 승리했습니다.",
                        20,
                        40,
                        20
                    )
                    gameEnd()
                } else {
                    explayer.sendTitle(
                        "${ChatColor.BOLD}${ChatColor.DARK_RED}레드팀 승리",
                        "${ChatColor.BOLD}상대를 전멸시켜 승리했습니다.",
                        20,
                        40,
                        20
                    )
                    gameEnd()
                }
            }
        }


    }
    private fun isInsideRegion(location: Location, minPoint: Location, maxPoint: Location): Boolean {
        return (location.x >= minPoint.x && location.x <= maxPoint.x
                && location.y >= minPoint.y && location.y <= maxPoint.y
                && location.z >= minPoint.z && location.z <= maxPoint.z)
    }
}