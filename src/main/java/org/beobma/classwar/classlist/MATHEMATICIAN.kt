@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.mathematician
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.playParticlesAtBlockCorners
import org.beobma.classwar.util.Particle.Companion.playRectangleBorderParticles
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.calculateVolumeBetweenLocations
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.doRectanglesOverlap
import org.beobma.classwar.util.Skill.Companion.getPlayersInsideRectangle
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class MATHEMATICIAN : Listener {

    var marker: MutableList<Location> = mutableListOf()
    var cuboid: MutableList<MutableList<Location>> = mutableListOf()
    private var task: BukkitTask? = null

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        task = null
        marker = mutableListOf()
        cuboid = mutableListOf()
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == mathematician.name) {
            when (skill) {
                mathematician.skill[0] -> { // 일반 스킬
                    val shootResolt = Skill.shootLaserFromBlock(player)

                    if (shootResolt == null) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 바라보는 블럭이 존재하지 않아 스킬을 발동할 수 없습니다.")
                        return
                    }

                    if (marker.size == 1) {
                        if (calculateVolumeBetweenLocations(
                                marker.first(), shootResolt.location.add(0.5, 0.5, 0.5)
                            ) <= 125
                        ) {
                            marker.add(shootResolt.location.add(0.5, 0.5, 0.5))
                        } else {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 부피가 125 이상이기 때문에 좌표축을 설정할 수 없습니다.")
                            return
                        }
                    } else if (marker.size == 2) {
                        if (calculateVolumeBetweenLocations(
                                marker.last(), shootResolt.location.add(0.5, 0.5, 0.5)
                            ) <= 125
                        ) {
                            marker.removeFirst()
                            marker.add(shootResolt.location.add(0.5, 0.5, 0.5))
                        } else {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 부피가 125 이상이기 때문에 좌표축을 설정할 수 없습니다.")
                            return
                        }
                    } else {
                        marker.add(shootResolt.location.add(0.5, 0.5, 0.5))
                    }
                    task?.cancel()

                    task = object : BukkitRunnable() {
                        override fun run() {
                            if (marker.size == 1) {
                                playParticlesAtBlockCorners(player.location.world, marker.first(), Particle.END_ROD)
                            } else {
                                playRectangleBorderParticles(
                                    marker.first(), marker.last(), Particle.END_ROD, player.location.world
                                )
                            }

                            if (cuboid.size != 0) {
                                cuboid.forEach {
                                    playRectangleBorderParticles(
                                        it.first(), it.last(), Particle.END_ROD, player.location.world
                                    )
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 20L)

                    player.location.world.playSound(
                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                    )
                }

                mathematician.skill[1] -> { // 보조 스킬
                    if (marker.size != 2) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 좌표축이 설정되지 않았습니다.")
                        return
                    }

                    cuboid.forEach {
                        if (doRectanglesOverlap(marker.first(), marker.last(), it.first(), it.last())) {
                            player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 생성된 직육면체가 다른 직육면체와 충돌하여 두 직육면체가 소멸되었습니다.")
                            marker = mutableListOf()
                            cuboid.remove(it)
                            task?.cancel()

                            task = object : BukkitRunnable() {
                                override fun run() {
                                    if (marker.size == 1) {
                                        playParticlesAtBlockCorners(
                                            player.location.world, marker.first(), Particle.END_ROD
                                        )
                                    } else if (marker.size == 2) {
                                        playRectangleBorderParticles(
                                            marker.first(), marker.last(), Particle.END_ROD, player.location.world
                                        )
                                    }

                                    if (cuboid.size != 0) {
                                        cuboid.forEach { it1 ->
                                            playRectangleBorderParticles(
                                                it1.first(), it1.last(), Particle.END_ROD, player.location.world
                                            )
                                        }
                                    }
                                }
                            }.runTaskTimer(CLASSWAR.instance, 0L, 20L)
                            player.coolTime(10, 2, mathematician.skill[1])
                            return
                        }
                    }

                    val damage = calculateVolumeBetweenLocations(marker.first(), marker.last()) / 5
                    getPlayersInsideRectangle(marker.first(), marker.last(), player.location.world).forEach {
                        if (player.isTeam("RedTeam")) {
                            if (it.isSkillTarget() && it.isTeam("BlueTeam")) {
                                it.damageHealth(damage, mathematician.getSkillName(1), player)
                            }
                        } else {
                            if (it.isSkillTarget() && it.isTeam("RedTeam")) {
                                it.damageHealth(damage, mathematician.getSkillName(1), player)
                            }
                        }
                    }

                    cuboid.add(mutableListOf(marker.first(), marker.last()))
                    marker = mutableListOf()

                    task?.cancel()

                    task = object : BukkitRunnable() {
                        override fun run() {
                            if (marker.size == 1) {
                                playParticlesAtBlockCorners(player.location.world, marker.first(), Particle.END_ROD)
                            } else if (marker.size == 2) {
                                playRectangleBorderParticles(
                                    marker.first(), marker.last(), Particle.END_ROD, player.location.world
                                )
                            }

                            if (cuboid.size != 0) {
                                cuboid.forEach {
                                    playRectangleBorderParticles(
                                        it.first(), it.last(), Particle.END_ROD, player.location.world
                                    )
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 20L)

                    object : BukkitRunnable() {
                        override fun run() {
                            player.location.world.playSound(
                                player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                            )
                            object : BukkitRunnable() {
                                override fun run() {
                                    player.location.world.playSound(
                                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                    )
                                    object : BukkitRunnable() {
                                        override fun run() {
                                            player.location.world.playSound(
                                                player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                            )
                                            object : BukkitRunnable() {
                                                override fun run() {
                                                    player.location.world.playSound(
                                                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                                    )
                                                    object : BukkitRunnable() {
                                                        override fun run() {
                                                            player.location.world.playSound(
                                                                player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                                            )
                                                            object : BukkitRunnable() {
                                                                override fun run() {
                                                                    player.location.world.playSound(
                                                                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                                                    )
                                                                    object : BukkitRunnable() {
                                                                        override fun run() {
                                                                            player.location.world.playSound(
                                                                                player.location,
                                                                                Sound.ITEM_BOOK_PUT,
                                                                                0.5f,
                                                                                1.5f
                                                                            )
                                                                            object : BukkitRunnable() {
                                                                                override fun run() {
                                                                                    player.location.world.playSound(
                                                                                        player.location,
                                                                                        Sound.ITEM_BOOK_PUT,
                                                                                        0.5f,
                                                                                        1.5f
                                                                                    )
                                                                                }
                                                                            }.runTaskLater(CLASSWAR.instance, 5L)
                                                                        }
                                                                    }.runTaskLater(CLASSWAR.instance, 5L)
                                                                }
                                                            }.runTaskLater(CLASSWAR.instance, 5L)
                                                        }
                                                    }.runTaskLater(CLASSWAR.instance, 5L)
                                                }
                                            }.runTaskLater(CLASSWAR.instance, 5L)
                                        }
                                    }.runTaskLater(CLASSWAR.instance, 5L)
                                }
                            }.runTaskLater(CLASSWAR.instance, 5L)
                        }
                    }.runTaskLater(CLASSWAR.instance, 5L)

                    player.coolTime(10, 2, mathematician.skill[1])
                }

                mathematician.skill[2] -> { // 궁극 스킬
                    if (cuboid.size == 0) {
                        player.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] 직육면체가 존재하지 않아 스킬을 사용할 수 없습니다.")
                        return
                    }

                    val damage = cuboid.size * 3

                    cuboid.forEach {
                        getPlayersInsideRectangle(it.first(), it.last(), player.location.world).forEach { it1 ->
                            if (player.isTeam("RedTeam")) {
                                if (it1.isSkillTarget() && it1.isTeam("BlueTeam")) {
                                    it1.damageHealth(damage, mathematician.getSkillName(2), player)
                                }
                            } else {
                                if (it1.isSkillTarget() && it1.isTeam("RedTeam")) {
                                    it1.damageHealth(damage, mathematician.getSkillName(2), player)
                                }
                            }
                        }
                    }

                    object : BukkitRunnable() {
                        override fun run() {
                            player.location.world.playSound(
                                player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                            )
                            object : BukkitRunnable() {
                                override fun run() {
                                    player.location.world.playSound(
                                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                    )
                                    object : BukkitRunnable() {
                                        override fun run() {
                                            player.location.world.playSound(
                                                player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                            )
                                            object : BukkitRunnable() {
                                                override fun run() {
                                                    player.location.world.playSound(
                                                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                                    )
                                                    object : BukkitRunnable() {
                                                        override fun run() {
                                                            player.location.world.playSound(
                                                                player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                                            )
                                                            object : BukkitRunnable() {
                                                                override fun run() {
                                                                    player.location.world.playSound(
                                                                        player.location, Sound.ITEM_BOOK_PUT, 0.5f, 1.5f
                                                                    )
                                                                    object : BukkitRunnable() {
                                                                        override fun run() {
                                                                            player.location.world.playSound(
                                                                                player.location,
                                                                                Sound.ITEM_BOOK_PUT,
                                                                                0.5f,
                                                                                1.5f
                                                                            )
                                                                            object : BukkitRunnable() {
                                                                                override fun run() {
                                                                                    player.location.world.playSound(
                                                                                        player.location,
                                                                                        Sound.ITEM_BOOK_PUT,
                                                                                        0.5f,
                                                                                        1.5f
                                                                                    )
                                                                                }
                                                                            }.runTaskLater(CLASSWAR.instance, 5L)
                                                                        }
                                                                    }.runTaskLater(CLASSWAR.instance, 5L)
                                                                }
                                                            }.runTaskLater(CLASSWAR.instance, 5L)
                                                        }
                                                    }.runTaskLater(CLASSWAR.instance, 5L)
                                                }
                                            }.runTaskLater(CLASSWAR.instance, 5L)
                                        }
                                    }.runTaskLater(CLASSWAR.instance, 5L)
                                }
                            }.runTaskLater(CLASSWAR.instance, 5L)
                        }
                    }.runTaskLater(CLASSWAR.instance, 5L)
                    cuboid = mutableListOf()
                    task?.cancel()

                    player.inventory.setItem(3, null)
                }
            }
        }
    }
}