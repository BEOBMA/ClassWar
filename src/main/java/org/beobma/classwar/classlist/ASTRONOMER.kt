@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.astronomer
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.createParticlesInSphere
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addSlow
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.damageIncrease
import org.beobma.classwar.util.Skill.Companion.getMana
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeMana
import org.beobma.classwar.util.Skill.Companion.shootLaserFromBlock
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable

class ASTRONOMER : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == astronomer.name) {
            when (skill) {
                astronomer.skill[0] -> { // 일반 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false)

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

                    shootResort.location.world.spawnParticle(Particle.END_ROD, shootResort.location, 1, 0.0, 0.0, 0.0, 0.0)
                    val mana = player.getMana()
                    shootResort.damageHealth(6 + (mana / 20), astronomer.getSkillName(0), player)
                    player.removeMana(mana)

                    player.coolTime(15, 1, astronomer.skill[0])
                }

                astronomer.skill[1] -> { // 보조 스킬
                    val shootResolt = shootLaserFromBlock(player)
                    var i = 0
                    if (shootResolt == null) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 블럭이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }
                    val location1 = shootResolt.location
                    val location = Location(location1.world, location1.x, location1.y.plus(1), location1.z)
                    spawnParticleRadius(location, Particle.WAX_ON, 5.0, 4.0)

                    object : BukkitRunnable() {
                        override fun run() {
                            i++

                            if (i >= 80) {
                                this.cancel()
                            }



                            createParticlesInSphere(
                                location,
                                1.0,
                                location.world,
                                org.bukkit.Particle.BLOCK_MARKER,
                                50,
                                Material.BLACK_CONCRETE.createBlockData()
                            )


                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (player.isTeam("RedTeam")) {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        return
                                    }
                                } else if (player.isTeam("BlueTeam")) {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        return
                                    }
                                }

                                if (targetPlayer.isRadius(location, 5.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        val playerLocation = player.location
                                        val direction = location.clone().subtract(playerLocation).toVector().normalize().multiply(0.1)
                                        player.velocity = direction
                                        val mana = player.getMana()
                                        targetPlayer.damageHealth(2 + (mana / 20), astronomer.getSkillName(1), player, false,
                                            message = false
                                        )
                                        player.removeMana(mana)
                                    }
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 1L)

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.75f, 2f
                    )
                    player.coolTime(20, 2, astronomer.skill[1])
                }

                astronomer.skill[2] -> { // 궁극 스킬
                    var i = 0
                    val location = player.location.clone()
                    spawnParticleRadius(location, Particle.END_ROD, 8.0, 5.0)


                    object : BukkitRunnable() {
                        override fun run() {
                            i++

                            if (i >= 100) {
                                this.cancel()
                            }

                            createParticlesInSphere(location, 8.0, location.world, Particle.END_ROD, 5)

                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (player.isTeam("RedTeam")) {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        return
                                    }
                                } else if (player.isTeam("BlueTeam")) {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        return
                                    }
                                }

                                if (targetPlayer.isRadius(location, 8.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        targetPlayer.damageIncrease(1, 20)
                                        val mana = player.getMana()
                                        targetPlayer.damageHealth(2 + (mana / 20), astronomer.getSkillName(2), player, false,
                                            message = false
                                        )
                                        player.removeMana(mana)
                                        targetPlayer.addSlow(1, 30)
                                    }
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 1L)

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.75f, 0.5f
                    )
                    player.inventory.setItem(3, null)
                }
            }
        }
    }
}