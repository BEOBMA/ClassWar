@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.lightningwizard
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Skill.Companion.addElectric
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isElectric
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.math.cos
import kotlin.math.sin

class LIGHTNINGWIZARD : Listener {

    var marker: MutableList<Location> = mutableListOf()
    private var task: BukkitTask? = null

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        task = null
        marker = mutableListOf()
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == lightningwizard.name) {
            when (skill) {
                lightningwizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 20) {
                        player.sendManaInsufficient()
                    } else {
                        if (marker.size >= 3) {
                            marker.removeFirst()
                        }
                        marker.add(player.location)
                        task?.cancel()

                        task = object : BukkitRunnable() {
                            override fun run() {
                                marker.forEach {
                                    it.world.spawnParticle(Particle.WAX_OFF, it, 1, 0.0, 0.0, 0.0, 1.0)
                                }
                            }
                        }.runTaskTimer(CLASSWAR.instance, 0L, 1L)


                        player.location.world.playSound(
                            player.location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.5F, 2.0F
                        )

                        player.removeMana(20)
                        player.coolTime(3, 1, lightningwizard.skill[0])
                    }
                }

                lightningwizard.skill[1] -> { // 보조 스킬
                    if (player.getMana() < 40) {
                        player.sendManaInsufficient()
                    } else {
                        if (marker.size == 0) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.GOLD}${ChatColor.BOLD}표식${ChatColor.RED}${ChatColor.BOLD}이 없어 스킬을 발동할 수 없습니다.")
                            return
                        }

                        marker.forEach {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isRadius(it, 3.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        if (player.isTeam("RedTeam") && targetPlayer.isTeam("BlueTeam")) {
                                            targetPlayer.damageHealth(
                                                7, lightningwizard.getSkillName(1), player
                                            )
                                            targetPlayer.addElectric(10)

                                        } else if (player.isTeam("BlueTeam") && targetPlayer.isTeam("RedTeam")) {
                                            targetPlayer.damageHealth(
                                                7, lightningwizard.getSkillName(1), player
                                            )
                                            targetPlayer.addElectric(10)
                                        }
                                    }
                                }
                            }

                            it.world.playSound(it, Sound.BLOCK_SCULK_SENSOR_CLICKING, 1.0F, 0.5F)
                            spawnParticleRadius(it, Particle.WAX_OFF, 3.0, 1.0)
                        }

                        player.removeMana(40)
                        player.coolTime(10, 2, lightningwizard.skill[1])
                    }
                }

                lightningwizard.skill[2] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {
                        if (marker.size == 0) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.GOLD}${ChatColor.BOLD}표식${ChatColor.RED}${ChatColor.BOLD}이 없어 스킬을 발동할 수 없습니다.")
                            return
                        }

                        marker.forEach {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isRadius(it, 4.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        if (player.isTeam("RedTeam") && targetPlayer.isTeam("BlueTeam")) {
                                            targetPlayer.damageHealth(
                                                10, lightningwizard.getSkillName(2), player
                                            )
                                            if (targetPlayer.isElectric()) {
                                                targetPlayer.addElectric(10)
                                                for (targetPlayer2 in Bukkit.getServer().onlinePlayers) {
                                                    if (targetPlayer2.isRadius(targetPlayer.location, 4.0)) {
                                                        if (targetPlayer2.isSkillTarget()) {
                                                            if (targetPlayer2.isTeam("BlueTeam")) {
                                                                targetPlayer.damageHealth(
                                                                    5, lightningwizard.getSkillName(2), player
                                                                )
                                                                targetPlayer.addElectric(10)
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        } else if (player.isTeam("BlueTeam") && targetPlayer.isTeam("RedTeam")) {
                                            targetPlayer.damageHealth(
                                                10, lightningwizard.getSkillName(2), player
                                            )
                                            if (targetPlayer.isElectric()) {
                                                targetPlayer.addElectric(10)
                                                for (targetPlayer2 in Bukkit.getServer().onlinePlayers) {
                                                    if (targetPlayer2.isRadius(targetPlayer.location, 4.0)) {
                                                        if (targetPlayer2.isSkillTarget()) {
                                                            if (targetPlayer2.isTeam("RedTeam")) {
                                                                targetPlayer.damageHealth(
                                                                    5, lightningwizard.getSkillName(2), player
                                                                )
                                                                targetPlayer.addElectric(10)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            it.world.playSound(it, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.5F, 1.0F)
                            val startY = it.y + 100.0
                            for (i in 0..100) {
                                val progress = i / 100.0
                                val currentY = startY - progress * 100.0

                                for (theta in 0..360 step 30) {
                                    val radians = Math.toRadians(theta.toDouble())
                                    val x = it.x + 1.0 * cos(radians)
                                    val z = it.z + 1.0 * sin(radians)

                                    val location = Location(it.world, x, currentY, z)
                                    it.world.spawnParticle(Particle.WAX_OFF, location, 1, 0.0, 0.0, 0.0, 0.0)
                                }
                            }
                            spawnParticleRadius(it, Particle.WAX_OFF, 4.0, 1.0)
                        }

                        marker = mutableListOf()
                        task?.cancel()


                        player.removeMana(100)
                        player.inventory.setItem(3, null)
                    }
                }
            }
        }
    }
}