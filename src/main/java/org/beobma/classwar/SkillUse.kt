package org.beobma.classwar

import org.beobma.classwar.LOCALIZATION.Companion.classList
import org.beobma.classwar.event.SkillUsingEvent
import org.beobma.classwar.util.Skill.Companion.isSilence
import org.beobma.classwar.util.Skill.Companion.isStun
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class SkillUse : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (player.scoreboardTags.contains("training") || player.scoreboardTags.contains("gameplayer")) {
            if (event.action.name.contains("RIGHT")) {
                if (event.item != null) {
                    if (!player.isSilence() && !player.isStun()) {
                        if (classList.any { it in player.scoreboardTags}) {
                            val tag = classList.first { it in player.scoreboardTags }
                            CLASSWAR.instance.server.pluginManager.callEvent(SkillUsingEvent(player, tag, event.item!!))
                        }
                    }
                }
            }
        }
    }
}