@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager.Companion.isGaming
import org.beobma.classwar.LOCALIZATION.Companion.firewizard
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Skill.Companion.addBurn
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import kotlin.math.cos
import kotlin.math.sin


class FIREWIZARD : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == firewizard.name) {
            when (skill) {
                firewizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 40) {
                        player.sendManaInsufficient()
                    } else {
                        if (player.isTeam("RedTeam")) {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isSkillTarget() && targetPlayer.isTeam("BlueTeam") && targetPlayer.isRadius(
                                        player.location, 5.0
                                    )
                                ) {
                                    targetPlayer.damageHealth(10, firewizard.getSkillName(0), player)
                                    targetPlayer.addBurn(3)
                                }
                            }
                        } else {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isSkillTarget() && targetPlayer.isTeam("RedTeam") && targetPlayer.isRadius(
                                        player.location, 5.0
                                    )
                                ) {
                                    targetPlayer.damageHealth(10, firewizard.getSkillName(0), player)
                                    targetPlayer.addBurn(3)
                                }
                            }
                        }

                        player.location.world.playSound(
                            player.location, Sound.ITEM_FIRECHARGE_USE, 0.5f, 0.5f
                        )
                        spawnParticleRadius(player.location, Particle.FLAME, 5.0, 0.05)

                        player.removeMana(40)
                        player.coolTime(10, 1, firewizard.skill[0])
                    }
                }

                firewizard.skill[1] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {
                        player.removeMana(100)
                        val world = player.location.world
                        val pos = player.location
                        val startY = pos.y + 100.0

                        CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.location.distance(pos) <= 6.0 && targetPlayer.isSkillTarget()) {
                                    targetPlayer.damageHealth(
                                        30, firewizard.getSkillName(1), player
                                    )
                                    targetPlayer.addBurn(5)
                                }
                            }
                            for (i in 0..100) {
                                val progress = i / 100.0
                                val currentY: Double = startY - progress * 100.0

                                var theta = 0
                                while (theta <= 360) {
                                    val radians = theta * (Math.PI / 180)
                                    val x = pos.x + 6.0 * cos(radians)
                                    val z = pos.z + 6.0 * sin(radians)

                                    val location = Location(world, x, currentY, z)
                                    world.spawnParticle(Particle.FLAME, location, 1, 0.0, 0.0, 0.0, 0.0)
                                    theta += 5
                                }
                            }
                            world.playSound(pos, Sound.ENTITY_BLAZE_SHOOT, 2f, 0.3f)
                            world.playSound(pos, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 2f, 2f)
                        }, 60L)

                        player.location.world.playSound(
                            player.location, Sound.ENTITY_WARDEN_SONIC_BOOM, 0.75f, 2f
                        )
                        player.location.world.spawnParticle(
                            Particle.FIREWORKS_SPARK, player.eyeLocation.add(0.0, 0.0, 0.0), 100, 1.0, 1.0, 1.0, 0.0
                        )
                        spawnParticleRadius(pos, Particle.FLAME, 6.0, 3.0)

                        player.inventory.setItem(2, null)

                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.damager
        val damagerEntity = event.entity

        if (isGaming) {
            if (damagerEntity.scoreboardTags.contains(firewizard.name) && damagerEntity is Player) {
                if (damagedEntity is Player) {
                    damagedEntity.addBurn(1)
                }
            }
        }
    }
}