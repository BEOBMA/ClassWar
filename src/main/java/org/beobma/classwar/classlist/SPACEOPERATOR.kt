@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.LOCALIZATION.Companion.spaceoperator
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.attackDamage
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.exile
import org.beobma.classwar.util.Skill.Companion.getCharging
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.isTraining
import org.beobma.classwar.util.Skill.Companion.removeCharging
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask

class SPACEOPERATOR : Listener {

    private var task: BukkitTask? = null
    private var playerResetLocation: Location? = null
    private var shootResortResetLocation: Location? = null

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == spaceoperator.name) {
            when (skill) {
                spaceoperator.skill[0] -> { // 일반 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false, 2.0)

                    if (shootResort == null || shootResort !is Player) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    if (player.isTeam("RedTeam")) {
                        if (shootResort.isTeam("RedTeam")) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                            return
                        }
                    } else if (player.isTeam("BlueTeam")) {
                        if (shootResort.isTeam("BlueTeam")) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                            return
                        }
                    }

                    if (player.getCharging() >= 40) {
                        player.removeCharging(40)
                        player.attackDamage(3, 3)

                        shootResort.damageHealth(15, spaceoperator.getSkillName(0), player)

                        shootResort.location.world.playSound(
                            shootResort.location, Sound.BLOCK_BEACON_POWER_SELECT, 1.0F, 2.0F
                        )
                        org.beobma.classwar.util.Particle.spawnFanShapeParticles(
                            player, Particle.WAX_OFF, 2.0, 100, 85.0
                        )


                    } else {
                        shootResort.damageHealth(10, spaceoperator.getSkillName(0), player)

                        shootResort.location.world.playSound(
                            shootResort.location, Sound.BLOCK_BEACON_POWER_SELECT, 1.0F, 1.7F
                        )
                        org.beobma.classwar.util.Particle.spawnFanShapeParticles(
                            player, Particle.WAX_ON, 2.0, 100, 85.0
                        )
                    }

                    player.coolTime(10, 1, spaceoperator.skill[0])

                }

                spaceoperator.skill[1] -> { // 보조 스킬
                    val startLocation = player.location
                    val direction = player.location.direction
                    val scaledDirection = direction.normalize().multiply(4.0)
                    val newLocation = player.location.add(scaledDirection)
                    val endLocation = newLocation.setDirection(direction)

                    player.teleport(newLocation.setDirection(direction))

                    if (player.getCharging() >= 40) {
                        player.removeCharging(40)
                        player.attackDamage(3, 3)

                        player.world.players.forEach { otherPlayer ->
                            if (otherPlayer != player) {
                                val otherLocation = otherPlayer.location
                                if (otherPlayer.isSkillTarget()) {
                                    if (player.isTeam("RedTeam")) {
                                        if (otherPlayer.isTeam("BlueTeam")) {
                                            if (Skill.isBetween(startLocation, endLocation, otherLocation)) {
                                                otherPlayer.damageHealth(
                                                    10, spaceoperator.getSkillName(1), player
                                                )
                                            }
                                        }

                                    } else {
                                        if (otherPlayer.isTeam("RedTeam")) {
                                            if (Skill.isBetween(startLocation, endLocation, otherLocation)) {
                                                otherPlayer.damageHealth(
                                                    10, spaceoperator.getSkillName(1), player
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        if (player.isTeam("RedTeam")) {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isSkillTarget()) {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        if (targetPlayer.isRadius(player.location, 2.0)) {
                                            targetPlayer.damageHealth(
                                                7, spaceoperator.skill[1].itemMeta.displayName, player
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isSkillTarget()) {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        if (targetPlayer.isRadius(player.location, 2.0)) {
                                            targetPlayer.damageHealth(
                                                7, spaceoperator.skill[1].itemMeta.displayName, player
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }


                    val distance = startLocation.distance(endLocation)
                    val increment = endLocation.subtract(startLocation).toVector().normalize().multiply(distance / 100)

                    val currentLocation = startLocation.clone()
                    for (i in 0 until 100) {
                        player.world.spawnParticle(Particle.END_ROD, currentLocation, 1, 0.0, 0.0, 0.0, 0.01)
                        currentLocation.add(increment)
                    }

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1.0f, 2.0f
                    )

                    player.coolTime(10, 2, spaceoperator.skill[1])

                }

                spaceoperator.skill[2] -> { // 궁극 스킬
                    if (player.getCharging() < 100) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RED}${ChatColor.BOLD}이 부족하여 스킬을 사용할 수 없습니다.")
                        return
                    } else {
                        if (player.isTraining()) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 훈련중 해당 스킬을 사용할 수 없습니다.")
                            return
                        }
                        player.scoreboardTags.remove("charge_user")
                        player.attackDamage(3, 3)

                        val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                        if (shootResort == null || shootResort !is Player) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                            return
                        }

                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                                return
                            }
                        }

                        player.exile(7)
                        shootResort.exile(7)

                        player.inventory.setItem(3, null)
                    }
                }
            }
        }
    }
}