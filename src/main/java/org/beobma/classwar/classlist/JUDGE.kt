package org.beobma.classwar.classlist

import org.beobma.classwar.GameManager
import org.beobma.classwar.GameManager.Companion.teams
import org.beobma.classwar.LOCALIZATION.Companion.judge
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.exile
import org.beobma.classwar.util.Skill.Companion.getTeam
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.isTraining
import org.beobma.classwar.util.Skill.Companion.shieldAdd
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class JUDGE : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == judge.name) {
            when (skill) {
                judge.skill[0] -> { // 일반 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false, 4.0)

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

                    if (player.getTeam() == "RedTeam") {
                        if (teams["RedTeam"]?.size!! > teams["BlueTeam"]?.size!!) {
                            shootResort.damageHealth(5, judge.getSkillName(0), player)
                        } else if (teams["RedTeam"]?.size!! == teams["BlueTeam"]?.size!!) {
                            shootResort.damageHealth(7, judge.getSkillName(0), player)
                        } else {
                            shootResort.damageHealth(10, judge.getSkillName(0), player)
                        }
                    } else {
                        if (teams["BlueTeam"]?.size!! > teams["RedTeam"]?.size!!) {
                            shootResort.damageHealth(5, judge.getSkillName(0), player)
                        } else if (teams["BlueTeam"]?.size!! == teams["RedTeam"]?.size!!) {
                            shootResort.damageHealth(7, judge.getSkillName(0), player)
                        } else {
                            shootResort.damageHealth(10, judge.getSkillName(0), player)
                        }
                    }

                    org.beobma.classwar.util.Particle.spawnFanShapeParticles(
                        player, Particle.END_ROD, 2.0, 100, 85.0
                    )
                    player.location.world.playSound(
                        player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 0.7f
                    )

                    player.coolTime(10, 1, judge.skill[0])
                }

                judge.skill[1] -> { // 보조 스킬
                    if (player.getTeam() == "RedTeam") {
                        if (teams["RedTeam"]?.size!! > teams["BlueTeam"]?.size!!) {
                            player.shieldAdd(3, 4)
                        } else if (teams["RedTeam"]?.size!! == teams["BlueTeam"]?.size!!) {
                            player.shieldAdd(3, 4)
                        } else {
                            player.shieldAdd(3, 8)
                        }
                    } else {
                        if (teams["BlueTeam"]?.size!! > teams["RedTeam"]?.size!!) {
                            player.shieldAdd(3, 4)
                        } else if (teams["BlueTeam"]?.size!! == teams["RedTeam"]?.size!!) {
                            player.shieldAdd(3, 4)
                        } else {
                            player.shieldAdd(3, 8)
                        }
                    }

                    org.beobma.classwar.util.Particle.spawnSphereParticles(
                        player.eyeLocation.toCenterLocation(), Particle.END_ROD, 2.0
                    )

                    player.location.world.playSound(
                        player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 0.7f
                    )

                    player.coolTime(10, 2, judge.skill[1])
                }

                judge.skill[2] -> { // 궁극 스킬
                    if (player.getTeam() == "RedTeam") {
                        if (teams["RedTeam"]?.size!! > teams["BlueTeam"]?.size!!) {
                            return
                        } else if (teams["RedTeam"]?.size!! == teams["BlueTeam"]?.size!!) {
                            return
                        } else {
                            val blueTeam = teams["BlueTeam"]?.players?.toMutableList()
                            repeat(teams["BlueTeam"]?.size!! - teams["RedTeam"]?.size!!) {
                                val players = blueTeam?.random()

                                if (players is Player) {
                                    blueTeam.remove(players)
                                    players.exile(5)
                                }
                            }
                        }
                    } else {
                        if (teams["BlueTeam"]?.size!! > teams["RedTeam"]?.size!!) {
                            return
                        } else if (teams["BlueTeam"]?.size!! == teams["RedTeam"]?.size!!) {
                            return
                        } else {
                            val redTeam = teams["RedTeam"]?.players?.toMutableList()
                            repeat(teams["RedTeam"]?.size!! - teams["BlueTeam"]?.size!!) {
                                val players = redTeam?.random()

                                if (players is Player) {
                                    redTeam.remove(players)
                                    players.exile(5)
                                }
                            }
                        }
                    }

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_BELL_USE, 1.0f, 1.5f
                    )

                    if (player.isTraining()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute positioned 47.5 -52.0 -43.5 run function base:particle/judge_skill_3_particle")
                    } else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute positioned 12.5 -52.0 -41.5 run function base:particle/judge_skill_3_particle")

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
            if (damagerEntity.scoreboardTags.contains(judge.name) && damagedEntity is Player && damagerEntity is Player) {
                if (damagerEntity.getTeam() == "RedTeam") {
                    if (teams["RedTeam"]?.size!! > teams["BlueTeam"]?.size!!) {
                        event.damage -= 4
                    } else if (teams["RedTeam"]?.size!! == teams["BlueTeam"]?.size!!) {
                        event.damage -= 2
                    } else {
                        event.damage += 2
                    }
                } else {
                    if (teams["BlueTeam"]?.size!! > teams["RedTeam"]?.size!!) {
                        event.damage -= 4
                    } else if (teams["BlueTeam"]?.size!! == teams["RedTeam"]?.size!!) {
                        event.damage -= 2
                    } else {
                        event.damage += 2
                    }
                }
                event.damage -= 1
            }
        }
    }
}