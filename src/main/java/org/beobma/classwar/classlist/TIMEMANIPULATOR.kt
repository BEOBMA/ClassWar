@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager
import org.beobma.classwar.LOCALIZATION.Companion.timemanipulator
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Particle.Companion.spawnSphereParticles
import org.beobma.classwar.util.Skill.Companion.addTime
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.healingHealth
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.removeTime
import org.beobma.classwar.util.Skill.Companion.shieldAdd
import org.beobma.classwar.util.Skill.Companion.time
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class TIMEMANIPULATOR : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == timemanipulator.name) {
            when (skill) {
                timemanipulator.skill[0] -> { // 일반 스킬
                    addTime(2)
                    player.shieldAdd(2, 4)

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, 0.5f, 2.0f
                    )
                    spawnSphereParticles(
                        player.eyeLocation.toCenterLocation(), Particle.ENCHANTMENT_TABLE, 2.0
                    )

                    player.coolTime(10, 1, timemanipulator.skill[0])
                }

                timemanipulator.skill[1] -> { // 보조 스킬
                    removeTime(3)
                    if (player.isTeam("RedTeam")) {
                        for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                            if (targetPlayer.isTeam("BlueTeam")) {
                                if (targetPlayer.isRadius(player.location, 5.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        targetPlayer.damageHealth(
                                            10, timemanipulator.skill[1].itemMeta.displayName, player
                                        )
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
                                            10, timemanipulator.skill[1].itemMeta.displayName, player
                                        )
                                    }
                                }
                            }
                        }
                    }

                    player.location.world.playSound(
                        player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5f, 2.0f
                    )
                    spawnParticleRadius(player.location, Particle.ENCHANTMENT_TABLE, 5.0, 0.05)

                    player.coolTime(10, 2, timemanipulator.skill[1])
                }

                timemanipulator.skill[2] -> { // 궁극 스킬
                    when (time) {
                        -5 -> {
                            player.damageHealth(9999, timemanipulator.skill[2].itemMeta.displayName, player)
                        }

                        in -4..-1 -> {
                            player.damageHealth(10, timemanipulator.skill[2].itemMeta.displayName, player)
                        }

                        in 1..4 -> {
                            if (player.isTeam("RedTeam")) {
                                for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        if (targetPlayer.isSkillTarget()) {
                                            targetPlayer.damageHealth(
                                                3, timemanipulator.skill[2].itemMeta.displayName, player
                                            )
                                        }
                                    }
                                }
                            } else {
                                for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        if (targetPlayer.isSkillTarget()) {
                                            targetPlayer.damageHealth(
                                                3, timemanipulator.skill[2].itemMeta.displayName, player
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        5 -> {
                            CLASSWAR.instance.server.scheduler.cancelTasks(CLASSWAR.instance)
                            val gameController = GameManager()
                            if (player.isTeam("RedTeam")) {
                                for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        targetPlayer.healingHealth(
                                            5, timemanipulator.skill[2].itemMeta.displayName, player
                                        )
                                    }
                                }
                            } else {
                                for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        targetPlayer.healingHealth(
                                            5, timemanipulator.skill[2].itemMeta.displayName, player
                                        )
                                    }
                                }
                            }
                            if (player.scoreboardTags.contains("training")) {
                                player.inventory.setItem(3, null)
                                return
                            } else {
                                Bukkit.getServer()
                                    .broadcastMessage("${player.name}이(가) 시전한 ${timemanipulator.skill[2].itemMeta.displayName}에 의해 게임이 초기화 되었습니다.")
                                gameController.gameStart()
                            }
                        }
                    }


                    //* 시각 효과 블록*/
                    player.location.world.playSound(
                        player.location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 10.0f, 2f
                    )
                    player.location.world.spawnParticle(
                        Particle.END_ROD, player.location, 500, 10.0, 0.0, 10.0, 0.2
                    )

                    //* 스킬 엔드 블록*/
                    player.inventory.setItem(3, null)

                }
            }
        }
    }
}