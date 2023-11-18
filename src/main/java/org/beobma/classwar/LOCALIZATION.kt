@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.bukkit.ChatColor
import java.util.ArrayList

class LOCALIZATION {
    companion object{

        val map_list = listOf(
            mapOf("id" to 1, "value" to "${ChatColor.BOLD}${ChatColor.GREEN}숲"),
            mapOf("id" to 2, "value" to "${ChatColor.BOLD}${ChatColor.DARK_GRAY}동굴"),
            mapOf("id" to 3, "value" to "${ChatColor.BOLD}${ChatColor.GRAY}건물"),
            mapOf("id" to 4, "value" to "${ChatColor.BOLD}${ChatColor.WHITE}시공간의 끝"),
            mapOf("id" to 5, "value" to "${ChatColor.BOLD}${ChatColor.GOLD}사막"),
            mapOf("id" to 6, "value" to "${ChatColor.BOLD}${ChatColor.DARK_GREEN}대나무 숲"),
            mapOf("id" to 7, "value" to "${ChatColor.BOLD}${ChatColor.RED}화산"),
            mapOf("id" to 8, "value" to "${ChatColor.BOLD}${ChatColor.DARK_BLUE}심해"),
            mapOf("id" to 9, "value" to "${ChatColor.BOLD}${ChatColor.BLUE}해전"),
            mapOf("id" to 10, "value" to "${ChatColor.BOLD}${ChatColor.DARK_PURPLE}엔드")


            // 추가적인 맵들...
        )

        val berserker_name: String = "${ChatColor.BOLD}광전사"
        val berserker_lore: List<String> = arrayListOf(
            "첫 번째 줄 텍스트",
            "두 번째 줄 텍스트",
            "세 번째 줄 텍스트",
        )


        val archer_name: String = "${ChatColor.BOLD}궁수"
        val archer_lore: List<String> = arrayListOf(
            "첫 번째 줄 텍스트",
            "두 번째 줄 텍스트",
            "세 번째 줄 텍스트",
        )


        val fire_wizard_name: String = "${ChatColor.BOLD}불마법사"
        val fire_wizard_lore: List<String> = arrayListOf(
            "첫 번째 줄 텍스트",
            "두 번째 줄 텍스트",
            "세 번째 줄 텍스트",
        )
    }
}