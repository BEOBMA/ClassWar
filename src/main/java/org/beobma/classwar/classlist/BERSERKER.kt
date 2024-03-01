@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.GameManager.Companion.isGaming
import org.beobma.classwar.LOCALIZATION.Companion.berserker
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill.Companion.attackSpeed
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.damageReduction
import org.beobma.classwar.util.Skill.Companion.speedIncrease
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class BERSERKER : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == berserker.name) {
            when (skill) {
                berserker.skill[0] -> { // 일반 스킬
                    player.attackSpeed(4, 20)
                    player.location.world.spawnParticle(
                        Particle.DAMAGE_INDICATOR,
                        player.eyeLocation,
                        100,
                        1.0,
                        1.0,
                        1.0,
                        0.0
                    )
                    player.location.world.playSound(player.location, Sound.ENTITY_HORSE_DEATH, 0.5f, 0.5f)
                    player.coolTime(10, 1, berserker.skill[0])
                }
                berserker.skill[1] -> { // 궁극 스킬
                    player.damageHealth(5, berserker.getSkillName(1), player)
                    player.speedIncrease(8, 20)
                    player.damageReduction(8, 40)
                    player.location.world.spawnParticle(
                        Particle.FIREWORKS_SPARK,
                        player.eyeLocation.add(0.0, 0.0, 0.0),
                        100,
                        1.0,
                        1.0,
                        1.0,
                        0.0
                    )
                    player.location.world.playSound(
                        player.location,
                        Sound.ENTITY_WARDEN_SONIC_BOOM,
                        0.75f,
                        2f
                    )
                    player.inventory.setItem(2, null)
                }
            }
        }
    }
    private var plusdamage: Double = 0.0

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.damager
        val damagerEntity = event.entity

        if (isGaming) {
            if (damagerEntity.scoreboardTags.contains(berserker.name) && damagerEntity is Player) {
                for (currentHealth in 40 downTo 20) {
                    if (damagerEntity.health in 20.0..39.0) {
                        plusdamage += 0.1
                        damagerEntity.location.world.spawnParticle(
                            Particle.VILLAGER_ANGRY,
                            damagerEntity.eyeLocation.add(0.0, 0.0, 0.0),
                            1,
                            1.0,
                            1.0,
                            1.0,
                            0.0
                        )
                    }
                }
            }

            if (damagedEntity.scoreboardTags.contains(berserker.name)) {
                event.damage += plusdamage
            }
        }
    }

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        plusdamage = 0.0
    }
}