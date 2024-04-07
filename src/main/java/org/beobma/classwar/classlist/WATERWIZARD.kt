@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager.Companion.isStarting
import org.beobma.classwar.LOCALIZATION.Companion.waterwizard
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Particle.Companion.spawnSphereParticles
import org.beobma.classwar.util.Skill.Companion.addSlow
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.findClosestFriendly
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.Skill.Companion.shieldAdd
import org.beobma.classwar.util.Skill.Companion.spawnProjectile
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffectType

class WATERWIZARD : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == waterwizard.name) {
            when (skill) {
                waterwizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 40) {
                        player.sendManaInsufficient()
                    } else {
                        if (player.isTeam("RedTeam")) {
                            val targetPlayer = player.findClosestFriendly("RedTeam")
                            if (targetPlayer is Player) {
                                targetPlayer.shieldAdd(2, 4)
                                targetPlayer.scoreboardTags.add("waterwizard_shield")
                                CLASSWAR.instance.server.scheduler.runTaskLater(
                                    CLASSWAR.instance, Runnable {
                                        targetPlayer.scoreboardTags.remove("waterwizard_shield")
                                    }, 60
                                )
                            }
                        } else {
                            val targetPlayer = player.findClosestFriendly("BlueTeam")
                            if (targetPlayer is Player) {
                                targetPlayer.shieldAdd(2, 4)

                                targetPlayer.scoreboardTags.add("waterwizard_shield")
                                CLASSWAR.instance.server.scheduler.runTaskLater(
                                    CLASSWAR.instance, Runnable {
                                        targetPlayer.scoreboardTags.remove("waterwizard_shield")
                                    }, 60
                                )
                            }
                        }

                        player.location.world.playSound(
                            player.location, Sound.ITEM_BUCKET_EMPTY_FISH, 0.5f, 0.7f
                        )
                        spawnSphereParticles(
                            player.eyeLocation.toCenterLocation(), Particle.DRIP_WATER, 2.0
                        )
                        player.removeMana(40)
                        player.coolTime(10, 1, waterwizard.skill[0])

                    }
                }

                waterwizard.skill[1] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {
                        spawnProjectile(
                            player,
                            waterwizard.skill[1],
                            0.75,
                            2.0,
                            2.0,
                            2.0,
                            physics = true,
                            crash = true,
                            remove = false,
                            time = null,
                            head = true,
                            flat = true
                        )

                        player.location.world.playSound(
                            player.location, Sound.ENTITY_FISHING_BOBBER_SPLASH, 0.5f, 1.5f
                        )

                        player.inventory.setItem(2, null)
                        player.removeMana(100)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onMarkerHit(event: MarkerHitEvent) {
        val crashEntity = event.crashEntity
        val marker = event.marker
        if (event.skill == waterwizard.skill[1]) {
            if (crashEntity != null) {
                marker.pitch = crashEntity.pitch
                marker.yaw = crashEntity.yaw

                crashEntity.teleport(marker)
                crashEntity.addSlow(3, 45)
                crashEntity.location.world.playSound(
                    crashEntity.location, Sound.ENTITY_FISHING_BOBBER_SPLASH, 0.05f, 1.5f
                )
            }
        }
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        if (event.skill == waterwizard.skill[1]) {
            location.world.spawnParticle(
                Particle.DRIP_WATER, location, 100, 0.5, 0.5, 0.5, 0.1
            )
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagerEntity = event.entity

        if (isStarting) {
            if (damagerEntity.scoreboardTags.contains("waterwizard_shield") && damagerEntity is Player) {
                if (!damagerEntity.hasPotionEffect(PotionEffectType.ABSORPTION)) {
                    if (damagerEntity.isTeam("RedTeam")) {
                        for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                            if (!targetPlayer.isTeam("RedTeam")) {
                                if (targetPlayer.isRadius(damagerEntity.location, 3.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        targetPlayer.addSlow(3, 15)
                                        spawnParticleRadius(damagerEntity.location, Particle.DRIP_WATER, 3.0, 0.05)
                                    }
                                }
                            }
                        }
                    } else {
                        for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                            if (!targetPlayer.isTeam("BlueTeam")) {
                                if (targetPlayer.isRadius(damagerEntity.location, 3.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        targetPlayer.addSlow(3, 15)
                                        spawnParticleRadius(damagerEntity.location, Particle.DRIP_WATER, 3.0, 0.05)
                                    }
                                }
                            }
                        }
                    }
                }
                damagerEntity.scoreboardTags.remove("waterwizard_shield")
            }
        }
    }
}