@file:Suppress("DEPRECATION")

package org.beobma.classwar.util

import net.md_5.bungee.api.ChatColor
import org.beobma.classwar.CLASSWAR
import org.beobma.classwar.Class
import org.beobma.classwar.GameManager
import org.beobma.classwar.GameManager.Companion.isStarting
import org.beobma.classwar.GameManager.Companion.onlinePlayers
import org.beobma.classwar.LOCALIZATION.Companion.gambler
import org.beobma.classwar.LOCALIZATION.Companion.warlock
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.Type
import org.bukkit.*
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffect.INFINITE_DURATION
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.nextInt

class Skill : Listener {

    companion object {

        /**
         * 일정 시간동안 특정 플레이어의 공격 속도를 증가시킵니다.
         * @param duration 효과의 지속시간을 나타냅니다. (단위 초)
         * @param amplifier 증가시 효과의 위력을 나타냅니다 (단위 20%의 배수)*/
        fun Player.attackSpeed(duration: Int, amplifier: Int) {
            val ticks = secondsToTicks(duration)
            val strength = (amplifier / 10) - 1
            if (player!!.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                val playerPotionAmplifier = player!!.getPotionEffect(PotionEffectType.FAST_DIGGING)?.amplifier
                val playerPotionDuration = player!!.getPotionEffect(PotionEffectType.FAST_DIGGING)?.duration
                val reStrength = playerPotionAmplifier!! + strength
                val reTicks = playerPotionDuration!! + ticks
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.FAST_DIGGING, reTicks, reStrength, false, false, true
                    )
                )
            } else {
                player!!.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, ticks, strength, false, false, true))
            }
        }

        /**
         * 일정 시간동안 특정 플레이어의 공격력을 증가시킵니다.
         * @param duration 효과의 지속시간을 나타냅니다. (단위 초)
         * @param amplifier 증가시 효과의 위력을 나타냅니다 (단위 3의 배수)*/
        fun Player.attackDamage(duration: Int, amplifier: Int) {
            val ticks = secondsToTicks(duration)
            val strength = (amplifier / 3) - 1
            if (player!!.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                val playerPotionAmplifier = player!!.getPotionEffect(PotionEffectType.INCREASE_DAMAGE)?.amplifier
                val playerPotionDuration = player!!.getPotionEffect(PotionEffectType.INCREASE_DAMAGE)?.duration
                val reStrength = playerPotionAmplifier!! + strength
                val reTicks = playerPotionDuration!! + ticks
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.INCREASE_DAMAGE, reTicks, reStrength, false, false, true
                    )
                )
            } else {
                player!!.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, ticks, strength, false, false, true))
            }
        }


        /**
         * 일정 시간동안 특정 플레이어의 받는 피해를 감소시킵니다.
         * @param duration 효과의 지속시간을 나타냅니다. (단위 초)
         * @param amplifier 감소시 효과의 위력을 나타냅니다 (단위 20%의 배수)*/
        fun Player.damageReduction(duration: Int, amplifier: Int) {
            val ticks = secondsToTicks(duration)
            val strength = (amplifier / 20) - 1
            if (player!!.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                val playerPotionAmplifier = player!!.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)?.amplifier
                val playerPotionDuration = player!!.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)?.duration
                val reStrength = playerPotionAmplifier!! + strength
                val reTicks = playerPotionDuration!! + ticks
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.DAMAGE_RESISTANCE, reTicks, reStrength, false, false, true
                    )
                )
            } else {
                player!!.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, ticks, strength, false, false, true))
            }
        }

        /**
         * 일정 시간동안 특정 플레이어의 이동 속도를 증가시킵니다.
         * @param duration 효과의 지속시간을 나타냅니다. (단위 초)
         * @param amplifier 증가시 효과의 위력을 나타냅니다 (단위 20%의 배수)*/
        fun Player.speedIncrease(duration: Int, amplifier: Int) {
            val ticks = secondsToTicks(duration)
            val strength = (amplifier / 20) - 1
            if (player!!.hasPotionEffect(PotionEffectType.SPEED)) {
                val playerPotionAmplifier = player!!.getPotionEffect(PotionEffectType.SPEED)?.amplifier
                val playerPotionDuration = player!!.getPotionEffect(PotionEffectType.SPEED)?.duration
                val reStrength = playerPotionAmplifier!! + strength
                val reTicks = playerPotionDuration!! + ticks
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.SPEED, reTicks, reStrength, false, false, true
                    )
                )
            } else {
                player!!.addPotionEffect(PotionEffect(PotionEffectType.SPEED, ticks, strength, false, false, true))
            }
        }

        /**
         * 일정 시간동안 특정 플레이어의 점프 높이를 증가시킵니다.
         * @param duration 효과의 지속시간을 나타냅니다. (단위 초)
         * @param amplifier 증가시 효과의 위력을 나타냅니다 (단위 0~255)*/
        fun Player.jumpBoost(duration: Int, amplifier: Int) {
            if (duration == INFINITE_DURATION) {
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.JUMP, INFINITE_DURATION, amplifier, false, false, true
                    )
                )
            } else {
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.JUMP, secondsToTicks(duration), amplifier, false, false, true
                    )
                )
            }
        }

        /**
         * 일정 시간동안 특정 플레이어에게 보호막을 부여합니다.
         * @param duration 보호막의 지속시간을 나타냅니다. (단위 초)
         * @param amplifier 보호막의 체력을 나타냅니다 (단위 4의 배수)*/
        fun Player.shieldAdd(duration: Int, amplifier: Int) {
            val ticks = secondsToTicks(duration)
            val strength = (amplifier / 2) - 1
            if (player!!.hasPotionEffect(PotionEffectType.ABSORPTION)) {
                val playerPotionAmplifier = player!!.getPotionEffect(PotionEffectType.ABSORPTION)?.amplifier
                val playerPotionDuration = player!!.getPotionEffect(PotionEffectType.ABSORPTION)?.duration
                val reStrength = playerPotionAmplifier!! + strength
                val reTicks = playerPotionDuration!! + ticks
                player!!.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.ABSORPTION, reTicks, reStrength, false, false, true
                    )
                )
            } else {
                player!!.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, ticks, strength, false, false, true))
            }
        }

        /**
         * 일정 시간동안 특정 플레이어에게 투명 상태를 부여합니다.
         * @param duration 투명 상태의 지속시간을 나타냅니다. (단위 초)*/
        fun Player.invisibilityState(duration: Int) {
            val ticks = secondsToTicks(duration)
            player!!.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, ticks, 1, false, false, true))
        }

        /**
         * 특정 플레이어에게 피해를 입힙니다.
         * @param damage 피해의 위력입니다.
         * @param skillname 피해의 근원 스킬입니다.
         * @param myplayer 피해 스킬을 사용한 근원 대상입니다.
         * @param invulnerable 피해가 무적 시간을 무시할지 여부*/
        fun Player.damageHealth(damage: Int, skillname: String, myplayer: Player, invulnerable: Boolean = true) {
            val currentHealth = player!!.health
            var finalDamage = damage
            if (player!!.isFatalWound()) {
                finalDamage = (finalDamage + (10 * 0.1)).toInt()
                GameManager.gamingPlayer?.forEach {
                    if (it.scoreboardTags.contains(warlock.name)) {
                        it.healingHealth((finalDamage / 2) / 20, warlock.getSkillName(0), it)
                    }
                }
            }

            if (currentHealth - finalDamage.toDouble() <= 0) {
                if (invulnerable) {
                    if (player!!.isInvulnerable) {
                        player!!.health = 0.0
                        Bukkit.getServer()
                            .broadcastMessage("${player!!.name}이(가) ${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 사망했습니다.")
                    } else {
                        player!!.damage(99999.0)
                        Bukkit.getServer()
                            .broadcastMessage("${player!!.name}이(가) ${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 사망했습니다.")
                    }
                } else {
                    player!!.damage(99999.0)
                    Bukkit.getServer()
                        .broadcastMessage("${player!!.name}이(가) ${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 사망했습니다.")
                }
            } else {
                if (invulnerable) {
                    if (player!!.isInvulnerable) {
                        val health = player!!.health
                        player!!.health = health - finalDamage.toDouble()
                        player!!.sendMessage("${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 ${finalDamage}의 피해를 입었습니다.")
                    } else {
                        player!!.damage(finalDamage.toDouble())
                        player!!.sendMessage("${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 ${finalDamage}의 피해를 입었습니다.")
                    }
                } else {
                    player!!.damage(finalDamage.toDouble())
                    player!!.sendMessage("${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 ${finalDamage}의 피해를 입었습니다.")
                }
            }
        }

        /**
         * 특정 플레이어를 회복시킵니다.
         * @param healint 회복량입니다.
         * @param skillname 회복의 근원 스킬입니다.
         * @param myplayer 회복 스킬을 사용한 근원 대상입니다.*/
        fun Player.healingHealth(healint: Int, skillname: String, myplayer: Player) {
            if (player!!.health + healint > 40) {
                player!!.health = 40.0
                player!!.sendMessage("${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 ${healint}을 회복했습니다.")
            } else {
                player!!.sendMessage("${myplayer.name}이(가) 시전한 ${skillname}${ChatColor.RESET}에 의해 ${healint}을 회복했습니다.")
                player!!.health += healint
            }
        }

        /**
         * 특정 플레이어의 스킬에 쿨타임을 적용합니다.
         * @param time 쿨타임입니다. (단위 초)
         * @param slot 쿨타임을 적용할 스킬이 존재하는 슬롯입니다. (단위 0~8)
         * @param itemstack 쿨타임을 적용할 스킬입니다.*/
        fun Player.coolTime(time: Int, slot: Int, itemstack: ItemStack) {
            player!!.inventory.setItem(slot, null)
            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                if (player != null) {
                    if (isStarting) {
                        player?.inventory?.setItem(slot, itemstack)
                    } else if (player!!.scoreboardTags.contains("training")) {
                        player?.inventory?.setItem(slot, itemstack)
                    }
                }
            }, secondsToTicks(time).toLong())
        }


        // 상태이상 관련

        /** 마나 값을 불러옴*/
        fun Player.getMana(): Int {
            return player!!.scoreboard.getObjective("Mana")?.getScore(player!!.name)?.score ?: 0
        }

        /** 마나를 수치 많큼 제거함*/
        fun Player.removeMana(int: Int) {
            if (player!!.scoreboard.getObjective("Mana")?.getScore(player!!)?.score != null) {
                player!!.scoreboard.getObjective("Mana")?.getScore(player!!)?.score =
                    player!!.scoreboard.getObjective("Mana")?.getScore(player!!)?.score!! - int
            }
        }

        /** 충전 값을 불러옴*/
        fun Player.getCharging(): Int {
            return player!!.scoreboard.getObjective("Charging")?.getScore(player!!.name)?.score ?: 0
        }

        /** 충전를 수치 많큼 제거함*/
        fun Player.removeCharging(int: Int) {
            if (player!!.scoreboard.getObjective("Charging")?.getScore(player!!)?.score != null) {
                player!!.scoreboard.getObjective("Charging")?.getScore(player!!)?.score =
                    player!!.scoreboard.getObjective("Charging")?.getScore(player!!)?.score!! - int
            }
        }

        /** 화상을 수치 만큼 추가함*/
        fun Player.addBurn(int: Int) {
            player!!.fireTicks += int * 40
        }

        /** 둔화를 수치 만큼 추가함 15%의 배수여야만 가능*/
        fun Player.addSlow(duration: Int, amplifier: Int) {
            val ticks = secondsToTicks(duration)
            val strength = (amplifier / 15) - 1
            player!!.addPotionEffect(PotionEffect(PotionEffectType.SLOW, ticks, strength, false, false, true))
        }

        /** 중력 반전을 시간 만큼 지속되게 추가함*/
        fun Player.addLevitation(duration: Int) {
            val ticks = secondsToTicks(duration)
            player!!.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, ticks, 0, false, false, true))
        }

        /** 중력 강하를 시간 만큼 지속되게 추가함*/
        fun Player.addLevitationDown(duration: Int) {
            val ticks = secondsToTicks(duration)
            player!!.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, ticks, 128, false, false, true))
        }

        /** 침묵을 시간동안 추가함*/
        fun Player.addSilence(int: Int) {
            player!!.scoreboardTags.add("silence")

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.remove("silence")
            }, int.toLong() * 20)
        }

        /** 플레이어가 침묵 상태인지 여부*/
        fun Player.isSilence(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("silence"))
            } else {
                false
            }
        }

        /** 기절을 시간동안 추가함*/
        fun Player.addStun(int: Int) {
            player!!.scoreboardTags.add("stun")
            player!!.addSilence(int)
            val location: Location = player!!.location

            object : BukkitRunnable() {
                override fun run() {
                    if (player!!.scoreboardTags.contains("stun")) {
                        player!!.teleport(location)
                        player!!.sendTitle("${ChatColor.YELLOW}${ChatColor.BOLD}기절", "", 0, int, 0)
                    } else {
                        this.cancel()
                    }
                }
            }.runTaskTimer(CLASSWAR.instance, 0, 1)

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.remove("stun")
            }, int.toLong() * 20)
        }

        /** 플레이어가 기절 상태인지 여부*/
        fun Player.isStun(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("stun"))
            } else {
                false
            }
        }

        /** 공포를 시간동안 추가함*/
        fun Player.addFear(int: Int) {
            player!!.scoreboardTags.add("fear")
            player!!.addSilence(int)
            player!!.addAbyss(10)
            object : BukkitRunnable() {
                override fun run() {
                    if (player!!.scoreboardTags.contains("fear")) {
                        player!!.location.world.playSound(player!!.location, Sound.BLOCK_WOOD_STEP, 1.0F, 1.0F)
                        player!!.sendTitle("${ChatColor.BLACK}${ChatColor.BOLD}공포", "", 0, int, 0)
                    } else {
                        this.cancel()
                    }
                }
            }.runTaskTimer(CLASSWAR.instance, 0, 1)

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.remove("fear")
            }, int.toLong() * 20)
        }

        /** 플레이어가 공포 상태인지 여부*/
        fun Player.isFear(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("fear"))
            } else {
                false
            }
        }

        /** 감전을 시간동안 추가함*/
        fun Player.addElectric(int: Int) {
            if (player!!.isElectric()) {
                player!!.addStun(3)
                return
            }
            player!!.scoreboardTags.add("electric")
            player!!.addSlow(int, 15)

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.remove("electric")
            }, int.toLong() * 20)
        }

        /** 플레이어가 기절 상태인지 여부*/
        fun Player.isElectric(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("electric"))
            } else {
                false
            }
        }

        /** 광휘을 시간동안 추가함*/
        fun Player.addBrilliance(int: Int) {
            player!!.scoreboardTags.add("brilliance")
            player!!.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, int * 20, 0, false, false, true))

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.remove("brilliance")
            }, int.toLong() * 20)
        }

        /** 플레이어가 광휘 상태인지 여부*/
        fun Player.isBrilliance(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("brilliance"))
            } else {
                false
            }
        }

        /** 심연을 시간동안 추가함*/
        fun Player.addAbyss(int: Int) {
            player!!.scoreboardTags.add("abyss")
            player!!.scoreboardTags.add("one_abyss")
            player!!.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, int * 20, 0, false, false, true))

            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.remove("abyss")
            }, int.toLong() * 20)
        }

        /** 플레이어가 심연 상태인지 여부*/
        fun Player.isAbyss(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("abyss"))
            } else {
                false
            }
        }

        /** 치명적 상처을 시간동안 추가함*/
        fun Player.addFatalWound(int: Int) {
            player!!.scoreboardTags.add("fatal_wound")
            CLASSWAR.instance.server.scheduler.runTaskLater(CLASSWAR.instance, Runnable {
                player!!.scoreboardTags.add("fatal_wound")
            }, int.toLong() * 20)
        }

        /** 플레이어가 치명적 상처 상태인지 여부*/
        fun Player.isFatalWound(): Boolean {
            return if (player != null) {
                (player!!.scoreboardTags.contains("fatal_wound"))
            } else {
                false
            }
        }


        var time: Int = 0

        /** 시간을 수치만큼 추가함*/
        fun addTime(int: Int) {
            time += int
            if (time > 5) {
                time = 5
            }
        }

        /** 시간을 수치만큼 제거함*/
        fun removeTime(int: Int) {
            time -= int
            if (time < -5) {
                time = -5
            }
        }

        /** 중력 값을 불러옴*/
        fun getGravity(player: Player): Int {
            return player.scoreboard.getObjective("Gravity")?.getScore(player.name)?.score ?: 0
        }

        /** 중력을 초기값(20)으로*/
        fun resetGravity(player: Player) {
            if (player.scoreboard.getObjective("Gravity")?.getScore(player)?.score != null) {
                player.scoreboard.getObjective("Gravity")?.getScore(player)?.score = 20
            }
        }

        /** 중력을 수치 만큼 제거함*/
        fun removeGravity(player: Player, int: Int) {
            if (player.scoreboard.getObjective("Gravity")?.getScore(player)?.score != null) {
                player.scoreboard.getObjective("Gravity")?.getScore(player)?.score =
                    player.scoreboard.getObjective("Gravity")?.getScore(player)?.score!! - int
            }

            if (getGravity(player) in 15..19) {
                player.jumpBoost(INFINITE_DURATION, 0)

            } else if (getGravity(player) in 10..14) {
                player.removePotionEffect(PotionEffectType.JUMP)
                player.jumpBoost(INFINITE_DURATION, 1)

            } else if (getGravity(player) in 5..9) {
                player.removePotionEffect(PotionEffectType.JUMP)
                player.jumpBoost(INFINITE_DURATION, 2)

            } else if (getGravity(player) in 1..4) {
                player.removePotionEffect(PotionEffectType.JUMP)
                player.jumpBoost(INFINITE_DURATION, 3)

            } else {
                player.removePotionEffect(PotionEffectType.JUMP)
                player.jumpBoost(INFINITE_DURATION, 10)

            }
        }

        /** 진동을 수치 만큼 추가함*/
        fun Player.addVibration(int: Int) {
            player!!.scoreboard.getObjective("Vibration")!!.getScore(player!!.name).score += int
        }

        /** 진동 수치를 반환함*/
        private fun Player.getVibration(): Int {
            return player!!.scoreboard.getObjective("Vibration")?.getScore(player!!.name)?.score ?: 0
        }

        /** 진동 폭발을 추가함*/
        fun Player.addVibratingExplosion(myplayer: Player) {
            val vibration = player!!.getVibration()
            if (vibration >= 1) {
                player!!.damageHealth(
                    vibration * 2,
                    "${org.bukkit.ChatColor.GOLD}${org.bukkit.ChatColor.BOLD}진동 폭발${org.bukkit.ChatColor.RESET}${org.bukkit.ChatColor.GRAY}",
                    myplayer
                )
                player!!.scoreboard.getObjective("Vibration")!!.getScore(player!!.name).score = 0
                player!!.location.world.playSound(player!!.location, Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1f, 0.5f)
            }
        }

        /** 출혈을 수치 만큼 추가함*/
        fun Player.addBleeding(int: Int) {
            player!!.scoreboard.getObjective("Bleeding")!!.getScore(player!!.name).score += int

            var time = 0
            object : BukkitRunnable() {
                override fun run() {
                    if (time >= 60) {
                        player!!.scoreboard.getObjective("Bleeding")!!.getScore(player!!.name).score = 0
                        this.cancel()
                    }

                    if (player!!.isSprinting) {
                        player!!.damage(player!!.getBleeding().toDouble())
                        player!!.location.world.playSound(
                            player!!.location, Sound.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1.0f, 1.0f
                        )
                        player!!.location.world.spawnParticle(
                            Particle.BLOCK_CRACK,
                            location,
                            10,
                            0.0,
                            0.0,
                            0.0,
                            1.0,
                            Material.REDSTONE_BLOCK.createBlockData()
                        )
                    }



                    time++
                }
            }.runTaskTimer(CLASSWAR.instance, 0L, 1L)
        }

        /** 출혈 수치를 반환함*/
        private fun Player.getBleeding(): Int {
            return player!!.scoreboard.getObjective("Bleeding")?.getScore(player!!.name)?.score ?: 0
        }

        data class Card(val type: String)

        private var cards: MutableList<Card> = mutableListOf()
        private var hand: MutableList<Card> = mutableListOf()

        /** 카드덱을 초기값으로*/
        fun cardDeckReset() {
            for (i in 1..5) {
                cards.add(Card("Special"))
            }
            for (i in 1..45) {
                cards.add(Card("Normal"))
            }
        }

        fun cardDeckShuffle() {
            cards.shuffle()
        }

        fun cardDeckRemaining(): Int {
            return cards.size
        }

        /** 카드를 수 만큼 버림*/
        fun Player.cardThrow(int: Int) {
            var discardCount = 0

            val normalCards = hand.filter { it.type == "Normal" }.toMutableList()

            while (discardCount < int && normalCards.isNotEmpty()) {
                val card = normalCards.removeAt(0)
                hand.remove(card)
                discardCount++
                when (Random.nextInt(1..3)) {
                    1 -> {
                        player!!.speedIncrease(3, 20)
                    }

                    2 -> {
                        cardDraw(1)
                    }

                    3 -> {
                        player!!.healingHealth(3, gambler.getSkillName(3), player!!)
                    }
                }
            }

        }

        /** 카드를 수 만큼 뽑음*/
        fun Player.cardDraw(int: Int) {
            for (i in 1..int) {
                if (cards.isNotEmpty()) {
                    val card = cards.removeAt(0)
                    if (card.type == "Special") {
                        if (player!!.scoreboard.getObjective("SpecialCardCount")!!.getScore(player!!.name).score == 4) {
                            if (isStarting) {
                                if (player!!.isTeam("RedTeam")) {
                                    specialVictory("RedTeam", gambler)
                                } else {
                                    specialVictory("BlueTeam", gambler)
                                }
                            } else {
                                player!!.sendMessage("${org.bukkit.ChatColor.GREEN}${org.bukkit.ChatColor.BOLD}[!] 도박사의 효과로 특수 승리했습니다.")
                                player!!.inventory.clear()
                                val playerTags = player!!.scoreboardTags.toList()
                                for (tag in playerTags) {
                                    player!!.removeScoreboardTag(tag)
                                }
                                player!!.activePotionEffects.forEach { effect ->
                                    player!!.removePotionEffect(effect.type)
                                }
                                player!!.scoreboard.getObjective("SpecialCardCount")!!.getScore(player!!.name).score = 0
                                player!!.teleport(Location(Bukkit.getWorld("world"), 10.0, -60.0, 0.0, 90F, 0F))
                            }


                        } else {
                            player!!.scoreboard.getObjective("SpecialCardCount")!!.getScore(player!!.name).score += 1
                        }
                    }
                    hand.add(card)
                } else {
                    break
                }
            }
        }

        // 그 외 잡다한 것들

        private fun secondsToTicks(seconds: Int): Int {
            return seconds * 20
        }

        /** 특수 승리*/
        private fun specialVictory(team: String, victoryTags: Class) {
            val gameManager = GameManager()
            if (team == "RedTeam") {
                when (victoryTags) {
                    gambler -> {
                        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                            onlinePlayer.sendTitle(
                                "${org.bukkit.ChatColor.BOLD}${org.bukkit.ChatColor.DARK_RED}레드팀 승리",
                                "${org.bukkit.ChatColor.BOLD}도박사의 능력으로 특수 승리했습니다.",
                                20,
                                40,
                                20
                            )
                            onlinePlayer.playSound(onlinePlayer.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F)
                        }
                    }
                }
            } else {
                when (victoryTags) {
                    gambler -> {
                        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                            onlinePlayer.sendTitle(
                                "${org.bukkit.ChatColor.BOLD}${org.bukkit.ChatColor.DARK_BLUE}블루팀 승리",
                                "${org.bukkit.ChatColor.BOLD}도박사의 능력으로 특수 승리했습니다.",
                                20,
                                40,
                                20
                            )
                            onlinePlayer.playSound(onlinePlayer.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F)
                        }
                    }
                }
            }
            gameManager.gameEnd()
        }

        /** player의 팀이 team과 일치하는지 확인*/
        fun Player.isTeam(team: String): Boolean {
            if (player != null) {
                return GameManager.teams[team]?.hasEntry(player!!.name) == true
            }
            return false
        }

        fun Player.getTeam(): String? {
            return GameManager.teams.entries.firstOrNull {
                it.value!!.hasEntry(player!!.name)
            }?.key
        }

        /** player와 가장 가까운 아군 반환, 자신이 마지막 인원이면 자신 반환*/
        fun Player.findClosestFriendly(team: String): Player? {
            var closestPlayer: Player? = null
            var closestDistanceSquared = Double.MAX_VALUE
            var teamPlayerCount = 0

            if (player != null) {
                for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.isTeam(team)) {
                        teamPlayerCount++
                        if (onlinePlayer != player) {
                            val distanceSquared = player!!.location.distanceSquared(onlinePlayer.location)
                            if (distanceSquared < closestDistanceSquared) {
                                closestPlayer = onlinePlayer
                                closestDistanceSquared = distanceSquared
                            }
                        }
                    }
                }
                return if (teamPlayerCount <= 1) player else closestPlayer
            }
            return null
        }


        /** 플레이어가 전장에 있는지 여부*/
        fun Player.isBattlefield(): Boolean {
            if (player != null) {
                return (player!!.location.x >= -6.0 && player!!.location.x <= 30.0 && player!!.location.y >= -64.0 && player!!.location.y <= 100.0 && player!!.location.z >= -60.0 && player!!.location.z <= -24.0 || player!!.location.x in 31.0..63.0 && player!!.location.y >= -64.0 && player!!.location.y <= 100.0 && player!!.location.z >= -58.0 && player!!.location.z <= -26.0 ||
                        player!!.location.x in 66.0..72.0 && player!!.location.y >= -52.0 && player!!.location.y <= -46.0 && player!!.location.z >= -101.0 && player!!.location.z <= -95.0)
            }
            return false
        }

        /** 플레이어 반지름 내 존재 여부*/
        fun Player.isRadius(playerpose: Location, radius: Double): Boolean {
            if (player != null) {
                return playerpose.distance(player!!.location) <= radius && playerpose != player
            }
            return false
        }

        /** 플레이어가 게임 참가자인지 여부*/
        fun Player.isPlayer(): Boolean {
            return player in onlinePlayers
        }

        /** 플레이어가 훈련중인지 여부*/
        fun Player.isTraining(): Boolean {
            return player?.scoreboardTags?.contains("training") == true
        }

        /** N초간 대상 지정 불가 상태를 추가함*/
        fun Player.addUntargetability(time: Int) {
            player!!.scoreboardTags.add("untargetability")
            object : BukkitRunnable() {
                override fun run() {
                    player!!.scoreboardTags.remove("untargetability")
                }
            }.runTaskLater(CLASSWAR.instance, (time * 20).toLong())
        }

        /** 플레이어가 대상 지정 불가 상태인지 여부*/
        fun Player.isUntargetability(): Boolean {
            return player?.scoreboardTags?.contains("untargetability") == true
        }

        /** 플레이어가 스킬의 대상이 될 수 있는지 여부*/
        fun Player.isSkillTarget(): Boolean {
            if (player != null) {
                if (player!!.isPlayer() || player!!.isTraining()) {
                    if (!player!!.isUntargetability()) {
                        return true
                    }
                }
                return false
            }
            return false
        }

        fun spawnProjectile(
            player: Player,
            skill: ItemStack,
            speed: Double,
            x: Double,
            y: Double,
            z: Double,
            physics: Boolean,
            crash: Boolean,
            remove: Boolean,
            time: Int? = null,
            head: Boolean = true,
            flat: Boolean = false
        ) {
            val initialLocation: Location = if (head) {
                player.eyeLocation
            } else {
                player.location
            }

            if (flat) {
                initialLocation.pitch = 0F
            }
            val direction = initialLocation.direction.normalize()
            var timeSet = 0
            val currentLocation: Location = initialLocation.clone()
            var traveledDistance = 0.0


            object : BukkitRunnable() {
                override fun run() {
                    if (physics) {
                        if (!currentLocation.block.type.isAir) {
                            val hitEvent = MarkerHitEvent(
                                player, skill, Type.BLOCK, currentLocation.block.location, null, currentLocation
                            )
                            CLASSWAR.instance.server.pluginManager.callEvent(hitEvent)
                            this.cancel()
                        }
                    }

                    if (crash) {
                        if (currentLocation.world.getNearbyEntities(currentLocation, x, y, z)
                                .any { it is Player && it != player }
                        ) {
                            val collidedPlayer = currentLocation.world.getNearbyEntities(currentLocation, x, y, z)
                                .filterIsInstance<Player>().firstOrNull { it != player }

                            if (collidedPlayer != null) {
                                if (collidedPlayer.isSkillTarget()) {
                                    if (player.isTeam("RedTeam")) {
                                        if (!collidedPlayer.isTeam("RedTeam")) {
                                            val hitEvent = MarkerHitEvent(
                                                player, skill, Type.ENTITY, null, collidedPlayer, currentLocation
                                            )
                                            CLASSWAR.instance.server.pluginManager.callEvent(hitEvent)
                                            if (remove) {
                                                this.cancel()
                                            }
                                        }
                                    } else {
                                        if (!collidedPlayer.isTeam("BlueTeam")) {
                                            val hitEvent = MarkerHitEvent(
                                                player, skill, Type.ENTITY, null, collidedPlayer, currentLocation
                                            )
                                            CLASSWAR.instance.server.pluginManager.callEvent(hitEvent)
                                            if (remove) {
                                                this.cancel()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (time != null) {
                        if (timeSet >= time * 20) {
                            this.cancel()
                        }
                        timeSet++
                    }

                    val moveEvent = MarkerMoveEvent(player, skill, currentLocation)
                    CLASSWAR.instance.server.pluginManager.callEvent(moveEvent)

                    currentLocation.x += direction.x * speed
                    currentLocation.y += direction.y * speed
                    currentLocation.z += direction.z * speed
                    traveledDistance += speed
                }
            }.runTaskTimer(CLASSWAR.instance, 0L, 1L)
        }

        fun shootLaserFromPlayer(player: Player, size: Double, wallShot: Boolean, range: Double? = null): Entity? {
            val world = player.world
            val startLocation = player.eyeLocation
            val direction = startLocation.direction

            val maxDistance: Double = range ?: 100.0

            val blockRayTraceResult = world.rayTraceBlocks(startLocation, direction, maxDistance)

            if (wallShot) {
                if (blockRayTraceResult?.hitBlock != null) {
                    return null
                }
            }

            val entityRayTraceResult = world.rayTraceEntities(startLocation, direction, maxDistance, size) { entity ->
                entity !== player
            }

            if (entityRayTraceResult?.hitEntity is Player) {
                val hitPlayer = entityRayTraceResult.hitEntity as Player
                if (hitPlayer.isSkillTarget()) {
                    return hitPlayer
                }
            }
            return null
        }

        fun shootLaserFromBlock(player: Player, range: Double? = null): Block? {
            val world = player.world
            val startLocation = player.eyeLocation
            val direction = startLocation.direction

            val maxDistance: Double = range ?: 100.0

            val blockRayTraceResult = world.rayTraceBlocks(startLocation, direction, maxDistance)

            return if (blockRayTraceResult?.hitBlock == null) {
                null
            } else {
                blockRayTraceResult.hitBlock
            }
        }

        fun isBetween(startLocation: Location, endLocation: Location, checkLocation: Location): Boolean {
            val startToEnd = endLocation.toVector().subtract(startLocation.toVector())
            val startToCheck = checkLocation.toVector().subtract(startLocation.toVector())

            val dot = startToCheck.dot(startToEnd)
            if (dot > 0) {
                val lengthSquared = startToEnd.lengthSquared()
                return dot < lengthSquared && checkLocation.world == startLocation.world
            }
            return false
        }

        /**
         * 두 위치 사이의 직육면체 부피를 계산합니다.
         *
         * @param loc1 첫 번째 위치
         * @param loc2 두 번째 위치
         * @return 두 위치 사이의 직육면체 부피
         */
        fun calculateVolumeBetweenLocations(loc1: Location, loc2: Location): Int {
            val xDistance = abs(loc1.x - loc2.x)
            val yDistance = abs(loc1.y - loc2.y)
            val zDistance = abs(loc1.z - loc2.z)

            val volume = xDistance * yDistance * zDistance

            return volume.toInt()
        }

        /**
         * 직육면체 내부에 존재하는 플레이어들을 반환합니다.
         *
         * @param loc1 직육면체를 정의하는 첫 번째 위치
         * @param loc2 직육면체를 정의하는 두 번째 위치
         * @param world 파티클을 재생할 월드
         * @return 직육면체 내부에 있는 플레이어 리스트
         */
        fun getPlayersInsideRectangle(loc1: Location, loc2: Location, world: World): List<Player> {
            val minX = loc1.x.coerceAtMost(loc2.x) - 0.5
            val maxX = loc1.x.coerceAtLeast(loc2.x) + 0.5
            val minY = loc1.y.coerceAtMost(loc2.y) - 0.5
            val maxY = loc1.y.coerceAtLeast(loc2.y) + 0.5
            val minZ = loc1.z.coerceAtMost(loc2.z) - 0.5
            val maxZ = loc2.z.coerceAtLeast(loc1.z) + 0.5

            return world.players.filter { player ->
                val playerLoc = player.location
                playerLoc.x in minX..maxX && playerLoc.y in minY..maxY && playerLoc.z in minZ..maxZ
            }
        }

        /**
         * 두 직육면체가 겹치는지 확인합니다.
         *
         * @param loc1A 첫 번째 직육면체를 정의하는 첫 번째 위치
         * @param loc2A 첫 번째 직육면체를 정의하는 두 번째 위치
         * @param loc1B 두 번째 직육면체를 정의하는 첫 번째 위치
         * @param loc2B 두 번째 직육면체를 정의하는 두 번째 위치
         * @return 겹치면 true, 그렇지 않으면 false
         */
        fun doRectanglesOverlap(loc1A: Location, loc2A: Location, loc1B: Location, loc2B: Location): Boolean {
            val minX1 = loc1A.x.coerceAtMost(loc2A.x) - 0.5
            val maxX1 = loc1A.x.coerceAtLeast(loc2A.x) + 0.5
            val minY1 = loc1A.y.coerceAtMost(loc2A.y) - 0.5
            val maxY1 = loc1A.y.coerceAtLeast(loc2A.y) + 0.5
            val minZ1 = loc1A.z.coerceAtMost(loc2A.z) - 0.5
            val maxZ1 = loc1A.z.coerceAtLeast(loc2A.z) + 0.5

            val minX2 = loc1B.x.coerceAtMost(loc2B.x) - 0.5
            val maxX2 = loc1B.x.coerceAtLeast(loc2B.x) + 0.5
            val minY2 = loc1B.y.coerceAtMost(loc2B.y) - 0.5
            val maxY2 = loc1B.y.coerceAtLeast(loc2B.y) + 0.5
            val minZ2 = loc1B.z.coerceAtMost(loc2B.z) - 0.5
            val maxZ2 = loc1B.z.coerceAtLeast(loc2B.z) + 0.5

            val overlapX = minX1 < maxX2 && maxX1 > minX2
            val overlapY = minY1 < maxY2 && maxY1 > minY2
            val overlapZ = minZ1 < maxZ2 && maxZ1 > minZ2

            return overlapX && overlapY && overlapZ
        }
    }
}