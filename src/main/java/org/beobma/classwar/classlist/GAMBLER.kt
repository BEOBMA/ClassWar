package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.LOCALIZATION.Companion.gambler
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill
import org.beobma.classwar.util.Skill.Companion.cardDraw
import org.beobma.classwar.util.Skill.Companion.cardThrow
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable

class GAMBLER : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == gambler.name) {
            when (skill) {
                gambler.skill[0] -> { // 일반 스킬
                    player.cardThrow(1)
                    player.cardDraw(1)

                    player.location.world.playSound(player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f)


                    player.coolTime(5, 1, gambler.skill[0])
                }

                gambler.skill[1] -> { // 보조 스킬
                    player.cardThrow(2)
                    player.cardDraw(3)


                    player.location.world.playSound(player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f)
                    object : BukkitRunnable() {
                        override fun run() {
                            player.location.world.playSound(
                                player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                            )
                            object : BukkitRunnable() {
                                override fun run() {
                                    player.location.world.playSound(
                                        player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                                    )
                                }
                            }.runTaskLater(CLASSWAR.instance, 3L)
                        }
                    }.runTaskLater(CLASSWAR.instance, 3L)


                    player.coolTime(10, 2, gambler.skill[1])
                }

                gambler.skill[2] -> { // 궁극 스킬
                    Skill.cardDeckShuffle()

                    object : BukkitRunnable() {
                        override fun run() {
                            player.location.world.playSound(
                                player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                            )
                            object : BukkitRunnable() {
                                override fun run() {
                                    player.location.world.playSound(
                                        player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                                    )
                                    object : BukkitRunnable() {
                                        override fun run() {
                                            player.location.world.playSound(
                                                player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                                            )
                                            object : BukkitRunnable() {
                                                override fun run() {
                                                    player.location.world.playSound(
                                                        player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                                                    )
                                                    object : BukkitRunnable() {
                                                        override fun run() {
                                                            player.location.world.playSound(
                                                                player.location, Sound.ITEM_BOOK_PAGE_TURN, 10.0f, 2.0f
                                                            )
                                                        }
                                                    }.runTaskLater(CLASSWAR.instance, 3L)
                                                }
                                            }.runTaskLater(CLASSWAR.instance, 3L)
                                        }
                                    }.runTaskLater(CLASSWAR.instance, 3L)
                                }
                            }.runTaskLater(CLASSWAR.instance, 3L)
                        }
                    }.runTaskLater(CLASSWAR.instance, 3L)

                    player.inventory.setItem(3, null)
                }
            }
        }
    }
}