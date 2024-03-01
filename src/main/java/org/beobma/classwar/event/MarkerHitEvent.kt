package org.beobma.classwar.event

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class MarkerHitEvent(val player: Player, val skill: ItemStack, val type: Type, val blockPosition: Location? = null, val crashEntity: Player? = null, val marker: Location) : Event() {
    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}

enum class Type{
    ENTITY, BLOCK
}