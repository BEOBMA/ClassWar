@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.LOCALIZATION.Companion.landwizard
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill.Companion.addVibratingExplosion
import org.beobma.classwar.util.Skill.Companion.addVibration
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.Skill.Companion.shieldAdd
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class LANDWIZARD : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == landwizard.name) {
            when (skill) {
                landwizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 20) {
                        player.sendManaInsufficient()
                    } else {
                        if (player.isTeam("RedTeam")) {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isTeam("BlueTeam")) {
                                    if (targetPlayer.isRadius(player.location, 5.0)) {
                                        if (targetPlayer.isSkillTarget()) {
                                            targetPlayer.damageHealth(
                                                2, landwizard.skill[0].itemMeta.displayName, player
                                            )
                                            targetPlayer.addVibration(2)
                                        }
                                    }
                                }
                            }
                        } else {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isTeam("RedTeam")) {
                                    if (targetPlayer.isRadius(player.location, 5.0)) {
                                        if (targetPlayer.isSkillTarget()) {
                                            targetPlayer.damageHealth(
                                                2, landwizard.skill[0].itemMeta.displayName, player
                                            )
                                            targetPlayer.addVibration(2)
                                        }
                                    }
                                }
                            }
                        }


                        player.location.world.playSound(
                            player.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.4f, 2.0f
                        )

                        player.location.world.spawnParticle(
                            Particle.BLOCK_CRACK,
                            player.location,
                            200,
                            2.5,
                            0.0,
                            2.5,
                            0.0,
                            Material.SANDSTONE.createBlockData()
                        )

                        player.removeMana(20)
                        player.coolTime(1, 1, landwizard.skill[0])
                    }
                }

                landwizard.skill[1] -> { // 보조 스킬
                    if (player.getMana() < 60) {
                        player.sendManaInsufficient()
                    } else {
                        player.shieldAdd(3, 4)
                        if (player.isTeam("RedTeam")) {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isTeam("BlueTeam")) {
                                    if (targetPlayer.isRadius(player.location, 5.0)) {
                                        if (targetPlayer.isSkillTarget()) {
                                            targetPlayer.addVibratingExplosion(player)
                                        }
                                    }
                                }
                            }
                        } else {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isTeam("RedTeam")) {
                                    if (targetPlayer.isRadius(player.location, 5.0)) {
                                        if (targetPlayer.isSkillTarget()) {
                                            targetPlayer.addVibratingExplosion(player)
                                        }
                                    }
                                }
                            }
                        }

                        player.location.world.playSound(
                            player.location, Sound.BLOCK_SLIME_BLOCK_STEP, 0.5f, 0.5f
                        )
                        player.location.world.spawnParticle(
                            Particle.BLOCK_CRACK,
                            player.location,
                            100,
                            1.0,
                            1.0,
                            1.0,
                            0.0,
                            Material.SANDSTONE.createBlockData()
                        )

                        player.removeMana(60)
                        player.coolTime(10, 2, landwizard.skill[1])
                    }
                }

                landwizard.skill[2] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {
                        if (player.isTeam("RedTeam")) {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isSkillTarget()) {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        targetPlayer.damageHealth(
                                            3, landwizard.skill[2].itemMeta.displayName, player
                                        )
                                        targetPlayer.addVibratingExplosion(player)
                                    }
                                }
                            }
                        } else {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isSkillTarget()) {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        targetPlayer.damageHealth(
                                            3, landwizard.skill[2].itemMeta.displayName, player
                                        )
                                        targetPlayer.addVibratingExplosion(player)
                                    }
                                }
                            }
                        }

                        player.location.world.playSound(
                            player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 0.5f
                        )
                        player.location.world.spawnParticle(
                            Particle.BLOCK_CRACK,
                            player.location,
                            1000,
                            10.0,
                            0.0,
                            10.0,
                            0.0,
                            Material.SANDSTONE.createBlockData()
                        )

                        player.removeMana(100)
                        player.inventory.setItem(3, null)
                    }
                }
            }
        }
    }
}