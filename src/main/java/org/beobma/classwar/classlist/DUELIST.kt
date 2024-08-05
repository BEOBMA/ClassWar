@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager.Companion.isGaming
import org.beobma.classwar.LOCALIZATION.Companion.duelist
import org.beobma.classwar.event.DamageEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.spawnStraightParticles
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.scheduler.BukkitRunnable

class DUELIST : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == duelist.name) {
            when (skill) {
                duelist.skill[0] -> { // 일반 스킬 1
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, true, 2.0)

                    if (shootResort == null) {
                        player.coolTime(10, 1, duelist.skill[0])
                        spawnStraightParticles(player, Particle.END_ROD, 2)
                        player.scoreboardTags.add("duelist_damaged")
                        return
                    }

                    if (shootResort is Player) {
                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.coolTime(10, 1, duelist.skill[0])
                                spawnStraightParticles(player, Particle.END_ROD, 2)
                                player.scoreboardTags.add("duelist_damaged")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.coolTime(10, 1, duelist.skill[0])
                                spawnStraightParticles(player, Particle.END_ROD, 2)
                                player.scoreboardTags.add("duelist_damaged")
                                return
                            }
                        }

                        shootResort.damageHealth(5, duelist.getSkillName(0), player)

                        shootResort.location.world.playSound(
                            shootResort.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.5F
                        )
                        spawnStraightParticles(player, Particle.END_ROD, 2)

                        player.inventory.setItem(1, duelist.skill[1])
                    }
                }

                duelist.skill[1] -> { // 일반 스킬 2
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, true, 2.0)

                    if (shootResort == null) {
                        player.coolTime(10, 1, duelist.skill[0])
                        spawnStraightParticles(player, Particle.END_ROD, 2)
                        player.scoreboardTags.add("duelist_damaged")
                        return
                    }

                    if (shootResort is Player) {
                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.coolTime(10, 1, duelist.skill[0])
                                spawnStraightParticles(player, Particle.END_ROD, 2)
                                player.scoreboardTags.add("duelist_damaged")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.coolTime(10, 1, duelist.skill[0])
                                spawnStraightParticles(player, Particle.END_ROD, 2)
                                player.scoreboardTags.add("duelist_damaged")
                                return
                            }
                        }

                        shootResort.damageHealth(5, duelist.getSkillName(0), player)

                        shootResort.location.world.playSound(
                            shootResort.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.7F
                        )
                        spawnStraightParticles(player, Particle.END_ROD, 2)
                        player.inventory.setItem(1, duelist.skill[2])
                    }
                }

                duelist.skill[2] -> { // 일반 스킬 3
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, true, 2.0)

                    if (shootResort == null) {
                        player.coolTime(10, 1, duelist.skill[0])
                        spawnStraightParticles(player, Particle.END_ROD, 2)
                        player.scoreboardTags.add("duelist_damaged")
                        return
                    }

                    if (shootResort is Player) {
                        if (player.isTeam("RedTeam")) {
                            if (shootResort.isTeam("RedTeam")) {
                                player.coolTime(10, 1, duelist.skill[0])
                                spawnStraightParticles(player, Particle.END_ROD, 2)
                                player.scoreboardTags.add("duelist_damaged")
                                return
                            }
                        } else if (player.isTeam("BlueTeam")) {
                            if (shootResort.isTeam("BlueTeam")) {
                                player.coolTime(10, 1, duelist.skill[0])
                                spawnStraightParticles(player, Particle.END_ROD, 2)
                                player.scoreboardTags.add("duelist_damaged")
                                return
                            }
                        }

                        shootResort.damageHealth(7, duelist.getSkillName(0), player)

                        shootResort.location.world.playSound(
                            shootResort.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 2.0F
                        )
                        spawnStraightParticles(player, Particle.END_ROD, 2)

                        player.coolTime(10, 1, duelist.skill[0])
                    }
                }

                duelist.skill[3] -> { // 보조 스킬
                    val startLocation = player.eyeLocation
                    val direction = startLocation.direction

                    player.velocity = player.velocity.add(direction.multiply(1.5))

                    player.inventory.heldItemSlot = 1

                    player.coolTime(10, 2, duelist.skill[3])
                }

                duelist.skill[4] -> { // 궁극 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

                    if (shootResort == null || shootResort !is Player) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    if (player.isTeam("RedTeam") && shootResort.isTeam("RedTeam")) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    } else if (player.isTeam("BlueTeam") && shootResort.isTeam("BlueTeam")) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 대상이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    shootResort.scoreboardTags.add("duelist_enemy")
                    shootResort.sendTitle("${net.md_5.bungee.api.ChatColor.YELLOW}${net.md_5.bungee.api.ChatColor.BOLD}결투", player.name, 0, 200, 0)
                    player.scoreboardTags.add("duelist_my")
                    player.sendTitle("${net.md_5.bungee.api.ChatColor.YELLOW}${net.md_5.bungee.api.ChatColor.BOLD}결투", shootResort.name, 0, 200, 0)

                    object : BukkitRunnable() {
                        override fun run() {
                            shootResort.scoreboardTags.remove("duelist_enemy")
                            player.scoreboardTags.remove("duelist_my")
                        }
                    }.runTaskLater(CLASSWAR.instance, 200L)

                    player.inventory.setItem(3, null)
                }
            }
        }
    }

    @EventHandler
    fun onSkillDamage(event: DamageEvent) {
        val attacker = event.attacker
        val target = event.target
        val damage = event.damage

        if (!isGaming) return
        if (attacker.scoreboardTags.contains("duelist_my") && target.scoreboardTags.contains("duelist_enemy")) {
            event.damage *= 1.5
        } else if (attacker.scoreboardTags.contains("duelist_enemy") && target.scoreboardTags.contains("duelist_my")) {
            event.damage *= 1.5
        } else if (target.scoreboardTags.contains("duelist_my") && !attacker.scoreboardTags.contains("duelist_enemy")) {
            event.damage /= 1.5
        } else if (target.scoreboardTags.contains("duelist_enemy") && !attacker.scoreboardTags.contains("duelist_my")) {
            event.damage /= 1.5
        }

        if (target.scoreboardTags.contains("duelist_damaged")) {
            event.damage *= 1.25
            target.scoreboardTags.remove("duelist_damaged")
        }
    }
    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagerEntity = event.damager
        val damagedEntity = event.entity

        if (!isGaming) return
        if (damagerEntity.scoreboardTags.contains("duelist_my") || damagedEntity.scoreboardTags.contains("duelist_enemy")) {
            event.damage *= 1.5
        } else if (damagerEntity.scoreboardTags.contains("duelist_my") && !damagedEntity.scoreboardTags.contains("duelist_enemy")) {
            event.damage /= 1.5
        } else if (!damagerEntity.scoreboardTags.contains("duelist_my") && damagedEntity.scoreboardTags.contains("duelist_enemy")) {
            event.damage /= 1.5
        }

        if (damagedEntity.scoreboardTags.contains("duelist_damaged")) {
            event.damage *= 1.5
            damagedEntity.scoreboardTags.remove("duelist_damaged")
        }
    }
}