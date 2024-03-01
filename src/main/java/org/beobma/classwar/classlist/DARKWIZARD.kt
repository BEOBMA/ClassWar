@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager.Companion.gamingPlayer
import org.beobma.classwar.LOCALIZATION.Companion.darkwizard
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.createParticlesInSphere
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addAbyss
import org.beobma.classwar.util.Skill.Companion.addFear
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class DARKWIZARD : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == darkwizard.name) {
            when (skill) {
                darkwizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 40) {
                        player.sendManaInsufficient()
                    } else {
                        val location = player.location

                        var int = 0
                        object : BukkitRunnable() {
                            override fun run() {
                                if (int >= 60) {
                                    this.cancel()
                                }

                                createParticlesInSphere(
                                    location,
                                    2.5,
                                    location.world,
                                    org.bukkit.Particle.BLOCK_MARKER,
                                    500,
                                    Material.BLACK_CONCRETE.createBlockData()
                                )

                                gamingPlayer?.forEach {
                                    if (it.isRadius(location, 2.5)) {
                                        if (it.isSkillTarget()) {
                                            if (player.isTeam("RedTeam")) {
                                                if (it.isTeam("BlueTeam")) {
                                                    it.addPotionEffect(
                                                        PotionEffect(
                                                            PotionEffectType.GLOWING, 20, 0, false, false, true
                                                        )
                                                    )
                                                }
                                            } else {
                                                if (it.isTeam("RedTeam")) {
                                                    it.addPotionEffect(
                                                        PotionEffect(
                                                            PotionEffectType.GLOWING, 20, 0, false, false, true
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }




                                int++
                            }
                        }.runTaskTimer(CLASSWAR.instance, 0L, 1L)


                        player.removeMana(40)
                        player.coolTime(15, 1, darkwizard.skill[0])
                    }
                }

                darkwizard.skill[1] -> { // 보조 스킬
                    if (player.getMana() < 60) {
                        player.sendManaInsufficient()
                    } else {
                        Skill.spawnProjectile(
                            player,
                            darkwizard.skill[1],
                            1.0,
                            1.5,
                            1.5,
                            1.5,
                            physics = false,
                            crash = true,
                            remove = false,
                            time = 6
                        )

                        player.location.world.playSound(
                            player.location, Sound.ENTITY_ENDERMAN_HURT, 0.5f, 0.5f
                        )
                        player.removeMana(60)
                        player.coolTime(13, 2, darkwizard.skill[1])
                    }
                }

                darkwizard.skill[2] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {
                        gamingPlayer?.forEach {
                            if (it.scoreboardTags.contains("one_abyss")) {
                                if (it.isSkillTarget()) {
                                    it.addFear(3)
                                }
                            }

                            it.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.BLINDNESS, 100, 0, false, false, true
                                )
                            )
                            createParticlesInSphere(
                                it.location,
                                2.5,
                                it.location.world,
                                org.bukkit.Particle.BLOCK_MARKER,
                                50,
                                Material.BLACK_CONCRETE.createBlockData()
                            )
                        }

                        player.world.playSound(player.location, Sound.ENTITY_WITHER_AMBIENT, 99.0F, 0.5F)
                        player.removeMana(100)
                        player.inventory.setItem(3, null)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onMarkerHit(event: MarkerHitEvent) {
        val crashEntity = event.crashEntity

        if (event.skill == darkwizard.skill[1]) {
            if (crashEntity != null) {

                crashEntity.addAbyss(2)
                crashEntity.damageHealth(10, darkwizard.getSkillName(1), event.player)
                crashEntity.location.world.playSound(
                    crashEntity.location, Sound.ENTITY_WITHER_HURT, 0.5f, 1.5f
                )
            }
        }
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        if (event.skill == darkwizard.skill[1]) {
            location.world.spawnParticle(
                Particle.BLOCK_MARKER, location, 30, 0.5, 0.5, 0.5, 0.1, Material.BLACK_CONCRETE.createBlockData()
            )
        }
    }
}