package org.beobma.classwar.classlist

import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.GameManager.Companion.isGaming
import org.beobma.classwar.LOCALIZATION.Companion.bard
import org.beobma.classwar.LOCALIZATION.Companion.bardweapon
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Particle.Companion.spawnParticleRadius
import org.beobma.classwar.util.Particle.Companion.spawnSphereParticles
import org.beobma.classwar.util.Skill.Companion.addUntargetability
import org.beobma.classwar.util.Skill.Companion.healingHealth
import org.beobma.classwar.util.Skill.Companion.isRadius
import org.beobma.classwar.util.Skill.Companion.isSkillTarget
import org.beobma.classwar.util.Skill.Companion.isTeam
import org.beobma.classwar.util.Skill.Companion.isTraining
import org.beobma.classwar.util.Skill.Companion.speedIncrease
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class BARD : Listener {

    companion object {
        var bardRunnable: BukkitTask? = null
        var int: Int = 0

        var bardItemSlot3: ItemStack? = null
    }

    @EventHandler
    fun onGameStating(event: GameStartEvent) {
        bardRunnable = null
        int = 0
        bardItemSlot3 = null
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == bard.name) {
            when (skill) {
                bard.skill[0] -> { // 일반 스킬
                    player.scoreboardTags.add("bard_skill_0_using")
                    player.scoreboardTags.add("bard_skill_using")
                    int = 0
                    bardRunnable = null

                    bardItemSlot3 = player.inventory.getItem(3)

                    player.inventory.setItem(0, null)
                    player.inventory.setItem(1, null)
                    player.inventory.setItem(2, null)
                    player.inventory.setItem(3, null)

                    player.inventory.setItem(4, bardweapon)
                    player.inventory.heldItemSlot = 4

                    bardRunnable = object : BukkitRunnable() {
                        override fun run() {
                            int++
                            spawnParticleRadius(player.location, Particle.COMPOSTER, 5.0, 0.05)
                            player.speedIncrease(1, 20)
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isRadius(player.location, 5.0)) {
                                    if (targetPlayer.isSkillTarget()) {
                                        when {
                                            player.isTeam("RedTeam") -> {
                                                if (targetPlayer.isTeam("RedTeam")) {
                                                    targetPlayer.speedIncrease(1, 20)
                                                }
                                            }

                                            player.isTeam("BlueTeam") -> {
                                                if (targetPlayer.isTeam("BlueTeam")) {
                                                    targetPlayer.speedIncrease(1, 20)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            when (int) {
                                8 -> {
                                    if (player.scoreboardTags.contains("bard_skill_timing")) {
                                        player.inventory.setItem(4, null)

                                        player.inventory.setItem(0, bardweapon)
                                        player.inventory.setItem(2, bard.skill[1])
                                        player.inventory.setItem(3, bardItemSlot3)
                                        player.scoreboardTags.remove("bard_skill_timing")
                                        player.scoreboardTags.remove("bard_skill_using")
                                        player.scoreboardTags.remove("bard_skill_0_using")
                                        this.cancel()
                                    }
                                    int = 0
                                }

                                7 -> {

                                }

                                6 -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                    player.scoreboardTags.add("bard_skill_timing")
                                }

                                1 -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                    player.setCooldown(Material.GOAT_HORN, 48)
                                }

                                else -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 8L)
                }

                bard.skill[1] -> { // 보조 스킬
                    player.scoreboardTags.add("bard_skill_1_using")
                    player.scoreboardTags.add("bard_skill_using")
                    int = 0
                    bardRunnable = null


                    bardItemSlot3 = player.inventory.getItem(3)

                    player.inventory.setItem(0, null)
                    player.inventory.setItem(1, null)
                    player.inventory.setItem(2, null)
                    player.inventory.setItem(3, null)

                    player.inventory.setItem(4, bardweapon)
                    player.inventory.heldItemSlot = 4

                    bardRunnable = object : BukkitRunnable() {
                        override fun run() {
                            int++

                            when (int) {
                                8 -> {
                                    if (player.scoreboardTags.contains("bard_skill_timing")) {
                                        player.inventory.setItem(4, null)

                                        player.inventory.setItem(0, bardweapon)
                                        player.inventory.setItem(1, bard.skill[0])
                                        player.inventory.setItem(3, bardItemSlot3)
                                        player.scoreboardTags.remove("bard_skill_using")
                                        player.scoreboardTags.remove("bard_skill_timing")
                                        player.scoreboardTags.remove("bard_skill_1_using")
                                        this.cancel()
                                    }
                                    int = 0
                                }

                                7 -> {

                                }

                                6 -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                    player.scoreboardTags.add("bard_skill_timing")
                                }

                                1 -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                    player.setCooldown(Material.GOAT_HORN, 36)
                                }

                                else -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 6L)
                }

                bard.skill[2] -> { // 궁극 스킬
                    player.scoreboardTags.add("bard_skill_2_using")
                    player.scoreboardTags.add("bard_skill_using")
                    int = 0
                    bardItemSlot3 = null
                    bardRunnable = null

                    player.inventory.setItem(0, null)
                    player.inventory.setItem(1, null)
                    player.inventory.setItem(2, null)
                    player.inventory.setItem(3, null)

                    player.inventory.setItem(4, bardweapon)
                    player.inventory.heldItemSlot = 4

                    bardRunnable = object : BukkitRunnable() {
                        override fun run() {
                            int++

                            spawnSphereParticles(player.location, Particle.ASH, 5.0)
                            for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                                if (targetPlayer.isRadius(player.location, 5.0)) {
                                    when {
                                        player.isTeam("RedTeam") -> {
                                            if (targetPlayer.isTeam("RedTeam")) {
                                                targetPlayer.addUntargetability(1)
                                            }
                                        }

                                        player.isTeam("BlueTeam") -> {
                                            if (targetPlayer.isTeam("BlueTeam")) {
                                                targetPlayer.addUntargetability(1)
                                            }
                                        }
                                    }
                                }
                            }

                            when (int) {
                                8 -> {
                                    if (player.scoreboardTags.contains("bard_skill_timing")) {
                                        player.inventory.setItem(4, null)

                                        player.inventory.setItem(0, bardweapon)
                                        player.inventory.setItem(1, bard.skill[0])
                                        player.inventory.setItem(2, bard.skill[1])
                                        player.scoreboardTags.remove("bard_skill_using")
                                        player.scoreboardTags.remove("bard_skill_timing")
                                        player.scoreboardTags.remove("bard_skill_2_using")
                                        this.cancel()
                                    }
                                    int = 0
                                }

                                7 -> {

                                }

                                6 -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                    player.scoreboardTags.add("bard_skill_timing")
                                }

                                1 -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                    player.setCooldown(Material.GOAT_HORN, 24)
                                }

                                else -> {
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 1.0f)
                                }
                            }
                        }
                    }.runTaskTimer(CLASSWAR.instance, 0L, 4L)
                }
            }
        }
    }

    @EventHandler
    fun onItemHeldChange(event: PlayerItemHeldEvent) {
        val player = event.player

        if (!isGaming && !player.isTraining()) return
        if (!player.scoreboardTags.contains(bard.name)) return
        if (!player.scoreboardTags.contains("bard_skill_using")) return

        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player

        if (!isGaming && !player.isTraining()) return
        if (!player.scoreboardTags.contains(bard.name)) return
        if (!player.scoreboardTags.contains("bard_skill_using")) return


        val item = event.item

        if (item == null || item != bardweapon) return

        if (!event.action.name.contains("RIGHT")) return

        if (player.scoreboardTags.contains("bard_skill_timing")) {
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10.0f, 2.0f)
            player.scoreboardTags.remove("bard_skill_timing")
            player.setCooldown(Material.GOAT_HORN, 9999)
            if (player.scoreboardTags.contains("bard_skill_1_using")) {
                spawnParticleRadius(player.location, Particle.TOTEM, 5.0, 0.05)
                for (targetPlayer in Bukkit.getServer().onlinePlayers) {
                    if (targetPlayer.isRadius(player.location, 5.0)) {
                        if (targetPlayer.isSkillTarget()) {
                            when {
                                player.isTeam("RedTeam") -> {
                                    if (targetPlayer.isTeam("RedTeam")) {
                                        targetPlayer.healingHealth(2, bard.getSkillName(1), player)
                                    }
                                }

                                player.isTeam("BlueTeam") -> {
                                    if (targetPlayer.isTeam("BlueTeam")) {
                                        targetPlayer.healingHealth(2, bard.getSkillName(1), player)
                                    }
                                }
                            }
                        }
                    }
                }
                player.healingHealth(2, bard.getSkillName(1), player)
            }

        } else {
            bardRunnable?.cancel()
            bardRunnable = null
            player.inventory.setItem(4, null)

            player.inventory.setItem(0, bardweapon)

            when {
                player.scoreboardTags.contains("bard_skill_0_using") -> {
                    player.inventory.setItem(2, bard.skill[1])
                    player.inventory.setItem(3, bardItemSlot3)
                }

                player.scoreboardTags.contains("bard_skill_1_using") -> {
                    player.inventory.setItem(1, bard.skill[0])
                    player.inventory.setItem(3, bardItemSlot3)
                }

                player.scoreboardTags.contains("bard_skill_2_using") -> {
                    player.inventory.setItem(1, bard.skill[0])
                    player.inventory.setItem(2, bard.skill[1])
                }
            }

            player.setCooldown(Material.GOAT_HORN, 200)

            player.scoreboardTags.remove("bard_skill_timing")
            player.scoreboardTags.remove("bard_skill_using")
            player.scoreboardTags.remove("bard_skill_0_using")
            player.scoreboardTags.remove("bard_skill_1_using")
            player.scoreboardTags.remove("bard_skill_2_using")
        }
    }
}