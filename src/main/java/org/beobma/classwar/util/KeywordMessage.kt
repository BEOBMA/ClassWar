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
     * @param n 20의 배수 감속의 경우 15의 배수
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
     * @return 시간
     */
    fun timeKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 중력
     */
    fun gravityKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 카드
     */
    fun cardKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 충전
     */
    fun chargeKeyword(): String {
        return "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 화상: 수치에 비례한 시간동안 불에 타오릅니다.
     */
    fun burnKeywordLong(): String {
        return "${burnKeyword()}: 수치에 비례한 시간동안 불에 타오릅니다."
    }

    /**
     * @return 진동: 진동 폭발이 적용되면 수치 x 2만큼 피해를 입고 수치를 초기화합니다."
     */
    fun vibrationKeywordLong(): String {
        return "${vibrationKeyword()}: ${vibrationExplosionKeyword()}이 적용되면 수치 x 2만큼 피해를 입고 수치를 초기화합니다."
    }

    /**
     * @param n 4의 배수
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
            "${ChatColor.AQUA}${ChatColor.BOLD}둔화${ChatColor.RESET}${ChatColor.GRAY}"
        } else {
            "${ChatColor.AQUA}${ChatColor.BOLD}${n}% 둔화${ChatColor.RESET}${ChatColor.GRAY}"
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
     * @return 출혈: 3초간 달릴 때 수치에 비례한 피해를 입습니다.
     */
    fun bleedingLongKeyword(): String {
        return "${bleedingKeyword()}: 3초간 달릴 때 수치에 비례한 피해를 입습니다."
    }

    /**
     * @return 기절
     */
    fun stunKeyword(): String {
        return "${ChatColor.YELLOW}${ChatColor.BOLD}기절${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 공포
     */
    fun fearKeyword(): String {
        return "${ChatColor.BLACK}${ChatColor.BOLD}공포${ChatColor.RESET}${ChatColor.GRAY}"
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
     * @return 추방
     */
    fun exileKeyword(): String {
        return "${ChatColor.DARK_GRAY}${ChatColor.BOLD}추방${ChatColor.RESET}${ChatColor.GRAY}"
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

    /**
     * @return 콤보
     */
    fun comboKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}콤보${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 콤보: 스킬의 조건에 따라 재사용할 수 있습니다.
     */
    fun comboLongKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}콤보${ChatColor.RESET}${ChatColor.GRAY}: 스킬의 조건에 따라 재사용할 수 있습니다."
    }

    /**
     * @return 탄환
     */
    fun shotKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}탄환${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 탄환: 특정 스킬 사용에 필요합니다.
     */
    fun shotLongKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}탄환${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 별자리
     */
    fun constellationKeyword(): String {
        return "${ChatColor.BLUE}${ChatColor.BOLD}별자리${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 행성: 항성 주위를 공전합니다.
     */
    fun planetLongKeyword(): String {
        return "${ChatColor.YELLOW}${ChatColor.BOLD}행성${ChatColor.RESET}${ChatColor.GRAY}: ${starKeyword()} 주위를 공전합니다."
    }

    /**
     * @return 별
     */
    fun starKeyword(): String {
        return "${ChatColor.BLUE}${ChatColor.BOLD}별${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 소환수
     */
    fun summonKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}소환수${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 무장 해제
     */
    fun disarmKeyword(): String {
        return "${ChatColor.DARK_GRAY}${ChatColor.BOLD}무장 해제${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 속박
     */
    fun restraintKeyword(): String {
        return "${ChatColor.DARK_GRAY}${ChatColor.BOLD}속박${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 집중
     */
    fun concentrationKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}집중${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 지연
     */
    fun delayKeyword(): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}지연${ChatColor.RESET}${ChatColor.GRAY}"
    }



    /**
     * @return 기타
     */
    fun goldKeyword(p: String): String {
        return "${ChatColor.GOLD}${ChatColor.BOLD}${p}${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 기타
     */
    fun greenKeyword(p: String): String {
        return "${ChatColor.GREEN}${ChatColor.BOLD}${p}${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 기타
     */
    fun darkGrayKeyword(p: String): String {
        return "${ChatColor.DARK_GRAY}${ChatColor.BOLD}${p}${ChatColor.RESET}${ChatColor.GRAY}"
    }

    /**
     * @return 기타
     */
    fun redKeyword(p: String): String {
        return "${ChatColor.RED}${ChatColor.BOLD}${p}${ChatColor.RESET}${ChatColor.GRAY}"
    }
}