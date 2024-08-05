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

    companion object {
        private var isUsed: Boolean = false
    }

    @EventHandler
    fun onPlayerSkillUsing(event: SkillUsingEvent) {
        val player = event.player
        if (event.job == archer.name) {
            when (event.skill) {
                archer.skill[0] -> handleNormalSkill(player)
                archer.skill[1] -> handleUltimateSkill(player)
            }
        }
    }

    private fun handleNormalSkill(player: Player) {
        if (getArrowCount(player) < 1) {
            player.sendArrowInsufficient()
        } else {
            spawnProjectile(player, archer.skill[0], 1.0, 0.5, 0.5, 0.5, true, crash = true, remove = true)
            player.location.world.playSound(player.location, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.3f)
            player.coolTime(10, 1, archer.skill[0])
            removeArrows(player, 1, archer.getItemName(0), archer.getItemLore(0))
        }
    }

    private fun handleUltimateSkill(player: Player) {
        if (getArrowCount(player) < 3) {
            player.sendArrowInsufficient()
        } else {
            spawnProjectile(player, archer.skill[1], 1.0, 0.75, 0.75, 0.75, physics = true, crash = true, remove = true)
            player.location.world.playSound(player.location, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.6f)
            removeArrows(player, 3, archer.getItemName(1), archer.getItemLore(1))
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (GameManager.isStarting && !isUsed) {
            val damagedEntity = event.entity
            if (damagedEntity is Player && damagedEntity.scoreboardTags.contains(archer.name)) {
                if (damagedEntity.health - event.damage <= 20) {
                    isUsed = true
                    event.isCancelled = true
                    damagedEntity.invisibilityState(4)
                }
            }
        }
    }

    @EventHandler
    fun onMarkerHit(event: MarkerHitEvent) {
        val entity = event.crashEntity
        val skillIndex = if (event.skill == archer.skill[0]) 0 else 1
        val damage = if (skillIndex == 0) 10 else 20
        val pitch = if (skillIndex == 0) 1f else 1.3f

        entity?.let {
            it.damageHealth(damage, archer.getSkillName(skillIndex), event.player)
            it.location.world.playSound(it.location, Sound.ENTITY_ARROW_HIT_PLAYER, 0.2f, pitch)
        }
    }

    @EventHandler
    fun onMoveMarker(event: MarkerMoveEvent) {
        val location = event.location
        val particleCount = if (event.skill == archer.skill[0]) 0.0 else 0.1

        location.world.spawnParticle(
            Particle.END_ROD,
            location,
            1,
            particleCount,
            particleCount,
            0.0,
            0.0
        )
    }

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        isUsed = false
    }

    private fun getArrowCount(player: Player): Int {
        return player.inventory.contents.filter { it != null && it.type == Material.ARROW }.sumOf { it!!.amount }
    }

    private fun removeArrows(player: Player, count: Int, name: String, lore: List<String>?) {
        val arrow = ItemStack(Material.ARROW, count).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(name)
                this.lore = lore
            }
        }
        player.inventory.removeItem(arrow)
    }
}
