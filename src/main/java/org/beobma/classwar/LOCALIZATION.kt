@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.bukkit.ChatColor

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
        )

        val berserker_name: String = "${ChatColor.BOLD}광전사"
        val berserker_lore: List<String> = arrayListOf(
            "${ChatColor.YELLOW}근거리 전사",
            "",
            "광전사는 자신을 강화하는 스킬을 사용하며, 체력이 낮을수록 더 큰 피해를 줍니다."
        )

        val berserker_weapon_name: String = "${ChatColor.BOLD}광전사의 도끼"
        val berserker_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}광전사가 가장 애용하는 도끼"
        )
        val berserker_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}피의 분노"
        val berserker_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}소모값 없음",
            "",
            "${ChatColor.GRAY}4초 동안 ${ChatColor.YELLOW}${ChatColor.BOLD}공격 속도가 20%${ChatColor.RESET}${ChatColor.GRAY} 빨라집니다."
        )
        val berserker_skill2_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}라그나로크"
        val berserker_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.DARK_RED}체력 5를 잃음",
            "",
            "${ChatColor.GRAY}8초 동안 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도가 20%${ChatColor.RESET}${ChatColor.GRAY}증가하고",
            "${ChatColor.YELLOW}${ChatColor.BOLD}받는 피해가 40%${ChatColor.RESET}${ChatColor.GRAY}가 감소합니다."
        )
        val berserker_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}광전사의 의지"
        val berserker_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}현재 체력에 비례해 ${ChatColor.GOLD}${ChatColor.BOLD}공격력${ChatColor.RESET}${ChatColor.GRAY}이 증가합니다.",
            "",
            "${ChatColor.DARK_GRAY}최대 체력에서의 차이 1당 0.1씩 증가합니다 (최대 2)",
        )










        val archer_name: String = "${ChatColor.BOLD}궁수"
        val archer_lore: List<String> = arrayListOf(
            "${ChatColor.DARK_GREEN}원거리 사격형 딜러",
            "",
            "궁수는 화살을 소모하여 폭발적인 피해를 입힙니다."
        )

        val archer_weapon_name: String = "${ChatColor.BOLD}단궁"
        val archer_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}중단거리 공격에 특화된 활"
        )
        val archer_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}폭풍 화살"
        val archer_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}화살 1개를 소모함",
            "",
            "${ChatColor.GRAY}화살을 발사하여 적중한 적에게 10의 피해를 입힙니다."
        )
        val archer_skill2_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}암살"
        val archer_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.DARK_RED}화살 3개를 소모함",
            "",
            "${ChatColor.GRAY}화살 3개를 동시에 발사하여 적중한 적에게 20의 피해를 입힙니다.",
        )
        val archer_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}생존 본능"
        val archer_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}게임당 한 번, 체력이 50% 이하로 내려가면 4초동안 ${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}투명 상태${ChatColor.RESET}${ChatColor.GRAY}가 됩니다.",
        )
        val archer_arrow_name: String = "${ChatColor.BOLD}화살"
        val archer_arrow_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}활을 쏠 때 필요합니다.",
        )








        val fire_wizard_name: String = "${ChatColor.BOLD}불마법사"
        val fire_wizard_lore: List<String> = arrayListOf(
            "${ChatColor.AQUA}근거리 마법사",
            "${ChatColor.BLUE}마나 사용자",
            "",
            "불마법사는 스킬로 상대에게 화상을 입히며 큰 피해를 줍니다."
        )
        val fire_wizard_weapon_name: String = "${ChatColor.BOLD}지팡이 대용 검"
        val fire_wizard_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}지팡이와 용도는 같으나 근접한 적에게 대적하기 위한 무기입니다."
        )
        val fire_wizard_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}발화"
        val fire_wizard_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 40을 소모함",
            "",
            "${ChatColor.GRAY}자신 주위에 있는 적에게 10의 피해를 주고 ${ChatColor.RED}${ChatColor.BOLD}화상 3${ChatColor.RESET}${ChatColor.GRAY}을 부여합니다.",
            "",
            "${ChatColor.RED}${ChatColor.BOLD}화상 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 피해를 입고 사라진다."
        )
        val fire_wizard_skill2_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}불기둥"
        val fire_wizard_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 100을 소모함",
            "",
            "${ChatColor.GRAY}자신 위치에 마법진을 만듭니다.",
            "${ChatColor.GRAY}3초 후 불기둥이 떨어지며 적중한 모든 대상은 30의 피해를 입고 ${ChatColor.RED}${ChatColor.BOLD}화상 5${ChatColor.RESET}${ChatColor.GRAY}을 얻습니다.",
            "",
            "${ChatColor.RED}${ChatColor.BOLD}화상 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 피해를 입고 사라진다."

        )
        val fire_wizard_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}화염일섬"
        val fire_wizard_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}자신이 피해를 입으면 피해를 입힌 대상의 ${ChatColor.RED}${ChatColor.BOLD}화상에 의해 피해를 입는 시간${ChatColor.RESET}${ChatColor.GRAY}이 단축됩니다.",
            "",
            "${ChatColor.RED}${ChatColor.BOLD}화상 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 피해를 입고 사라진다."
        )













        val water_wizard_name: String = "${ChatColor.BOLD}물마법사"
        val water_wizard_lore: List<String> = arrayListOf(
            "${ChatColor.GREEN}서포터 마법사",
            "${ChatColor.BLUE}마나 사용자",
            "",
            "물마법사는 아군에게 보호막을 제공하고 적의 이동을 방해합니다."
        )
        val water_wizard_weapon_name: String = "${ChatColor.BOLD}지팡이 대용 검"
        val water_wizard_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}지팡이와 용도는 같으나 근접한 적에게 대적하기 위한 무기입니다."
        )
        val water_wizard_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}물의 결계"
        val water_wizard_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 40을 소모함",
            "",
            "${ChatColor.GRAY}가장 가까운 아군에게 3초간 유지되는 ${ChatColor.AQUA}${ChatColor.BOLD}보호막${ChatColor.RESET}${ChatColor.GRAY}을 보냅니다.",
            "${ChatColor.GRAY}보호막이 파괴되거나 사라지면 주위 적에게 ${ChatColor.AQUA}${ChatColor.BOLD}침잠 3${ChatColor.RESET}${ChatColor.GRAY}을 부여합니다.",
            "",
            "${ChatColor.AQUA}${ChatColor.BOLD}침잠 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 감소합니다."
        )
        val water_wizard_skill2_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}파도"
        val water_wizard_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 100을 소모함",
            "",
            "${ChatColor.GRAY}파도를 일으킵니다.",
            "${ChatColor.GRAY}파도는 구조물, 물에 닿을 때까지 나아가며 수평으로만 나아갑니다.",
            "${ChatColor.GRAY}이에 닿은 적은 초당 ${ChatColor.AQUA}${ChatColor.BOLD}침잠 3${ChatColor.RESET}${ChatColor.GRAY}을 얻으며 파도와 함께 이동합니다.",
            "",
            "${ChatColor.AQUA}${ChatColor.BOLD}침잠 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 감소합니다."

        )
        val water_wizard_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}해왕성"
        val water_wizard_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 증가합니다.",
        )









        val time_manipulator_name: String = "${ChatColor.BOLD}시간 조작자"
        val time_manipulator_lore: List<String> = arrayListOf(
            "${ChatColor.GREEN}유틸 서포터",
            "${ChatColor.GOLD}시간 사용자",
            "",
            "시간 조작자는 여러 방면에서 활용도가 높으며, 높은 숙련도가 요구됩니다."
        )
        val time_manipulator_weapon_name: String = "${ChatColor.BOLD}지팡이 대용 검"
        val time_manipulator_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}지팡이와 용도는 같으나 근접한 적에게 대적하기 위한 무기입니다."
        )
        val time_manipulator_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}프로젝트-T"
        val time_manipulator_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.GOLD}시간 2를 얻음",
            "",
            "${ChatColor.GRAY}3초간 유지되며 ${ChatColor.AQUA}${ChatColor.BOLD}8의 피해를 흡수하는 보호막${ChatColor.RESET}${ChatColor.GRAY}을 얻습니다.",
        )

        val time_manipulator_skill2_name: String = "${ChatColor.GREEN}${ChatColor.BOLD}시간의 특이점"
        val time_manipulator_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.GOLD}시간 3를 소모함",
            "",
            "${ChatColor.GRAY}주위에 시간의 파동을 만들어 적에게 10의 피해를 입힙니다.",

        )

        val time_manipulator_skill3_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}Time Modifier"
        val time_manipulator_skill3_lore: List<String> = arrayListOf(
            "${ChatColor.GOLD}시간을 전부 소모함",
            "",
            "${ChatColor.GRAY}소모한 ${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY}만큼 아래의 효과를 얻습니다.",
            "${ChatColor.GRAY}-5 : 자신이 사망합니다.",
            "${ChatColor.GRAY}-4 ~ -1 : 자신이 피해 10을 입습니다.",
            "${ChatColor.GRAY}0 : 아무 효과가 없습니다.",
            "${ChatColor.GRAY}1 ~ 4 : 모든 적에게 5의 피해를 입힙니다.",
            "${ChatColor.GRAY}5 : 모든 아군과 적의 위치를 초기화하고 아군의${ChatColor.GREEN}${ChatColor.BOLD}체력을 회복${ChatColor.RESET}${ChatColor.GRAY}시킵니다."


        )

        val time_manipulator_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}시간 조작자"
        val time_manipulator_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}시간 조작자는 ${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY} 상태이상을 얻거나 소모할 수 있습니다.",
            "${ChatColor.GRAY}자신이 보유한 ${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY}은 표시되지 않으며, -5 ~ 5까지 얻을 수 있습니다."
        )










        val land_wizard_name: String = "${ChatColor.BOLD}대지 마법사"
        val land_wizard_lore: List<String> = arrayListOf(
            "${ChatColor.DARK_BLUE}탱킹형 딜러",
            "${ChatColor.BLUE}마나 사용자",
            "",
            "대지 마법사는 전방에서 상대의 공격을 맞으며 상대에게 진동 수치를 누적시켜 피해를 입힙니다."
        )
        val land_wizard_weapon_name: String = "${ChatColor.BOLD}지팡이 대용 검"
        val land_wizard_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}지팡이와 용도는 같으나 근접한 적에게 대적하기 위한 무기입니다."
        )
        val land_wizard_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}지진"
        val land_wizard_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 40을 소모함",
            "",
            "${ChatColor.GRAY}주변에 지진을 일으켜 상대에게 2의 피해를 입히고 ${ChatColor.GOLD}${ChatColor.BOLD}진동 2${ChatColor.RESET}${ChatColor.GRAY}를 적용합니다.",
            "",
            "${ChatColor.GOLD}${ChatColor.BOLD}진동 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 사라집니다.",
            "${ChatColor.GRAY}그 전에 ${ChatColor.GOLD}${ChatColor.BOLD}진동 폭발${ChatColor.RESET}${ChatColor.GRAY}이 적용되면 수치 x 2만큼 피해를 입습니다."
        )

        val land_wizard_skill2_name: String = "${ChatColor.GREEN}${ChatColor.BOLD}탄성 반발"
        val land_wizard_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 60을 소모함",
            "",
            "${ChatColor.GRAY}보호막을 얻고 주위 적에게 ${ChatColor.GOLD}${ChatColor.BOLD}진동 폭발${ChatColor.RESET}${ChatColor.GRAY}을 적용합니다.",
            "",
            "${ChatColor.GOLD}${ChatColor.BOLD}진동 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 사라집니다.",
            "${ChatColor.GRAY}그 전에 ${ChatColor.GOLD}${ChatColor.BOLD}진동 폭발${ChatColor.RESET}${ChatColor.GRAY}이 적용되면 수치 x 2만큼 피해를 입습니다."

        )

        val land_wizard_skill3_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}대지진"
        val land_wizard_skill3_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 100을 소모함",
            "",
            "${ChatColor.GRAY}맵 전체에 대지진을 일으켜 모든 적에게 ${ChatColor.GOLD}${ChatColor.BOLD}진동 폭발${ChatColor.RESET}${ChatColor.GRAY}을 적용하고 7의 피해를 입힙니다.",
            "",
            "${ChatColor.GOLD}${ChatColor.BOLD}진동 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 사라집니다.",
            "그 전에 ${ChatColor.GOLD}${ChatColor.BOLD}진동 폭발${ChatColor.RESET}${ChatColor.GRAY}이 적용되면 수치 x 2만큼 피해를 입습니다."


        )

        val land_wizard_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}암석화"
        val land_wizard_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GOLD}${ChatColor.BOLD}받는 피해가 40%${ChatColor.RESET}${ChatColor.GRAY}감소하고 지진의 재사용 대기시간이 감소합니다."
        )








        val wind_wizard_name: String = "${ChatColor.BOLD}바람 마법사"
        val wind_wizard_lore: List<String> = arrayListOf(
            "${ChatColor.GREEN}이동 보조형 서포터",
            "${ChatColor.BLUE}마나 사용자",
            "",
            "바람 마법사는 아군의 이동을 돕는 서포터입니다."
        )
        val wind_wizard_weapon_name: String = "${ChatColor.BOLD}지팡이 대용 검"
        val wind_wizard_weapon_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}지팡이와 용도는 같으나 근접한 적에게 대적하기 위한 무기입니다."
        )
        val wind_wizard_skill1_name: String = "${ChatColor.BLUE}${ChatColor.BOLD}윈드 필드"
        val wind_wizard_skill1_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 40을 소모함",
            "",
            "${ChatColor.GRAY}자신의 위치에 대기를 형성합니다.",
            "${ChatColor.GRAY}대기에 위치한 플레이어는 ${ChatColor.YELLOW}${ChatColor.BOLD}위로 떠오릅니다."
        )

        val wind_wizard_skill2_name: String = "${ChatColor.GREEN}${ChatColor.BOLD}윈드 스톰"
        val wind_wizard_skill2_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 60을 소모함",
            "",
            "${ChatColor.GRAY}토네이도를 만들어 발사합니다.",
            "${ChatColor.GRAY}토네이도는 빠르게 움직이다 느려지며, 상대를 끌어들이고 피해를 입히며, ${ChatColor.AQUA}${ChatColor.BOLD}파열 2${ChatColor.RESET}${ChatColor.GRAY}를 부여합니다.",
            "${ChatColor.GRAY}이 스킬로 상대를 전장 밖으로 밀어내면 상대는 게임에서 제외됩니다.",
            "",
            "${ChatColor.AQUA}${ChatColor.BOLD}파열 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 기본공격의 데미지가 감소합니다."
        )

        val wind_wizard_skill3_name: String = "${ChatColor.YELLOW}${ChatColor.BOLD}바람의 길"
        val wind_wizard_skill3_lore: List<String> = arrayListOf(
            "${ChatColor.BLUE}마나 100을 소모함",
            "",
            "${ChatColor.GRAY}전장에 바람을 일으켜 모든 아군의 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도가 40%${ChatColor.RESET}${ChatColor.GRAY}증가하고",
            "${ChatColor.GRAY}상대에게는 ${ChatColor.AQUA}${ChatColor.BOLD}파열 2${ChatColor.RESET}${ChatColor.GRAY}를 부여합니다.",
            "",
            "${ChatColor.AQUA}${ChatColor.BOLD}파열 : ${ChatColor.RESET}${ChatColor.GRAY}10초 후 수치만큼 기본공격의 데미지가 감소합니다."


        )

        val wind_wizard_passiveskill_name: String = "${ChatColor.RED}${ChatColor.BOLD}대기 순환"
        val wind_wizard_passiveskill_lore: List<String> = arrayListOf(
            "${ChatColor.GRAY}자신이 낙하할 때 느리게 낙하합니다."
        )

    }
}