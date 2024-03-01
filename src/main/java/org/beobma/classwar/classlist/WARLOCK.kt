@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.warlock
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.createParticlesInSphere
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addFatalWound
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.isFatalWound
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.shootLaserFromBlock
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class WARLOCK : Listener {

    private var chance: Int = 2
    private var task: BukkitTask? = null

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        chance = 2
        task = null
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == warlock.name) {
            when (skill) {
                warlock.skill[0] -> { // 일반 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort == null) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    if (shootResort is Player) {
                        shootResort.addFatalWound(5)
                    }
                    if (player.health <= 10) {
                        if (chance != 0) {
                            chance - 1

                        } else {
                            player.damageHealth(10, warlock.getSkillName(0), player)
                        }
                    } else {
                        player.damageHealth(10, warlock.getSkillName(0), player)
                    }
                    player.location.world.playSound(
                        player.location, Sound.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1.0f, 0.5f
                    )
                    spawnParticleRadius(shootResort.location, Particle.WHITE_ASH, 2.0, 1.0)
                    player.coolTime(10, 1, warlock.skill[0])
                }

                warlock.skill[1] -> { // 보조 스킬
                    val shootResolt = shootLaserFromBlock(player)

                    if (shootResolt == null) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 블럭이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    task?.cancel()
                    task = object : BukkitRunnable() {
                        override fun run() {
                            val location = shootResolt.location
                            createParticlesInSphere(location, 5.0, location.world, Particle.SCULK_SOUL, 25)

                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isRadius(location, 5.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        if (targetPlayer.isFatalWound()) {
                                            targetPlayer.damage(3.0)
                                            player.health += 1.5
                                        } else {
                                            targetPlayer.damage(2.0)
                                        }
                                    }
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 1L)

                    if (player.health <= 10) {
                        if (chance != 0) {
                            chance - 1

                        } else {
                            player.damageHealth(10, warlock.getSkillName(1), player)
                        }
                    } else {
                        player.damageHealth(10, warlock.getSkillName(1), player)
                    }

                    player.coolTime(20, 2, warlock.skill[1])
                }

                warlock.skill[2] -> { // 궁극 스킬
                    var shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort == null) {
                        shootResort = player
                    }

                    if (shootResort is Player) {
                        var int = 0
                        var distance = 1.0
                        object : BukkitRunnable() {
                            override fun run() {
                                if (int > 200) {
                                    this.cancel()
                                } else {
                                    spawnParticleRadius(shootResort.location, Particle.SCULK_SOUL, distance, 0.05)
                                    for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                        if (targetPlayer.isRadius(shootResort.location, distance)) {
                                            if (targetPlayer.isSkillTarget()) {
                                                if (targetPlayer != shootResort) {
                                                    if (targetPlayer.isFatalWound()) {
                                                        targetPlayer.damage(3.0)
                                                        player.health += 1.5
                                                    } else {
                                                        targetPlayer.damage(2.0)
                                                        player.health += 1.0
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                distance += 0.04
                                int++
                            }
                        }.runTaskTimer(CLASSWAR.instance, 0L, 1L)
                    }

                    if (player.health <= 20) {
                        if (chance != 0) {
                            chance - 1

                        } else {
                            player.damageHealth(20, warlock.getSkillName(2), player)
                        }
                    } else {
                        player.damageHealth(20, warlock.getSkillName(2), player)
                    }
                    player.inventory.setItem(3, null)
                }
            }
        }
    }
}