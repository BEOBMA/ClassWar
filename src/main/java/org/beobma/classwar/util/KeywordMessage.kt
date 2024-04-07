@file:Suppress("DEPRECATION")

package org.beobma.classwar.util

import org.bukkit.ChatColor

object KeywordMessage {

    /**
     * @param n 20의 배수
     * @return 공격 속도가 N%
     */
    fun attackSpeedKeyword(n: Int): String {
        return "${ChatColor.YELLOW}${ChatColor.BOLD}공격 속도가 ${n}%${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @param n 20의 배수
     * @return 이동 속도가 N%
     */
    fun moveSpeedKeyword(n: Int): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}이동 속도가 ${n}%${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @param n 20의 배수
     * @return 받는 피해가 I%
     */
    fun damageReductionKeyword(n: Int): String {
        return "${ChatColor.YELLOW}${ChatColor.BOLD}받는 피해가 ${n}%${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 공격력
     */
    fun attackDamageKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}공격력${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 투명 상태
     */
    fun invisibilityStateKeyword(): String {
        return "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}투명 상태${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @param n
     * @return 화상 N
     */
    fun burnKeyword(n: Int? = null): String {
        return if (n == null) {
            "${ChatColor.RED}${ChatColor.BOLD}화상${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.RED}${ChatColor.BOLD}화상 ${n}${ChatColor.RESET}${ChatColor.GRAY}"
        }
    }

    /**
     * @param n
     * @return N의 피해를 흡수하는 보호막
     */
    fun shieldKeyword(n: Int? = null): String {
        return if (n == null) {
            "${ChatColor.AQUA}${ChatColor.BOLD}보호막${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.AQUA}${ChatColor.BOLD}${n}의 피해를 흡수하는 보호막${ChatColor.RESET}${ChatColor.GRAY}"
        }
    }

    /**
     * @param n 15의 배수
     * @return N% 둔화
     */
    fun slowKeyword(n: Int? = null): String {
        return if (n == null) {
            "${ChatColor.AQUA}${ChatColor.BOLD}보호막${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.AQUA}${ChatColor.BOLD}${n}의 피해를 흡수하는 보호막${ChatColor.RESET}${ChatColor.GRAY}"
        }
    }

    /**
     * @param n
     * @return 체력을 N 회복
     */
    fun healKeyword(n: Int? = null): String {
        return if (n == null) {
            "${ChatColor.GREEN}${ChatColor.BOLD}체력을 회복${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.GREEN}${ChatColor.BOLD}체력을 $n 회복${ChatColor.RESET}${ChatColor.GRAY}"
        }
    }

    /**
     * @param n
     * @return 진동 N
     */
    fun vibrationKeyword(n: Int? = null): String {
        return if (n == null) {
            "${ChatColor.GOLD}${ChatColor.BOLD}진동${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.GOLD}${ChatColor.BOLD}진동 $n${ChatColor.RESET}${ChatColor.GRAY}"
        }
    }

    /**
     * @return 진동 폭발
     */
    fun vibrationExplosionKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}진동 폭발${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @param n
     * @return 출혈 N
     */
    fun bleedingKeyword(n: Int? = null): String {
        return if (n == null) {
            "${ChatColor.DARK_RED}${ChatColor.BOLD}출혈${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.DARK_RED}${ChatColor.BOLD}출혈 $n${ChatColor.RESET}${ChatColor.GRAY}"
        }
    }

    /**
     * @return 기절
     */
    fun stunKeyword(): String {
        return "${ChatColor.YELLOW}${ChatColor.BOLD}기절${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 감전
     */
    fun electricKeyword(): String {
        return "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}감전${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 광휘
     */
    fun brillianceKeyword(): String {
        return "${ChatColor.WHITE}${ChatColor.BOLD}광휘${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 심연
     */
    fun abyssKeyword(): String {
        return "${ChatColor.DARK_BLUE}${ChatColor.BOLD}심연${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 대상 지정 불가
     */
    fun untargetabilityKeyword(): String {
        return "${ChatColor.DARK_GRAY}${ChatColor.BOLD}대상 지정 불가${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 침묵
     */
    fun silenceKeyword(): String {
        return "${ChatColor.DARK_GRAY}${ChatColor.BOLD}침묵${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 스킬 체크
     */
    fun skillCheckKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}스킬 체크${ChatColor.RESET}${ChatColor.GRAY}"
    }
}