@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.priests
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addUntargetability
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.healingHealth
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.shieldAdd
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.scheduler.BukkitRunnable

class PRIESTS : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == priests.name) {
            when (skill) {
                priests.skill[0] -> { // 일반 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort is Player) {
                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 적에게 이 스킬을 사용할 수 없습니다.")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 적에게 이 스킬을 사용할 수 없습니다.")
                                return
                            }
                        }
                        shootResort.healingHealth(5, priests.getSkillName(0), player)
                    }

                    if (shootResort == null) {
                        player.healingHealth(5, priests.getSkillName(0), player)
                    }

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1.0f, 1.3f
                    )
                    player.coolTime(10, 1, priests.skill[0])
                }

                priests.skill[1] -> { // 보조 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort is Player) {
                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 적에게 이 스킬을 사용할 수 없습니다.")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 적에게 이 스킬을 사용할 수 없습니다.")
                                return
                            }
                        }

                        if (shootResort.scoreboardTags.contains("stun") || shootResort.scoreboardTags.contains("fear")) {
                            shootResort.scoreboardTags.remove("stun")
                            shootResort.scoreboardTags.remove("fear")
                            shootResort.sendMessage("${player}에 의해 이동 불가 상태이상이 모두 정화되었습니다.")
                        }
                    }

                    if (shootResort == null) {
                        if (player.scoreboardTags.contains("stun") || player.scoreboardTags.contains("fear")) {
                            player.scoreboardTags.remove("stun")
                            player.scoreboardTags.remove("fear")
                            player.sendMessage("${player}에 의해 이동 불가 상태이상이 모두 정화되었습니다.")
                        }
                    }

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.3f
                    )
                    player.coolTime(10, 2, priests.skill[1])
                }

                priests.skill[2] -> { // 궁극 스킬
                    var shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort == null) {
                        shootResort = player
                    }

                    if (shootResort is Player) {
                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 적에게 이 스킬을 사용할 수 없습니다.")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 적에게 이 스킬을 사용할 수 없습니다.")
                                return
                            }
                        }

                        shootResort.addScoreboardTag("dont_death")
                        object : BukkitRunnable() {
                            override fun run() {
                                shootResort.removeScoreboardTag("dont_death")
                                shootResort.addUntargetability(3)
                            }
                        }.runTaskLater(CLASSWAR.instance, 60L)
                    }

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 2.0f
                    )

                    player.inventory.setItem(3, null)
                }
            }
        }
    }

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        Bukkit.getServer().onlinePlayers.forEach {
            if (it.scoreboardTags.contains(priests.name)) {
                if (it.isTeam("RedTeam")) {
                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                        if (targetPlayer.isTeam("RedTeam")) {
                            targetPlayer.shieldAdd(PotionEffect.INFINITE_DURATION, 4)
                        }
                    }
                } else {
                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                        if (targetPlayer.isTeam("BlueTeam")) {
                            targetPlayer.shieldAdd(PotionEffect.INFINITE_DURATION, 4)
                        }
                    }
                }
            }
        }
    }
}