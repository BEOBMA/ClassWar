@file:Suppress("DEPRECATION")

package org.beobma.classwar.util

import org.bukkit.ChatColor
import org.bukkit.entity.Player

object SkillFaillMessage {

    /**
     * 화살이 부족하여 스킬을 발동할 수 없습니다.
     */
    fun Player.sendArrowInsufficient() {
        player!!.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.GOLD}${ChatColor.BOLD}화살${ChatColor.RED}${ChatColor.BOLD}이 부족하여 스킬을 발동할 수 없습니다.")
    }

    /**
     * 마나가 부족하여 스킬을 발동할 수 없습니다.
     */
    fun Player.sendManaInsufficient() {
        player!!.sendMessage("${ChatColor.RED}${ChatColor.BOLD}[!] ${ChatColor.BLUE}${ChatColor.BOLD}마나${ChatColor.RED}${ChatColor.BOLD}가 부족하여 스킬을 발동할 수 없습니다.")
    }
}