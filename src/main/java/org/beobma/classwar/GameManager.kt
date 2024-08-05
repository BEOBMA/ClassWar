@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.cardDraw
import org.beobma.classwar.util.Skill.Companion.isBattlefield
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.isTraining
import org.beobma.classwar.util.Skill.Companion.speedIncrease
import org.bukkit.*
import org.bukkit.block.Barrel
import org.bukkit.block.Block
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Marker
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffect.INFINITE_DURATION
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.Team
import java.util.*

class GameManager : Listener, CommandExecutor, TabCompleter {

    companion object {
        var onlinePlayers: MutableCollection<out Player> = Bukkit.getOnlinePlayers()
        var gamingPlayer: MutableCollection<out Player>? = Bukkit.getOnlinePlayers()
        var randomMap = LOCALIZATION.map_list.random()
        private var shuffledPlayers = onlinePlayers.shuffled()
        private var currentIndex = 0
        var isStarting = false
        var isGaming = false
        private var menu: Inventory = Bukkit.createInventory(null, 27, "${ChatColor.BOLD}클래스 선택")

        var teams = mutableMapOf(
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
            menu.setItem(0, createMenuItem(Material.IRON_AXE, LOCALIZATION.berserker.name, LOCALIZATION.berserker.description))
            menu.setItem(1, createMenuItem(Material.BOW, LOCALIZATION.archer.name, LOCALIZATION.archer.description))
            menu.setItem(2, createMenuItem(Material.BLAZE_POWDER, LOCALIZATION.firewizard.name, LOCALIZATION.firewizard.description))
            menu.setItem(3, createMenuItem(Material.WATER_BUCKET, LOCALIZATION.waterwizard.name, LOCALIZATION.waterwizard.description))
            menu.setItem(4, createMenuItem(Material.CLOCK, LOCALIZATION.timemanipulator.name, LOCALIZATION.timemanipulator.description))
            menu.setItem(5, createMenuItem(Material.SANDSTONE, LOCALIZATION.landwizard.name, LOCALIZATION.landwizard.description))
            menu.setItem(6, createMenuItem(Material.WHITE_BANNER, LOCALIZATION.windwizard.name, LOCALIZATION.windwizard.description))
            menu.setItem(
                7, createMenuItem(Material.APPLE, LOCALIZATION.gravitationalmanipulator.name, LOCALIZATION.gravitationalmanipulator.description)
            )
            menu.setItem(8, createMenuItem(Material.PAPER, LOCALIZATION.gambler.name, LOCALIZATION.gambler.description))
            menu.setItem(9, createMenuItem(Material.IRON_SWORD, LOCALIZATION.knight.name, LOCALIZATION.knight.description))
            menu.setItem(10, createMenuItem(Material.TWISTING_VINES, LOCALIZATION.spaceoperator.name, LOCALIZATION.spaceoperator.description))
            menu.setItem(11, createMenuItem(Material.LIGHTNING_ROD, LOCALIZATION.lightningwizard.name, LOCALIZATION.lightningwizard.description))
            menu.setItem(12, createMenuItem(Material.LIGHT, LOCALIZATION.lightwizard.name, LOCALIZATION.lightwizard.description))
            menu.setItem(13, createMenuItem(Material.BLACK_CONCRETE, LOCALIZATION.darkwizard.name, LOCALIZATION.darkwizard.description))
            menu.setItem(14, createMenuItem(Material.WOODEN_SHOVEL, LOCALIZATION.priests.name, LOCALIZATION.priests.description))
            menu.setItem(15, createMenuItem(Material.FERMENTED_SPIDER_EYE, LOCALIZATION.warlock.name, LOCALIZATION.warlock.description))
            menu.setItem(16, createMenuItem(Material.STONE, LOCALIZATION.mathematician.name, LOCALIZATION.mathematician.description))
            menu.setItem(17, createMenuItem(Material.WOODEN_SWORD, LOCALIZATION.physicist.name, LOCALIZATION.physicist.description))
            menu.setItem(18, createMenuItem(Material.BELL, LOCALIZATION.paladin.name, LOCALIZATION.paladin.description))
            menu.setItem(19, createMenuItem(Material.GOAT_HORN, LOCALIZATION.bard.name, LOCALIZATION.bard.description))
            menu.setItem(20, createMenuItem(Material.ELYTRA, LOCALIZATION.judge.name, LOCALIZATION.judge.description))
            menu.setItem(21, createMenuItem(Material.SPECTRAL_ARROW, LOCALIZATION.duelist.name, LOCALIZATION.duelist.description))
            menu.setItem(22, createMenuItem(Material.SPYGLASS, LOCALIZATION.astronomer.name, LOCALIZATION.astronomer.description))
            menu.setItem(23, createMenuItem(Material.IRON_NUGGET, LOCALIZATION.assassin.name, LOCALIZATION.assassin.description))
        }

        fun reset() {
            currentIndex = 0
            isStarting = false
            isGaming = false
            Skill.time = 0

            menu.setItem(0, createMenuItem(Material.IRON_AXE, LOCALIZATION.berserker.name, LOCALIZATION.berserker.description))
            menu.setItem(1, createMenuItem(Material.BOW, LOCALIZATION.archer.name, LOCALIZATION.archer.description))
            menu.setItem(2, createMenuItem(Material.BLAZE_POWDER, LOCALIZATION.firewizard.name, LOCALIZATION.firewizard.description))
            menu.setItem(3, createMenuItem(Material.WATER_BUCKET, LOCALIZATION.waterwizard.name, LOCALIZATION.waterwizard.description))
            menu.setItem(4, createMenuItem(Material.CLOCK, LOCALIZATION.timemanipulator.name, LOCALIZATION.timemanipulator.description))
            menu.setItem(5, createMenuItem(Material.SANDSTONE, LOCALIZATION.landwizard.name, LOCALIZATION.landwizard.description))
            menu.setItem(6, createMenuItem(Material.WHITE_BANNER, LOCALIZATION.windwizard.name, LOCALIZATION.windwizard.description))
            menu.setItem(
                7, createMenuItem(Material.APPLE, LOCALIZATION.gravitationalmanipulator.name, LOCALIZATION.gravitationalmanipulator.description)
            )
            menu.setItem(8, createMenuItem(Material.PAPER, LOCALIZATION.gambler.name, LOCALIZATION.gambler.description))
            menu.setItem(9, createMenuItem(Material.IRON_SWORD, LOCALIZATION.knight.name, LOCALIZATION.knight.description))
            menu.setItem(10, createMenuItem(Material.TWISTING_VINES, LOCALIZATION.spaceoperator.name, LOCALIZATION.spaceoperator.description))
            menu.setItem(11, createMenuItem(Material.LIGHTNING_ROD, LOCALIZATION.lightningwizard.name, LOCALIZATION.lightningwizard.description))
            menu.setItem(12, createMenuItem(Material.LIGHT, LOCALIZATION.lightwizard.name, LOCALIZATION.lightwizard.description))
            menu.setItem(13, createMenuItem(Material.BLACK_CONCRETE, LOCALIZATION.darkwizard.name, LOCALIZATION.darkwizard.description))
            menu.setItem(14, createMenuItem(Material.WOODEN_SHOVEL, LOCALIZATION.priests.name, LOCALIZATION.priests.description))
            menu.setItem(15, createMenuItem(Material.FERMENTED_SPIDER_EYE, LOCALIZATION.warlock.name, LOCALIZATION.warlock.description))
            menu.setItem(16, createMenuItem(Material.STONE, LOCALIZATION.mathematician.name, LOCALIZATION.mathematician.description))
            menu.setItem(17, createMenuItem(Material.WOODEN_SWORD, LOCALIZATION.physicist.name, LOCALIZATION.physicist.description))
            menu.setItem(18, createMenuItem(Material.BELL, LOCALIZATION.paladin.name, LOCALIZATION.paladin.description))
            menu.setItem(19, createMenuItem(Material.GOAT_HORN, LOCALIZATION.bard.name, LOCALIZATION.bard.description))
            menu.setItem(20, createMenuItem(Material.ELYTRA, LOCALIZATION.judge.name, LOCALIZATION.judge.description))
            menu.setItem(21, createMenuItem(Material.SPECTRAL_ARROW, LOCALIZATION.duelist.name, LOCALIZATION.duelist.description))
            menu.setItem(22, createMenuItem(Material.SPYGLASS, LOCALIZATION.astronomer.name, LOCALIZATION.astronomer.description))
            menu.setItem(23, createMenuItem(Material.IRON_NUGGET, LOCALIZATION.assassin.name, LOCALIZATION.assassin.description))
        }
    }

    private fun gamePick(player: Player) {
        onlinePlayers = Bukkit.getOnlinePlayers()
        gamingPlayer = Bukkit.getOnlinePlayers()
        shuffledPlayers = onlinePlayers.shuffled()
        isStarting = true

        for (targetPlayer in onlinePlayers) {
            if (targetPlayer.scoreboardTags.contains("training")) {
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
        }
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
        for (explayer in onlinePlayers) {
            explayer.teleport(Location(Bukkit.getWorld("world"), 1.0, -60.0, -16.0, 0F, 0F))
            explayer.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 2.0F)
        }

        Bukkit.getServer().broadcastMessage("\n${ChatColor.YELLOW}10초 후, 플레이어가 위치한 양털의 색에 따라 팀이 등록됩니다.")
        Bukkit.getServer().broadcastMessage("${ChatColor.YELLOW}흰색의 경우 관전자입니다.")
        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {

            for (explayer in onlinePlayers) {
                if (isInsideRegion(
                        explayer.location, Location(
                            Bukkit.getWorld("world"), 2.0, -61.0, -12.0
                        ), Location(
                            Bukkit.getWorld("world"), 7.0, -55.0, -7.0
                        )
                    )
                ) {
                    //레드팀
                    teams["RedTeam"]?.addEntry(explayer.name)
                    val nametag = explayer.playerListName
                    explayer.setPlayerListName("${ChatColor.DARK_RED}$nametag")


                } else if (isInsideRegion(
                        explayer.location, Location(
                            Bukkit.getWorld("world"), -4.0, -61.0, -12.0
                        ), Location(
                            Bukkit.getWorld("world"), 1.0, -55.0, -7.0
                        )
                    )
                ) {
                    //블루팀
                    teams["BlueTeam"]?.addEntry(explayer.name)
                    val nametag = explayer.playerListName
                    explayer.setPlayerListName("${ChatColor.DARK_BLUE}$nametag")

                } else if (isInsideRegion(
                        explayer.location, Location(
                            Bukkit.getWorld("world"), -5.0, -61.0, -19.0
                        ), Location(
                            Bukkit.getWorld("world"), 7.0, -54.0, -8.0
                        )
                    )
                ) {
                    //관전자팀
                    teams["SpectatorTeam"]?.addEntry(explayer.name)

                }
            }

            if (teams["RedTeam"]?.players?.isEmpty() == true) {
                Bukkit.getServer().broadcastMessage("\n${ChatColor.YELLOW}상대 팀이 존재하지 않아 게임을 진행할 수 없습니다.")
                gameEnd()
                return@Runnable
            } else if (teams["BlueTeam"]?.players?.isEmpty() == true) {
                Bukkit.getServer().broadcastMessage("\n${ChatColor.YELLOW}상대 팀이 존재하지 않아 게임을 진행할 수 없습니다.")
                gameEnd()
                return@Runnable
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
        }
        var countdown = 25
        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
            if (countdown > 0) {
                randomMap = LOCALIZATION.map_list.random()
                player.location.world.playSound(player.location, Sound.BLOCK_LEVER_CLICK, 1.0F, 2.0F)
                player.scoreboard.getObjective("map_list")?.getScore(player.name)?.score = randomMap["id"] as? Int ?: 0
                countdown--
            } else {
                CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
                Bukkit.broadcastMessage("${ChatColor.YELLOW}선택된 전장은 ${randomMap["value"]}${ChatColor.RESET}입니다.")
                player.location.world.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F)
                var seconds = 5

                object : BukkitRunnable() {
                    override fun run() {
                        if (seconds > 0) {
                            Bukkit.getServer().broadcastMessage("${ChatColor.YELLOW}${seconds}초 후 게임이 시작됩니다.")
                            onlinePlayers.forEach {
                                it.location.world.playSound(it.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F)
                            }
                            seconds--
                        } else {
                            gameStart()
                            this.cancel()
                        }
                    }
                }.runTaskTimer(CLASSWAR.instance, 20L, 20L)
            }
        }, 2L, 4L)

        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
            onlinePlayers.forEach { it.teleport(Location(Bukkit.getWorld("world"), 26.0, -57.0, -28.0, 135F, 0F)) }
        }, 0L, 1)
    }

    fun gameStart() {


        isGaming = true
        onlinePlayers.forEach { player ->
            if (player.scoreboardTags.contains("game_player")) {
                player.inventory.clear()
                player.activePotionEffects.forEach { effect ->
                    player.removePotionEffect(effect.type)
                }
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
                                player.teleport(Location(Bukkit.getWorld("world"), 2.5, -55.0, -31.5, -135F, 0F))
                            }
                        }
                        player.addScoreboardTag("gameplayer")

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
                                player.teleport(Location(Bukkit.getWorld("world"), 22.5, -56.0, -51.5, 45F, 0F))
                            }
                        }
                        player.addScoreboardTag("gameplayer")

                    }

                    teams["SpectatorTeam"]?.hasEntry(player.name) == true -> {
                        player.teleport(Location(Bukkit.getWorld("world"), 12.0, -50.0, -42.0, 0F, 90F))

                    }
                }
                player.location.world.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F)
                player.sendTitle("${ChatColor.BOLD}${ChatColor.RED}Fight!", "${ChatColor.BOLD}무승부까지 3분", 20, 30, 20)

                when {
                    player.scoreboardTags.contains(LOCALIZATION.berserker.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.berserker.weapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.berserker.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.berserker.skill[1]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.berserker.skill[2]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.archer.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.archer.weapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.archer.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.archer.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.archer.item[0]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.archer.skill[2]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.firewizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.firewizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.firewizard.skill[1]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.firewizard.skill[2]
                        )
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.waterwizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.waterwizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.waterwizard.skill[1]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.waterwizard.skill[2]
                        )
                        player.speedIncrease(INFINITE_DURATION, 20)
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.timemanipulator.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.timemanipulator.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.timemanipulator.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.timemanipulator.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.timemanipulator.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.landwizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.landwizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.landwizard.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.landwizard.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.landwizard.skill[3]
                        )
                        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, INFINITE_DURATION, 1, false, false, true))
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.windwizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.windwizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.windwizard.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.windwizard.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.windwizard.skill[3]
                        )
                        player.addPotionEffect(
                            PotionEffect(
                                PotionEffectType.SLOW_FALLING, INFINITE_DURATION, 0, false, false, true
                            )
                        )
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.gravitationalmanipulator.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.gravitationalmanipulator.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.gravitationalmanipulator.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.gravitationalmanipulator.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.gravitationalmanipulator.skill[3]
                        )
                        player.scoreboardTags.add("gravity_user")
                        Skill.resetGravity(player)
                    }

                    player.scoreboardTags.contains(LOCALIZATION.gambler.name) -> {
                        player.inventory.setItem(
                            1, LOCALIZATION.gambler.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.gambler.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.gambler.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.gambler.skill[3]
                        )
                        Skill.cardDeckReset()
                        Skill.cardDeckShuffle()
                        player.cardDraw(5)
                    }

                    player.scoreboardTags.contains(LOCALIZATION.knight.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.knightweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.knight.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.knight.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.knight.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.knight.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.spaceoperator.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.spaceoperator.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.spaceoperator.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.spaceoperator.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.spaceoperator.skill[3]
                        )
                        player.scoreboardTags.add("charge_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.lightningwizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.lightningwizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.lightningwizard.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.lightningwizard.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.lightningwizard.skill[3]
                        )
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.lightwizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.lightwizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.lightwizard.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.lightwizard.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.lightwizard.skill[3]
                        )
                        player.scoreboardTags.add("mana_user")
                        player.addPotionEffect(
                            PotionEffect(
                                PotionEffectType.NIGHT_VISION, INFINITE_DURATION, 0, false, false, true
                            )
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.darkwizard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.darkwizard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.darkwizard.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.darkwizard.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.darkwizard.skill[3]
                        )
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.priests.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.priestsweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.priests.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.priests.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.priests.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.priests.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.warlock.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.warlockweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.warlock.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.warlock.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.warlock.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.warlock.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.mathematician.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.mathematician.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.mathematician.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.mathematician.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.mathematician.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.physicist.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.physicist.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.physicist.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.physicist.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.physicist.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.paladin.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.paladinweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.paladin.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.paladin.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.paladin.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.paladin.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.bard.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.bardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.bard.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.bard.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.bard.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.bard.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.judge.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.judgeweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.judge.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.judge.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.judge.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.judge.skill[3]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.duelist.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.duelistweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.duelist.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.duelist.skill[3]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.duelist.skill[4]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.duelist.skill[5]
                        )
                    }

                    player.scoreboardTags.contains(LOCALIZATION.astronomer.name) -> {
                        player.inventory.setItem(
                            0, LOCALIZATION.wizardweapon
                        )
                        player.inventory.setItem(
                            1, LOCALIZATION.astronomer.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.astronomer.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.astronomer.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.astronomer.skill[3]
                        )
                        player.scoreboardTags.add("mana_user")
                    }

                    player.scoreboardTags.contains(LOCALIZATION.assassin.name) -> {
                        player.inventory.setItem(
                            1, LOCALIZATION.assassin.skill[0]
                        )
                        player.inventory.setItem(
                            2, LOCALIZATION.assassin.skill[1]
                        )
                        player.inventory.setItem(
                            3, LOCALIZATION.assassin.skill[2]
                        )
                        player.inventory.setItem(
                            8, LOCALIZATION.assassin.skill[3]
                        )
                    }
                }

                player.removeScoreboardTag("game_player")
            }
        }

        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
            onlinePlayers.forEach {
                it.sendTitle("${ChatColor.BOLD}무승부", "${ChatColor.BOLD}제한 시간 초과", 20, 40, 20)
            }
            gameEnd()
        }, 3600L)
        CLASSWAR.instance.server.pluginManager.callEvent(GameStartEvent())
        return
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (cmd.name.equals("classwar", ignoreCase = true) && args.isNotEmpty()) {
            when (args[0].lowercase(Locale.getDefault())) {
                "start" -> {
                    if (sender is Player && sender.isOp) {
                        if (!isStarting) {
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
                        if (isStarting) {
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

                "training" -> {
                    if (isStarting) {
                        sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 게임 진행중에 이용할 수 없습니다.")
                    } else {
                        sender.sendMessage("${ChatColor.YELLOW}${ChatColor.BOLD}[!] 훈련 종료시 /cw exit 명령어를 사용해주세요")
                        CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
                        if (sender is Player) {
                            sender.scoreboardTags.add("training")
                            openMenuInventory(sender)
                        }
                    }

                }

                "exit" -> {
                    if (isStarting) {
                        sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 게임 진행중에 이용할 수 없습니다.")
                    } else {
                        if (sender is Player) {
                            if (sender.scoreboardTags.contains("training")) {
                                sender.sendMessage("${ChatColor.GREEN}${ChatColor.BOLD}[!] 훈련장에서 나갔습니다.")
                                sender.inventory.clear()
                                val playerTags = sender.scoreboardTags.toList()
                                for (tag in playerTags) {
                                    sender.removeScoreboardTag(tag)
                                }
                                sender.activePotionEffects.forEach { effect ->
                                    sender.removePotionEffect(effect.type)
                                }
                                sender.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
                            } else {
                                sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 현재 훈련중이 아닙니다.")
                            }
                        }
                    }
                }

                else -> {
                    sender.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 알 수 없는 명령어입니다.")
                    return true
                }
            }
        }
        return false
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, alias: String, args: Array<String>
    ): List<String> {
        if (command.name.equals("classwar", ignoreCase = true) && args.size == 1) {
            return listOf("start", "stop", "training", "exit").filter {
                it.startsWith(
                    args.last().lowercase(Locale.getDefault())
                )
            }
        }
        return emptyList()
    }

    private fun openMenuInventory(player: Player) {
        if (player.scoreboardTags.contains("training")) {
            player.addScoreboardTag("Open_Menu")
            player.openInventory(menu)
        } else {
            player.addScoreboardTag("Open_Menu")
            currentIndex += 1
            Bukkit.getServer().broadcastMessage("${player.name}님이 클래스 선택 중입니다.")
            player.openInventory(menu)
        }
    }

    @EventHandler
    fun onEnhanceButtonClick(event: InventoryClickEvent) {
        val clickedItem = event.currentItem
        val player = event.whoClicked as? Player
        val inventory = event.inventory

        if (player?.scoreboardTags?.contains("Open_Menu") == true) {
            if (clickedItem != null) {
                if (LOCALIZATION.classList.contains(clickedItem.itemMeta.displayName)) {
                    val displayName = clickedItem.itemMeta.displayName
                    if (player.scoreboardTags.contains("training")) {
                        player.sendMessage("선택한 클래스는 ${ChatColor.BOLD}$displayName${ChatColor.RESET}입니다.")
                        player.removeScoreboardTag("Open_Menu")
                        player.addScoreboardTag(displayName)
                        player.closeInventory()
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 2.0F)
                        when {
                            player.scoreboardTags.contains(LOCALIZATION.berserker.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.berserker.weapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.berserker.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.berserker.skill[1]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.berserker.skill[2]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.archer.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.archer.weapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.archer.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.archer.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.archer.item[0]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.archer.skill[2]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.firewizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.firewizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.firewizard.skill[1]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.firewizard.skill[2]
                                )
                                player.scoreboardTags.add("mana_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.waterwizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.waterwizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.waterwizard.skill[1]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.waterwizard.skill[2]
                                )
                                player.speedIncrease(INFINITE_DURATION, 20)
                                player.scoreboardTags.add("mana_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.timemanipulator.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.timemanipulator.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.timemanipulator.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.timemanipulator.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.timemanipulator.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.landwizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.landwizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.landwizard.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.landwizard.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.landwizard.skill[3]
                                )
                                player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, INFINITE_DURATION, 1, false, false, true))
                                player.scoreboardTags.add("mana_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.windwizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.windwizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.windwizard.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.windwizard.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.windwizard.skill[3]
                                )
                                player.addPotionEffect(
                                    PotionEffect(
                                        PotionEffectType.SLOW_FALLING, INFINITE_DURATION, 0, false, false, true
                                    )
                                )
                                player.scoreboardTags.add("mana_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.gravitationalmanipulator.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.gravitationalmanipulator.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.gravitationalmanipulator.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.gravitationalmanipulator.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.gravitationalmanipulator.skill[3]
                                )
                                player.scoreboardTags.add("gravity_user")
                                Skill.resetGravity(player)
                            }

                            player.scoreboardTags.contains(LOCALIZATION.gambler.name) -> {
                                player.inventory.setItem(
                                    1, LOCALIZATION.gambler.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.gambler.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.gambler.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.gambler.skill[3]
                                )
                                Skill.cardDeckReset()
                                Skill.cardDeckShuffle()
                                player.cardDraw(5)

                            }

                            player.scoreboardTags.contains(LOCALIZATION.knight.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.knightweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.knight.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.knight.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.knight.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.knight.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.spaceoperator.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.spaceoperator.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.spaceoperator.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.spaceoperator.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.spaceoperator.skill[3]
                                )
                                player.scoreboardTags.add("charge_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.lightningwizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.lightningwizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.lightningwizard.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.lightningwizard.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.lightningwizard.skill[3]
                                )
                                player.scoreboardTags.add("mana_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.lightwizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.lightwizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.lightwizard.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.lightwizard.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.lightwizard.skill[3]
                                )
                                player.scoreboardTags.add("mana_user")
                                player.addPotionEffect(
                                    PotionEffect(
                                        PotionEffectType.NIGHT_VISION, INFINITE_DURATION, 0, false, false, true
                                    )
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.darkwizard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.darkwizard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.darkwizard.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.darkwizard.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.darkwizard.skill[3]
                                )
                                player.scoreboardTags.add("mana_user")
                            }

                            player.scoreboardTags.contains(LOCALIZATION.priests.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.priestsweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.priests.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.priests.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.priests.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.priests.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.warlock.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.warlockweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.warlock.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.warlock.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.warlock.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.warlock.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.mathematician.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.mathematician.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.mathematician.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.mathematician.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.mathematician.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.physicist.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.physicist.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.physicist.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.physicist.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.physicist.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.paladin.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.paladinweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.paladin.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.paladin.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.paladin.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.paladin.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.bard.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.bardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.bard.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.bard.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.bard.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.bard.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.judge.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.judgeweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.judge.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.judge.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.judge.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.judge.skill[3]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.duelist.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.duelistweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.duelist.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.duelist.skill[3]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.duelist.skill[4]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.duelist.skill[5]
                                )
                            }

                            player.scoreboardTags.contains(LOCALIZATION.astronomer.name) -> {
                                player.inventory.setItem(
                                    0, LOCALIZATION.wizardweapon
                                )
                                player.inventory.setItem(
                                    1, LOCALIZATION.astronomer.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.astronomer.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.astronomer.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.astronomer.skill[3]
                                )
                                player.scoreboardTags.add("mana_user")
                            }
                            player.scoreboardTags.contains(LOCALIZATION.assassin.name) -> {
                                player.inventory.setItem(
                                    1, LOCALIZATION.assassin.skill[0]
                                )
                                player.inventory.setItem(
                                    2, LOCALIZATION.assassin.skill[1]
                                )
                                player.inventory.setItem(
                                    3, LOCALIZATION.assassin.skill[2]
                                )
                                player.inventory.setItem(
                                    8, LOCALIZATION.assassin.skill[3]
                                )
                            }
                        }
                    } else {
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 2.0F)
                        val selectedClassMessage =
                            "${player.name}님이 선택한 클래스는 ${ChatColor.BOLD}$displayName${ChatColor.RESET}입니다."
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
                            teams = mutableMapOf(
                                "RedTeam" to Bukkit.getScoreboardManager().mainScoreboard.getTeam("RedTeam"),
                                "BlueTeam" to Bukkit.getScoreboardManager().mainScoreboard.getTeam("BlueTeam"),
                                "SpectatorTeam" to Bukkit.getScoreboardManager().mainScoreboard.getTeam("SpectatorTeam")
                            )
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
                else {
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 0.5F)
                }
            }
            event.isCancelled = true
        } else if (inventory.type == InventoryType.BARREL) {
            event.isCancelled = true
        }
    }

    fun gameEnd() {
        CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
        val marker = Bukkit.getWorld("world")?.entities?.filterIsInstance<Marker>()?.toList()
        val armorStands = Bukkit.getWorld("world")?.entities?.filterIsInstance<ArmorStand>()?.toList()
        marker?.forEach { it.remove() }
        armorStands?.forEach { it.remove() }
        for ((_, team) in teams) {
            team?.entries?.forEach { entry ->
                team.removeEntry(entry)
            }
        }

        Bukkit.getOnlinePlayers().forEach { player ->
            player.activePotionEffects.forEach { effect ->
                player.removePotionEffect(effect.type)
            }
            val playerTags = player.scoreboardTags.toList()
            for (tag in playerTags) {
                player.removeScoreboardTag(tag)
            }
            player.setPlayerListName(player.name)
            player.inventory.clear()
            player.health = player.maxHealth
            player.fireTicks = 0

            if (isInsideRegion(
                    player.location,
                    Location(Bukkit.getWorld("world"), -5.0, -61.0, -19.0),
                    Location(Bukkit.getWorld("world"), 7.0, -54.0, -8.0)
                ) || isInsideRegion(
                    player.location,
                    Location(Bukkit.getWorld("world"), 31.0, -64.0, -58.0),
                    Location(Bukkit.getWorld("world"), 63.0, -44.0, -26.0)
                )
            ) {
                player.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
            }

            if (player.isBattlefield()) {
                player.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
            }
            player.gameMode = GameMode.ADVENTURE
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard players reset @a")
        reset()
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player

        if (player !is Player) return
        if (player.scoreboardTags.contains("Open_Menu") && !player.isTraining()) {
            Bukkit.broadcastMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${player.name}님이 클래스 선택을 거부하여 게임이 종료됩니다.")
            gameEnd()
        } else if (player.scoreboardTags.contains("Open_Menu") && player.isTraining()) {
            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 클래스 선택을 거부하여 훈련이 종료됩니다.")
            player.inventory.clear()
            val playerTags = player.scoreboardTags.toList()
            for (tag in playerTags) {
                player.removeScoreboardTag(tag)
            }
            player.activePotionEffects.forEach { effect ->
                player.removePotionEffect(effect.type)
            }
            player.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.entity
        val damagerEntity = event.damager

        if (isStarting) {
            if (damagerEntity is Player && damagedEntity is Player) {
                if (!damagedEntity.isBattlefield()) {
                    event.isCancelled = true
                }

                event.damage *= (damagedEntity.scoreboard.getObjective("DamageMultiplier")?.getScore(damagedEntity.name)!!.score / 100)
            }
        }
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        if (isStarting) {
            event.isCancelled = true
        } else if (event.player.scoreboardTags.contains("training")) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity


        if (isGaming) {
            if (player.scoreboardTags.contains("dont_death")) {
                event.isCancelled = true
                return
            }

            if (player.isTeam("RedTeam")) {
                if (teams["RedTeam"]?.players?.size == 1) {
                    for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(
                            "${ChatColor.BOLD}${ChatColor.DARK_BLUE}블루팀 승리",
                            "${ChatColor.BOLD}상대를 전멸시켜 승리했습니다.",
                            20,
                            40,
                            20
                        )
                    }
                    gameEnd()
                } else {
                    teams["RedTeam"]?.removePlayer(player)
                    val playerTags = player.scoreboardTags.toList()
                    for (tag in playerTags) {
                        player.removeScoreboardTag(tag)
                    }
                    player.setPlayerListName(player.name)
                    player.inventory.clear()
                    player.health = player.maxHealth
                    player.fireTicks = 0
                    player.gameMode = GameMode.SPECTATOR
                }
            } else if (player.isTeam("BlueTeam")) {
                if (teams["BlueTeam"]?.players?.size == 1) {
                    for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(
                            "${ChatColor.BOLD}${ChatColor.DARK_RED}레드팀 승리",
                            "${ChatColor.BOLD}상대를 전멸시켜 승리했습니다.",
                            20,
                            40,
                            20
                        )
                    }
                    gameEnd()
                } else {
                    teams["BlueTeam"]?.removePlayer(player)
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

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val block: Block = event.clickedBlock ?: return

        // 플레이어가 상자를 우클릭했는지 확인
        if (block.state is Barrel && event.action.toString().contains("RIGHT")) {
            val barrel: Barrel = block.state as Barrel
            when (barrel.customName) {
                "${ChatColor.BOLD}광전사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.berserker.weapon)
                    barrel.inventory.setItem(10, LOCALIZATION.berserker.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.berserker.skill[1])
                    barrel.inventory.setItem(17, LOCALIZATION.berserker.skill[2])
                }

                "${ChatColor.BOLD}궁수" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }

                    barrel.inventory.setItem(9, LOCALIZATION.archer.weapon)
                    barrel.inventory.setItem(10, LOCALIZATION.archer.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.archer.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.archer.item[0])
                    barrel.inventory.setItem(17, LOCALIZATION.archer.skill[2])
                }

                "${ChatColor.BOLD}불마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.firewizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.firewizard.skill[1])
                    barrel.inventory.setItem(17, LOCALIZATION.firewizard.skill[2])
                }

                "${ChatColor.BOLD}물마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.waterwizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.waterwizard.skill[1])
                    barrel.inventory.setItem(17, LOCALIZATION.waterwizard.skill[2])
                }

                "${ChatColor.BOLD}시간 조작자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.timemanipulator.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.timemanipulator.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.timemanipulator.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.timemanipulator.skill[3])
                }

                "${ChatColor.BOLD}대지 마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.landwizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.landwizard.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.landwizard.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.landwizard.skill[3])
                }

                "${ChatColor.BOLD}바람 마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.windwizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.windwizard.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.windwizard.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.windwizard.skill[3])
                }

                "${ChatColor.BOLD}중력 조작자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.gravitationalmanipulator.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.gravitationalmanipulator.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.gravitationalmanipulator.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.gravitationalmanipulator.skill[3])
                }

                "${ChatColor.BOLD}도박사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(10, LOCALIZATION.gambler.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.gambler.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.gambler.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.gambler.skill[3])
                }

                "${ChatColor.BOLD}기사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.knightweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.knight.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.knight.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.knight.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.knight.skill[3])
                }

                "${ChatColor.BOLD}공간 조작자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.spaceoperator.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.spaceoperator.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.spaceoperator.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.spaceoperator.skill[3])
                }

                "${ChatColor.BOLD}번개 마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.lightningwizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.lightningwizard.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.lightningwizard.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.lightningwizard.skill[3])
                }

                "${ChatColor.BOLD}빛 마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.lightwizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.lightwizard.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.lightwizard.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.lightwizard.skill[3])
                }

                "${ChatColor.BOLD}어둠 마법사" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.darkwizard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.darkwizard.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.darkwizard.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.darkwizard.skill[3])
                }

                "${ChatColor.BOLD}사제" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.priestsweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.priests.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.priests.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.priests.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.priests.skill[3])
                }

                "${ChatColor.BOLD}워락" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.warlockweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.warlock.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.warlock.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.warlock.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.warlock.skill[3])
                }

                "${ChatColor.BOLD}수학자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.mathematician.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.mathematician.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.mathematician.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.mathematician.skill[3])
                }

                "${ChatColor.BOLD}물리학자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.physicist.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.physicist.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.physicist.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.physicist.skill[3])
                }

                "${ChatColor.BOLD}팔라딘" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.paladinweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.paladin.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.paladin.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.paladin.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.paladin.skill[3])
                }

                "${ChatColor.BOLD}바드" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.bardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.bard.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.bard.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.bard.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.bard.skill[3])
                }

                "${ChatColor.BOLD}심판자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.judgeweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.judge.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.judge.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.judge.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.judge.skill[3])
                }

                "${ChatColor.BOLD}결투가" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.duelistweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.duelist.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.duelist.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.duelist.skill[2])
                    barrel.inventory.setItem(13, LOCALIZATION.duelist.skill[3])
                    barrel.inventory.setItem(14, LOCALIZATION.duelist.skill[4])
                    barrel.inventory.setItem(17, LOCALIZATION.duelist.skill[5])
                }

                "${ChatColor.BOLD}천문학자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(9, LOCALIZATION.wizardweapon)
                    barrel.inventory.setItem(10, LOCALIZATION.astronomer.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.astronomer.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.astronomer.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.astronomer.skill[3])
                }

                "${ChatColor.BOLD}암살자" -> {
                    for (i in 0 until barrel.inventory.size) {
                        barrel.inventory.setItem(i, LOCALIZATION.nullpane)
                    }
                    barrel.inventory.setItem(10, LOCALIZATION.assassin.skill[0])
                    barrel.inventory.setItem(11, LOCALIZATION.assassin.skill[1])
                    barrel.inventory.setItem(12, LOCALIZATION.assassin.skill[2])
                    barrel.inventory.setItem(17, LOCALIZATION.assassin.skill[3])
                }
            }
        }
    }

    @EventHandler
    fun onSwapHands(event: PlayerSwapHandItemsEvent) {
        if (isStarting) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val whoClicked = event.whoClicked

        if (whoClicked is Player) {
            if (isStarting) {
                event.isCancelled = true
            } else if (whoClicked.scoreboardTags.contains("training")) {
                event.isCancelled = true
            }
        }
    }

    private fun isInsideRegion(location: Location, minPoint: Location, maxPoint: Location): Boolean {
        return (location.x >= minPoint.x && location.x <= maxPoint.x && location.y >= minPoint.y && location.y <= maxPoint.y && location.z >= minPoint.z && location.z <= maxPoint.z)
    }

}
