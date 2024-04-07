@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.gravitationalmanipulator
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addLevitation
import org.beobma.classwar.util.Skill.Companion.addLevitationDown
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.getGravity
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeGravity
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable


class GRAVITATIONALMANIPULATOR : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == gravitationalmanipulator.name) {
            when (skill) {
                gravitationalmanipulator.skill[0] -> { // 일반 스킬
                    if (getGravity(player) < 3) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RED}${ChatColor.BOLD}이 부족하여 스킬을 발동할 수 없습니다.")
                    } else {
                        for (players in Bukkit.getOnlinePlayers()) {
                            if (players.isSkillTarget()) {
                                players.addLevitation(3)
                            }
                        }
                        player.location.world.playSound(
                            player.location, Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.3f
                        )
                        val fixedY = -59.8

                        val playerX = player.location.blockX
                        val playerZ = player.location.blockZ

                        for (x in playerX - 12..playerX + 11 step 2) {
                            for (z in playerZ - 12..playerZ + 11 step 2) {
                                val location = player.location.world.getHighestBlockAt(x, z).location.add(0.5, 0.0, 0.5)
                                location.y = fixedY
                                location.world.spawnParticle(
                                    Particle.END_ROD, location, 0, 0.0, 1.0, 0.0, 0.5
                                )
                            }
                        }

                        removeGravity(player, 3)
                        player.coolTime(10, 1, gravitationalmanipulator.skill[0])
                    }
                }

                gravitationalmanipulator.skill[1] -> { // 보조 스킬
                    if (getGravity(player) < 5) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RED}${ChatColor.BOLD}이 부족하여 스킬을 발동할 수 없습니다.")
                    } else {
                        for (players in Bukkit.getOnlinePlayers()) {
                            if (players.isSkillTarget()) {
                                players.addLevitationDown(3)
                                if (player.isTeam("RedTeam")) {
                                    if (players.isTeam("BlueTeam")) {
                                        if (!players.isOnGround) {
                                            players.damageHealth(
                                                15, gravitationalmanipulator.getSkillName(1), players
                                            )
                                        } else {
                                            players.damageHealth(
                                                5, gravitationalmanipulator.getSkillName(1), players
                                            )
                                        }
                                    }
                                } else {
                                    if (player.isTeam("RedTeam")) {
                                        if (!players.isOnGround) {
                                            players.damageHealth(
                                                15, gravitationalmanipulator.getSkillName(1), players
                                            )
                                        } else {
                                            players.damageHealth(
                                                5, gravitationalmanipulator.getSkillName(1), players
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        player.location.world.playSound(
                            player.location, Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 0.75f
                        )

                        val fixedY = -45.2

                        val playerX = player.location.blockX
                        val playerZ = player.location.blockZ

                        for (x in playerX - 12..playerX + 11 step 2) {
                            for (z in playerZ - 12..playerZ + 11 step 2) {
                                val location = player.location.world.getHighestBlockAt(x, z).location.add(0.5, 0.0, 0.5)
                                location.y = fixedY
                                location.world.spawnParticle(
                                    Particle.END_ROD, location, 0, 0.0, -1.0, 0.0, 1.0
                                )
                            }
                        }

                        removeGravity(player, 5)
                        player.coolTime(10, 2, gravitationalmanipulator.skill[1])
                    }

                }

                gravitationalmanipulator.skill[2] -> { // 궁극 스킬
                    if (getGravity(player) < 10) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RED}${ChatColor.BOLD}이 부족하여 스킬을 발동할 수 없습니다.")
                    } else {
                        val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                        if (shootResort == null) {
                            player.inventory.setItem(3, gravitationalmanipulator.skill[2])
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                            return
                        }

                        if (shootResort is Player) {
                            player.sendMessage(
                                "${ChatColor.YELLOW}${ChatColor.BOLD}[!] ${
                                    gravitationalmanipulator.getSkillName(
                                        2
                                    )
                                }에 의하여 ${shootResort.name}에게 연결되었습니다."
                            )
                            shootResort.sendMessage(
                                "${ChatColor.YELLOW}${ChatColor.BOLD}[!] ${
                                    gravitationalmanipulator.getSkillName(
                                        2
                                    )
                                }에 의하여 ${player.name}에게 연결되었습니다."
                            )

                            object : BukkitRunnable() {
                                override fun run() {

                                    val directions =
                                        shootResort.location.toVector().subtract(player.location.toVector()).normalize()
                                    val distance = player.location.distance(shootResort.location)


                                    if (distance <= 2) return

                                    val particleDistance = 0.5
                                    val particleCount = (distance / particleDistance).toInt()
                                    for (i in 0 until particleCount) {
                                        val particleLocation =
                                            player.location.clone().add(directions.multiply(particleDistance * i))
                                        particleLocation.world.spawnParticle(
                                            Particle.BUBBLE_POP, particleLocation, 0, 0.0, 0.0, 0.0, 0.0
                                        )
                                    }

                                    val direction =
                                        shootResort.location.toVector().subtract(player.location.toVector()).normalize()
                                            .multiply(0.1)
                                    player.velocity = player.velocity.add(direction)

                                    val direction2 =
                                        player.location.toVector().subtract(shootResort.location.toVector()).normalize()
                                            .multiply(0.1)
                                    shootResort.velocity = shootResort.velocity.add(direction2)
                                }
                            }.runTaskTimer(CLASSWAR.instance, 0L, 1L)

                            player.location.world.playSound(
                                player.location, Sound.BLOCK_RESPAWN_ANCHOR_AMBIENT, 1.0f, 2f
                            )

                            removeGravity(player, 10)
                            player.inventory.setItem(3, null)
                        } else {
                            player.inventory.setItem(3, gravitationalmanipulator.skill[2])
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 플레이어여만 합니다.")
                            return
                        }
                    }
                }
            }
        }
    }
}