package org.beobma.classwar.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameStartEvent : Event() {

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