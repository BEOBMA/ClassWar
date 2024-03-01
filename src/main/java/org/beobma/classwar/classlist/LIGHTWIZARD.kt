@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager
import org.beobma.classwar.LOCALIZATION.Companion.lightwizard
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addBrilliance
import org.beobma.classwar.util.Skill.Companion.addSilence
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isBrilliance
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.isTraining
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class LIGHTWIZARD : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == lightwizard.name) {
            when (skill) {
                lightwizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 40) {
                        player.sendManaInsufficient()
                    } else {

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

                        if (shootResort.isBrilliance()) {
                            shootResort.damageHealth(10, lightwizard.getSkillName(0), player)
                        } else {
                            shootResort.addBrilliance(5)
                        }

                        shootResort.location.world.spawnParticle(
                            Particle.END_ROD, shootResort.eyeLocation, 10, 0.0, 0.0, 0.0, 0.05
                        )
                        shootResort.location.world.playSound(
                            shootResort.location, Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0F, 2.0F
                        )
                        player.removeMana(40)
                        player.coolTime(15, 1, lightwizard.skill[0])
                    }
                }

                lightwizard.skill[1] -> { // 보조 스킬
                    if (player.getMana() < 60) {
                        player.sendManaInsufficient()
                    } else {
                        player.addSilence(3)

                        var int = 0
                        object : BukkitRunnable() {
                            override fun run() {
                                if (int >= 60) {
                                    this.cancel()
                                }
                                val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false, 5.0)


                                val startLocation = player.eyeLocation
                                val direction = startLocation.direction

                                val scaledDirection = direction.normalize().multiply(5.0)
                                val newLocation = startLocation.clone().add(scaledDirection)

                                newLocation.direction = direction

                                val distance = 5.0
                                val increment = scaledDirection.normalize().multiply(distance / 100)
                                val currentLocation = startLocation.clone()
                                for (i in 0 until 50) {
                                    player.world.spawnParticle(
                                        Particle.END_ROD, currentLocation, 1, 0.0, 0.0, 0.0, 0.0
                                    )
                                    currentLocation.add(increment)
                                }


                                if (shootResort != null && shootResort is Player) {
                                    if (player.isTeam("RedTeam")) {
                                        if (shootResort.isTeam("BlueTeam")) {
                                            if (shootResort.isBrilliance()) {
                                                shootResort.addBrilliance(5)
                                                shootResort.damageHealth(2, lightwizard.getSkillName(1), player)
                                            } else {
                                                shootResort.addBrilliance(5)
                                            }
                                        }
                                    } else if (player.isTeam("BlueTeam")) {
                                        if (shootResort.isTeam("RedTeam")) {
                                            if (shootResort.isBrilliance()) {
                                                shootResort.addBrilliance(5)
                                                shootResort.damageHealth(2, lightwizard.getSkillName(1), player)
                                            }
                                        }
                                    }
                                }
                                int++
                            }
                        }.runTaskTimer(CLASSWAR.instance, 0L, 1L)

                        player.removeMana(60)
                        player.coolTime(13, 2, lightwizard.skill[1])
                    }
                }

                lightwizard.skill[2] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {

                        var int = 0
                        GameManager.gamingPlayer?.forEach {
                            if (it.isBrilliance()) {
                                int++
                            }
                        }

                        when (int) {
                            0 -> {
                                if (player.isTeam("RedTeam")) {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("BlueTeam")) {
                                                targetPlayer.damageHealth(
                                                    3, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("RedTeam")) {
                                                targetPlayer.damageHealth(
                                                    3, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (player.isTeam("RedTeam")) {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("BlueTeam")) {
                                                targetPlayer.damageHealth(
                                                    6, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("RedTeam")) {
                                                targetPlayer.damageHealth(
                                                    6, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            2 -> {
                                if (player.isTeam("RedTeam")) {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("BlueTeam")) {
                                                targetPlayer.damageHealth(
                                                    9, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("RedTeam")) {
                                                targetPlayer.damageHealth(
                                                    9, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            3 -> {
                                if (player.isTeam("RedTeam")) {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("BlueTeam")) {
                                                targetPlayer.damageHealth(
                                                    14, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("RedTeam")) {
                                                targetPlayer.damageHealth(
                                                    14, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            else -> {
                                if (player.isTeam("RedTeam")) {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("BlueTeam")) {
                                                targetPlayer.damageHealth(
                                                    20, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isSkillTarget()) {
                                            if (targetPlayer.isTeam("RedTeam")) {
                                                targetPlayer.damageHealth(
                                                    20, lightwizard.skill[2].itemMeta.displayName, player
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }


                        val center = if (player.isTraining()) {
                            Location(player.world, 47.5, -52.0, -43.5)
                        } else Location(player.world, 12.5, -52.0, -41.5)
                        val initialRadius = 10.0
                        val duration = 40.0
                        val particle = Particle.END_ROD
                        val world = center.world

                        world.playSound(center, Sound.BLOCK_BEACON_ACTIVATE, 99.0F, 0.7F)
                        object : BukkitRunnable() {
                            var radius = initialRadius
                            val decreaseFactor = initialRadius / (duration / 10)

                            override fun run() {
                                if (radius <= 0) {
                                    this.cancel()
                                    return
                                }

                                val points = (radius * 100).toInt()
                                for (i in 0 until points) {
                                    val angle = 2 * Math.PI * i / points
                                    val x = center.x + radius * cos(angle)
                                    val z = center.z + radius * sin(angle)
                                    val y = center.y

                                    world.spawnParticle(particle, x, y, z, 1, 0.0, 0.0, 0.0, 0.0)
                                }

                                org.beobma.classwar.util.Particle.spawnSphereParticles(center, Particle.END_ROD, 3.0)

                                radius -= decreaseFactor
                            }
                        }.runTaskTimer(CLASSWAR.instance, 0L, 10L)

                        player.removeMana(100)
                        player.inventory.setItem(3, null)
                    }
                }
            }
        }
    }
}