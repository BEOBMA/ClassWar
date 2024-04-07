@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.physicist
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.playRectangleBorderParticles
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class PHYSICIST : Listener {

    companion object {
        private var physicistTag: String = "particle"
    }

    @EventHandler
    fun onGameStartEvent(event: GameStartEvent) {
        physicistTag = "particle"
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        if (event.skill == physicist.skill[0]) {
            location.world.spawnParticle(
                Particle.SONIC_BOOM, location, 1, 0.0, 0.0, 0.0, 0.0
            )
        } else if (event.skill == physicist.skill[1]) {
            location.world.spawnParticle(
                Particle.SONIC_BOOM, location, 1, 0.0, 0.0, 0.0, 0.0
            )
            location.world.spawnParticle(
                Particle.WAX_OFF, location, 20, 0.3, 0.3, 0.3, 0.0
            )
        }
    }

    @EventHandler
    fun onMarkerHit(event: MarkerHitEvent) {
        val entity = event.crashEntity

        if (event.skill == physicist.skill[0]) {
            if (entity != null) {
                entity.damageHealth(Random.nextInt(0, 11), physicist.getSkillName(0), event.player)
                entity.location.world.playSound(
                    entity.location, Sound.ENTITY_WARDEN_SONIC_BOOM, 1.0f, 1f
                )
            }
        } else if (event.skill == physicist.skill[1]) {
            if (entity != null) {
                entity.damageHealth(Random.nextInt(0, 11), physicist.getSkillName(0), event.player)
                entity.location.world.playSound(
                    entity.location, Sound.ENTITY_WARDEN_SONIC_BOOM, 1.0f, 0.7f
                )
                for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                    if (targetPlayer.isRadius(entity.location, 3.0)) {
                        if (targetPlayer.isSkillTarget()) {
                            targetPlayer.damageHealth(Random.nextInt(0, 11), physicist.getSkillName(0), entity)
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == physicist.name) {
            when (skill) {
                physicist.skill[0] -> { // 일반 스킬

                    when (physicistTag) {
                        "particle" -> {
                            Skill.spawnProjectile(
                                player,
                                physicist.skill[0],
                                1.0,
                                1.0,
                                1.0,
                                1.0,
                                physics = true,
                                crash = true,
                                remove = true
                            )
                            player.location.world.playSound(player.location, Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 0.7f)
                            physicistTag = "wave"
                        }

                        "wave" -> {
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isRadius(player.location, 5.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        when {
                                            player.isTeam("RedTeam") -> {
                                                if (targetPlayer.isTeam("BlueTeam")) {
                                                    targetPlayer.damageHealth(
                                                        Random.nextInt(0, 11), physicist.getSkillName(1), player
                                                    )
                                                }
                                            }

                                            player.isTeam("BlueTeam") -> {
                                                if (targetPlayer.isTeam("RedTeam")) {
                                                    targetPlayer.damageHealth(
                                                        Random.nextInt(0, 11), physicist.getSkillName(1), player
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            spawnParticleRadius(player.location, Particle.SONIC_BOOM, 5.0, 0.05)
                            player.location.world.playSound(player.location, Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.2f)
                            physicistTag = "particle"
                        }

                        "integrated" -> {
                            Skill.spawnProjectile(
                                player,
                                physicist.skill[1],
                                1.0,
                                1.5,
                                1.0,
                                1.5,
                                physics = true,
                                crash = true,
                                remove = true
                            )
                            player.location.world.playSound(player.location, Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 2.0f)
                        }
                    }

                    if (physicistTag == "integrated") {
                        player.coolTime(5, 1, physicist.skill[0])
                        return
                    }
                    player.coolTime(10, 1, physicist.skill[0])
                }

                physicist.skill[1] -> { // 보조 스킬
                    physicistTag = "integrated"

                    object : BukkitRunnable() {
                        override fun run() {
                            physicistTag = "particle"
                        }
                    }.runTaskLater(CLASSWAR.instance, (Random.nextInt(1, 11) * 20).toLong())

                    player.location.world.playSound(player.location, Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f)
                    player.coolTime(10, 2, physicist.skill[1])
                }

                physicist.skill[2] -> { // 궁극 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort == null) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    if (shootResort is Player) {
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

                        val loc1x = shootResort.location.x - 1
                        val loc1y = shootResort.location.y - 1
                        val loc1z = shootResort.location.z - 1
                        val loc2x = shootResort.location.x + 1
                        val loc2y = shootResort.location.y + 1
                        val loc2z = shootResort.location.z + 1

                        playRectangleBorderParticles(
                            Location(shootResort.world, loc1x, loc1y, loc1z),
                            Location(shootResort.world, loc2x, loc2y, loc2z),
                            Particle.END_ROD,
                            shootResort.world
                        )
                        shootResort.world.playSound(shootResort.location, Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f)
                        shootResort.damageHealth(Random.nextInt(0, 41), physicist.getSkillName(2), player)
                    }

                    player.inventory.setItem(3, null)
                }
            }
        }
    }
}