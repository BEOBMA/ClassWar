@file:Suppress("DEPRECATION")

package org.beobma.classwar.classlist

import org.beobma.classwar.GameManager
import org.beobma.classwar.LOCALIZATION.Companion.archer
import org.beobma.classwar.event.GameStartEvent
import org.beobma.classwar.event.MarkerHitEvent
import org.beobma.classwar.event.MarkerMoveEvent
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill.Companion.coolTime
import org.beobma.classwar.util.Skill.Companion.damageHealth
import org.beobma.classwar.util.Skill.Companion.invisibilityState
import org.beobma.classwar.util.Skill.Companion.spawnProjectile
import org.beobma.classwar.util.SkillFaillMessage.sendArrowInsufficient
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack

class ARCHER : Listener {

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        val job = event.job
        val skill = event.skill

        if (job == archer.name) {
            when (skill) {
                archer.skill[0] -> { // 일반 스킬
                    if (getArrowCount(player) < 1) {
                        player.sendArrowInsufficient()
                    } else {
                        spawnProjectile(
                            player, archer.skill[0], 1.0, 0.5, 0.5, 0.5, physics = true, crash = true, remove = true
                        )

                        player.location.world.playSound(player.location, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.3f)

                        player.coolTime(10, 1, archer.skill[0])
                        val arrow = ItemStack(Material.ARROW, 1).apply {
                            itemMeta = itemMeta.apply {
                                setDisplayName(archer.getItemName(0))
                                lore = archer.getItemLore(0)
                            }
                        }
                        player.inventory.removeItem(arrow)
                    }
                }
                archer.skill[1] -> { // 궁극 스킬
                    if (getArrowCount(player) < 3) {
                        player.sendArrowInsufficient()
                    } else {
                        spawnProjectile(
                            player, archer.skill[1], 1.0, 0.75, 0.75, 0.75, physics = true, crash = true, remove = true
                        )

                        player.location.world.playSound(player.location, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.6f)

                        val arrow = ItemStack(Material.ARROW, 3).apply {
                            itemMeta = itemMeta.apply {
                                setDisplayName(archer.getItemName(0))
                                lore = archer.getItemLore(0)
                            }
                        }
                        player.inventory.removeItem(arrow)
                        player.inventory.setItem(2, null)
                    }

                }
            }
        }
    }

    private var isUsed: Boolean = false

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.entity

        if (GameManager.isGaming) {
            if (!isUsed) {
                if (damagedEntity.scoreboardTags.contains(archer.name) && damagedEntity is Player) {
                    if (damagedEntity.health - event.damage <= 20) {
                        isUsed = true
                        event.isCancelled = true
                        damagedEntity.invisibilityState(4)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onMarkerHit(event: MarkerHitEvent) {
        val entity = event.crashEntity
        if (event.skill == archer.skill[0]) {
            if (entity != null) {
                entity.damageHealth(10, archer.getSkillName(0), event.player)
                entity.location.world.playSound(
                    entity.location,
                    Sound.ENTITY_ARROW_HIT_PLAYER,
                    0.2f,
                    1f
                )
            }
        }
        if (event.skill == archer.skill[1]) {
            if (entity != null) {
                entity.damageHealth(20, archer.getSkillName(1), event.player)
                entity.location.world.playSound(
                    entity.location,
                    Sound.ENTITY_ARROW_HIT_PLAYER,
                    0.2f,
                    1.3f
                )
            }
        }
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        if (event.skill == archer.skill[0]) {
            location.world.spawnParticle(
                Particle.END_ROD,
                location,
                1,
                0.0,
                0.0,
                0.0,
                0.0
            )
        }

        if (event.skill == archer.skill[1]) {
            location.world.spawnParticle(
                Particle.END_ROD,
                location,
                1,
                0.1,
                0.1,
                0.0,
                0.0
            )
        }
    }

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        isUsed = false
    }

    private fun getArrowCount(player: Player): Int {
        val inventory = player.inventory
        var arrowCount = 0

        for (item in inventory.contents) {
            if (item != null && item.type == Material.ARROW) {
                arrowCount += item.amount
            }
        }

        return arrowCount
    }
}