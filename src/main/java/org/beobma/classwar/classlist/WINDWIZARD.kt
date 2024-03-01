package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.windwizard
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addLevitation
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.Skill.Companion.speedIncrease
import org.beobma.classwar.util.SkillFaillMessage.sendManaInsufficient
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class WINDWIZARD : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == windwizard.name) {
            when (skill) {
                windwizard.skill[0] -> { // 일반 스킬
                    if (player.getMana() < 40) {
                        player.sendManaInsufficient()
                    } else {
                        val pos = player.location

                        pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.3f, 1.0f)

                        var ticksElapsed = 0
                        CLASSWAR.instance.server.scheduler.runTaskTimer(CLASSWAR.instance, Runnable {
                            if (ticksElapsed >= 100) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                return@Runnable
                            } else if (ticksElapsed == 75) {
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.2f, 1.0f)
                            } else if (ticksElapsed == 80) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.15f, 1.0f)
                            } else if (ticksElapsed == 85) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.1f, 1.0f)
                            } else if (ticksElapsed == 90) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.075f, 1.0f)
                            } else if (ticksElapsed == 95) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.05f, 1.0f)
                            } else if (ticksElapsed == 96) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.04f, 1.0f)
                            } else if (ticksElapsed == 97) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.03f, 1.0f)
                            } else if (ticksElapsed == 98) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.02f, 1.0f)
                            } else if (ticksElapsed == 99) {
                                player.world.players.forEach { player ->
                                    player.stopSound(Sound.ITEM_ELYTRA_FLYING)
                                }
                                pos.world.playSound(pos, Sound.ITEM_ELYTRA_FLYING, 0.01f, 1.0f)
                            }
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(-1.0, 0.0, -1.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(1.0, 0.0, 0.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(1.0, 0.0, 0.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(0.0, 0.0, 1.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(-1.0, 0.0, 0.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(-1.0, 0.0, 0.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(0.0, 0.0, 1.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(1.0, 0.0, 0.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.spawnParticle(
                                Particle.CLOUD, pos.add(1.0, 0.0, 0.0), 0, 0.0, 1.0, 0.0, 0.5
                            )
                            pos.world.getNearbyEntities(pos.add(-1.0, 0.0, -1.0), 2.0, 5.0, 2.0)
                                .filterIsInstance<Player>().forEach { players ->
                                    if (players.isSkillTarget()) {
                                        players.addLevitation(1)
                                    }
                                }
                            ticksElapsed++
                        }, 0L, 1L)

                        player.removeMana(40)
                        player.coolTime(10, 1, windwizard.skill[0])
                    }
                }

                windwizard.skill[1] -> { // 보조 스킬
                    if (player.getMana() < 60) {
                        player.sendManaInsufficient()
                    } else {
                        Skill.spawnProjectile(
                            player,
                            windwizard.skill[1],
                            0.05,
                            1.0,
                            2.0,
                            1.0,
                            physics = false,
                            crash = true,
                            remove = false,
                            3,
                            false
                        )
                        player.removeMana(60)
                        player.coolTime(10, 2, windwizard.skill[1])
                    }

                }

                windwizard.skill[2] -> { // 궁극 스킬
                    if (player.getMana() < 100) {
                        player.sendManaInsufficient()
                    } else {
                        if (player.isTeam("RedTeam")) {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isTeam("RedTeam")) {
                                    targetPlayer.speedIncrease(5, 40)
                                }
                            }
                        } else {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isTeam("BlueTeam")) {
                                    targetPlayer.speedIncrease(5, 40)
                                }
                            }
                        }

                        player.location.world.playSound(
                            player.location, Sound.ITEM_ELYTRA_FLYING, 0.3f, 2f
                        )

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
        val marker = event.marker
        if (event.skill == windwizard.skill[1]) {
            if (crashEntity != null) {
                marker.pitch = crashEntity.pitch
                marker.yaw = crashEntity.yaw

                crashEntity.teleport(marker)
            }
        }
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        if (event.skill == windwizard.skill[1]) {
            location.world.spawnParticle(
                Particle.CLOUD, location, 10, 0.0, 0.5, 0.0, 0.1
            )
        }
    }
}