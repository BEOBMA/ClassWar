@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager
import org.beobma.classwar.LOCALIZATION.Companion.knight
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill.Companion.addBleeding
import org.beobma.classwar.util.Skill.Companion.addStun
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.isStun
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.shootLaserFromPlayer
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class KNIGHT : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == knight.name) {
            when (skill) {
                knight.skill[0] -> { // 일반 스킬
                    val shootResort = shootLaserFromPlayer(player, 1.0, false, 2.0)

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

                        shootResort.damageHealth(7, knight.getSkillName(0), player)
                        shootResort.addBleeding(3)

                        shootResort.location.world.playSound(
                            shootResort.location, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 1.2F
                        )
                        shootResort.eyeLocation.world.spawnParticle(
                            Particle.CRIT, shootResort.location, 10, 0.0, 0.0, 0.0, 0.0
                        )
                    }

                    player.coolTime(10, 1, knight.skill[0])
                }

                knight.skill[1] -> { // 보조 스킬
                    player.scoreboardTags.add("Parrying")

                    object : BukkitRunnable() {
                        override fun run() {
                            if (player.scoreboardTags.contains("Parrying")) {
                                player.scoreboardTags.remove("Parrying")
                                player.scoreboardTags.add("Parrying_fail")
                            }
                        }
                    }.runTaskLater(CLASSWAR.instance, 4L)

                    player.coolTime(20, 2, knight.skill[1])
                }

                knight.skill[2] -> { // 궁극 스킬
                    val shootResort = shootLaserFromPlayer(player, 1.0, false, 5.0)

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
                        object : BukkitRunnable() {
                            override fun run() {
                                val targetLocation = shootResort.location
                                val chargerLocation = player.location
                                val direction =
                                    targetLocation.toVector().subtract(chargerLocation.toVector()).normalize()
                                val distance = chargerLocation.distance(targetLocation)

                                player.velocity = player.velocity.add(direction.multiply(1.5))

                                if (distance <= 2.0) {
                                    if (shootResort.isStun()) {
                                        shootResort.damageHealth(20, knight.getSkillName(2), player)
                                        shootResort.addBleeding(6)
                                    } else {
                                        shootResort.damageHealth(10, knight.getSkillName(2), player)
                                    }
                                    player.velocity = Vector(0, 0, 0)
                                    this.cancel()
                                }
                            }
                        }.runTaskTimer(CLASSWAR.instance, 0L, 1L)
                    }


                    player.location.world.playSound(player.location, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.6f)

                    player.inventory.setItem(3, null)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.entity
        val damagerEntity = event.damager

        if (GameManager.isGaming) {
            if (damagerEntity.scoreboardTags.contains(knight.name) && damagedEntity is Player && damagerEntity is Player) {
                damagedEntity.addBleeding(1)
            }
            if (damagedEntity.scoreboardTags.contains(knight.name) && damagedEntity is Player && damagerEntity is Player) {
                if (damagedEntity.scoreboardTags.contains("Parrying")) {
                    event.isCancelled = true
                    damagerEntity.addStun(2)
                    damagedEntity.location.world.playSound(
                        damagedEntity.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1.0F, 1.0F
                    )
                    damagedEntity.eyeLocation.world.spawnParticle(
                        Particle.END_ROD, damagedEntity.location, 10, 0.0, 0.0, 0.0, 1.0
                    )
                    event.damager.sendMessage("${damagedEntity.name}이(가) 시전한 ${knight.getSkillName(1)}${ChatColor.RESET}에 의해 공격이 흘려졌습니다.")
                    damagedEntity.sendMessage("${event.damager.name}이(가) 시전한 공격을 흘려냈습니다.")
                    damagedEntity.scoreboardTags.remove("Parrying")
                } else if (damagedEntity.scoreboardTags.contains("Parrying_fail")) {
                    event.damage *= 2
                    damagedEntity.sendMessage("${event.damager.name}이(가) 시전한 공격을 흘려내는데 실패하여 2배의 피해를 입었습니다.")
                    damagedEntity.scoreboardTags.remove("Parrying_fail")
                }
            }
        }
    }
}