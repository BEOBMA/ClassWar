package org.beobma.classwar.util

import org.beobma.classwar.util.Skill.Companion.getBleeding
import org.beobma.classwar.util.Skill.Companion.isPlayer
import org.beobma.classwar.util.Skill.Companion.isTraining
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSprintEvent

class SkillEventList : Listener {

    @EventHandler
    fun onPlayerToggleSprint(event: PlayerToggleSprintEvent) {
        val player = event.player

        if (!event.isSprinting) return
        if (!player.isPlayer() && !player.isTraining()) return
        if (player.getBleeding() <= 0) return

        player.damage(player.getBleeding().toDouble())
        player.location.world.playSound(
            player.location, Sound.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1.0f, 1.0f
        )
        player.location.world.spawnParticle(
            Particle.BLOCK_CRACK,
            player.location,
            10,
            0.0,
            0.0,
            0.0,
            1.0,
            Material.REDSTONE_BLOCK.createBlockData()
        )
    }
}