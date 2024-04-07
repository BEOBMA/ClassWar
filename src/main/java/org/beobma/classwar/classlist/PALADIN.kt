@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager
import org.beobma.classwar.LOCALIZATION.Companion.paladin
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.addBrilliance
import org.beobma.classwar.util.Skill.Companion.addStun
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.isBrilliance
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.scheduler.BukkitRunnable

class PALADIN : Listener {


    companion object {
        var paladin_int = 3
    }

    @EventHandler
    fun onGameStating(event: GameStartEvent) {
        paladin_int = 3
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == paladin.name) {
            when (skill) {
                paladin.skill[0] -> { // 일반 스킬
                    player.scoreboardTags.add("paladin_skill1_tag")

                    object : BukkitRunnable() {
                        override fun run() {
                            player.scoreboardTags.remove("paladin_skill1_tag")
                        }
                    }.runTaskLater(CLASSWAR.instance, 60L)

                    player.coolTime(10, 1, paladin.skill[0])
                }

                paladin.skill[1] -> { // 보조 스킬
                    val shootResort = Skill.shootLaserFromPlayer(player, 1.0, false, 3.0)

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

                        shootResort.damageHealth(10, paladin.getSkillName(1), player)

                        if (shootResort.health <= 20) {
                            if (shootResort.isBrilliance()) {
                                shootResort.addStun(2)
                            } else {
                                shootResort.addBrilliance(5)
                            }
                        }
                        //여기에 소리 추가하기
                    }

                    player.coolTime(10, 2, paladin.skill[1])
                }

                paladin.skill[2] -> { // 궁극 스킬
                    player.scoreboardTags.add("paladin_skill2_tag")

                    player.inventory.setItem(3, null)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.damager
        val damagerEntity = event.entity

        if (GameManager.isGaming) {
            if (damagerEntity.scoreboardTags.contains(paladin.name) && damagerEntity is Player) {
                if (damagedEntity.scoreboardTags.contains("paladin_tag") && damagedEntity is Player) {
                    damagedEntity.scoreboardTags.remove("paladin_tag")
                    event.damage += 2
                    //여기에 소리 추가하기
                }
                if (damagerEntity.scoreboardTags.contains("paladin_skill1_tag")) {
                    event.damage += 2
                    //여기에 소리 추가하기
                }
            } else if (damagedEntity.scoreboardTags.contains(paladin.name) && damagedEntity is Player) {
                damagerEntity.scoreboardTags.add("paladin_tag")

                if (damagedEntity.scoreboardTags.contains("paladin_skill2_tag")) {
                    if (paladin_int > 0) {
                        paladin_int - 1
                        event.damage /= 2
                        //여기에 소리 추가하기

                        if (paladin_int == 0) {
                            damagedEntity.scoreboardTags.remove("paladin_skill2_tag")
                        }
                    }
                }
            }
        }
    }
}