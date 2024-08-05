@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.assassin
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.invisibilityState
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.spawnProjectile
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class ASSASSIN : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == assassin.name) {
            when (skill) {
                assassin.skill[0] -> { // 일반 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false, 2.0)

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

                    player.location.world.playSound(
                        player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.75f, 0.75f
                    )

                    shootResort.damageHealth(6, assassin.getSkillName(0), player)

                    player.coolTime(10, 1, assassin.skill[0])
                }

                assassin.skill[1] -> { // 보조 스킬
                    spawnProjectile(
                        player, assassin.skill[1], 1.0, 0.5, 0.5, 0.5, physics = true, crash = true, remove = true
                    )



                    player.location.world.playSound(
                        player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.75f, 2f
                    )
                    player.coolTime(10, 2, assassin.skill[1])
                }

                assassin.skill[2] -> { // 궁극 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false, 2.0)

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

                    if (player.isInvisible) {
                        shootResort.damageHealth(20, assassin.getSkillName(0), player)
                    }
                    else {
                        shootResort.damageHealth(10, assassin.getSkillName(0), player)
                    }
                    if (shootResort.gameMode == GameMode.SPECTATOR) {
                        player.location.world.playSound(
                            player.location, Sound.ITEM_TRIDENT_THUNDER, 1.0f, 2.0f
                        )
                        return
                    }
                    else {
                        player.location.world.playSound(
                            player.location, Sound.ITEM_TRIDENT_HIT, 1.0f, 0.5f
                        )
                    }

                    player.inventory.setItem(3, null)
                }
            }
        }
    }

    @EventHandler
    fun onMarkerHit(event: MarkerHitEvent) {
        val player = event.player
        val entity = event.crashEntity
        val block = event.blockPosition
        if (event.skill == assassin.skill[1]) {
            if (entity != null) {
                object : BukkitRunnable() {
                    override fun run() {
                        val targetLocation = entity.location
                        val chargerLocation = player.location
                        val playerLocation = player.location
                        val direction = entity.location.clone().subtract(playerLocation).toVector().normalize().multiply(1.5)
                        player.velocity = direction
                        val distance = chargerLocation.distance(targetLocation)

                        if (distance <= 2.0) {
                            player.velocity = Vector(0, 0, 0)
                            this.cancel()
                        }
                    }
                }.runTaskTimer(CLASSWAR.instance, 0L, 1L)
                entity.damageHealth(6, assassin.getSkillName(0), event.player)
                entity.location.world.playSound(
                    entity.location,
                    Sound.ENTITY_ARROW_HIT_PLAYER,
                    0.2f,
                    1f
                )
            }
            if (block != null) {
                object : BukkitRunnable() {
                    override fun run() {
                        val chargerLocation = player.location
                        val playerLocation = player.location
                        val direction = block.clone().subtract(playerLocation).toVector().normalize().multiply(1.5)
                        player.velocity = direction
                        val distance = chargerLocation.distance(block)

                        if (distance <= 3.0) {
                            player.velocity = Vector(0, 0, 0)
                            this.cancel()
                        }
                    }
                }.runTaskTimer(CLASSWAR.instance, 0L, 1L)
                player.invisibilityState(3)
                player.location.world.playSound(
                    player.location, Sound.ITEM_TRIDENT_HIT_GROUND, 0.75f, 0.5f
                )
            }
        }
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        if (event.skill == assassin.skill[1]) {
            location.world.spawnParticle(
                Particle.END_ROD,
                location,
                1,
                0.0,
                0.0,
                0.0,
                0.0
            )
        }
    }
}