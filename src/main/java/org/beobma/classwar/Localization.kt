@file:Suppress("DEPRECATION")

package org.beobma.classwar

import org.beobma.classwar.util.KeywordMessage.abyssKeyword
import org.beobma.classwar.util.KeywordMessage.attackDamageKeyword
import org.beobma.classwar.util.KeywordMessage.attackSpeedKeyword
import org.beobma.classwar.util.KeywordMessage.bleedingKeyword
import org.beobma.classwar.util.KeywordMessage.brillianceKeyword
import org.beobma.classwar.util.KeywordMessage.burnKeyword
import org.beobma.classwar.util.KeywordMessage.damageReductionKeyword
import org.beobma.classwar.util.KeywordMessage.electricKeyword
import org.beobma.classwar.util.KeywordMessage.healKeyword
import org.beobma.classwar.util.KeywordMessage.invisibilityStateKeyword
import org.beobma.classwar.util.KeywordMessage.moveSpeedKeyword
import org.beobma.classwar.util.KeywordMessage.shieldKeyword
import org.beobma.classwar.util.KeywordMessage.slowKeyword
import org.beobma.classwar.util.KeywordMessage.stunKeyword
import org.beobma.classwar.util.KeywordMessage.untargetabilityKeyword
import org.beobma.classwar.util.KeywordMessage.vibrationExplosionKeyword
import org.beobma.classwar.util.KeywordMessage.vibrationKeyword
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class Class(
    val name: String,
    val description: List<String>,
    val weapon: ItemStack,
    val skill: List<ItemStack>,
    val item: List<ItemStack> = emptyList()
) {

    fun getSkillName(num: Int): String {
        return skill[num].itemMeta.displayName
    }

    fun getItemName(num: Int): String {
        return item[num].itemMeta.displayName
    }

    fun getItemLore(num: Int): List<String> {
        return item[num].itemMeta.lore as List<String>
    }
}

class LOCALIZATION {

    companion object {
        val nullpane = ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("${ChatColor.BOLD}${ChatColor.GRAY}비어있음")
            }
        }
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
        val wizardweapon = ItemStack(Material.WOODEN_SWORD, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("${ChatColor.BOLD}지팡이 대용 검")
                lore = arrayListOf(
                    "${ChatColor.GRAY}지팡이와 용도는 같으나 근접한 적에게 대적하기 위한 무기입니다."
                )
                isUnbreakable = true
            }
        }

        val priestsweapon = ItemStack(Material.WOODEN_SHOVEL, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("${ChatColor.BOLD}나무 타격봉")
                lore = arrayListOf(
                    "${ChatColor.GRAY}조잡하지만, 없는 것 보다는 낫습니다."
                )
                isUnbreakable = true
            }
        }

        val warlockweapon = ItemStack(Material.WOODEN_SWORD, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("${ChatColor.BOLD}레이피어")
                lore = arrayListOf(
                    "${ChatColor.GRAY}나무로 만든 레이피어입니다."
                )
                isUnbreakable = true
            }
        }

        private val woodensword = ItemStack(Material.WOODEN_SWORD, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("${ChatColor.BOLD}목검")
                lore = arrayListOf(
                    "${ChatColor.GRAY}호신용 검"
                )
                isUnbreakable = true
            }
        }
        val knightweapon = ItemStack(Material.IRON_SWORD, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("${ChatColor.BOLD}기사의 검")
                lore = arrayListOf(
                    "${ChatColor.GRAY}매우 날카로운 검."
                )
                isUnbreakable = true
            }
        }

        val berserker = Class("${ChatColor.BOLD}광전사", arrayListOf(
            "${ChatColor.YELLOW}근거리 전사", "", "광전사는 자신을 강화하는 스킬을 사용하며, 체력이 감소할수록 더 큰 피해를 입힙니다."
        ), ItemStack(Material.IRON_AXE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.BOLD}광전사의 도끼"
                ); lore = arrayListOf(
                "${ChatColor.GRAY}광전사가 가장 선호하는 도끼"
            )
            }
        }, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.BLUE}${ChatColor.BOLD}피의 분노"
                ); lore = arrayListOf(
                "${ChatColor.GRAY}4초 동안 ${attackSpeedKeyword(20)} 증가합니다."
            )
            }
        }, ItemStack(Material.RED_DYE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.YELLOW}${ChatColor.BOLD}라그나로크"
                ); lore = arrayListOf(
                "${ChatColor.DARK_RED}체력 5를 잃음",
                "",
                "${ChatColor.GRAY}8초 동안 ${moveSpeedKeyword(20)} 증가하고,",
                "${damageReductionKeyword(40)} 감소합니다."
            )
            }
        }, ItemStack(Material.WHITE_DYE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.RED}${ChatColor.BOLD}광전사의 의지"
                ); lore = arrayListOf(
                "${ChatColor.GRAY}잃은 체력에 비례해 ${attackDamageKeyword()}이 증가합니다.",
                "",
                "${ChatColor.DARK_GRAY}잃은 체력 1당 0.1씩 공격력이 증가합니다. (최대 2)",
            )
            }
        })
        )

        val archer = Class("${ChatColor.BOLD}궁수", arrayListOf(
            "${ChatColor.DARK_GREEN}원거리 사격형 딜러", "", "궁수는 화살을 소모하여 폭발적인 피해를 입힙니다."
        ), ItemStack(Material.BOW, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.BOLD}단궁"
                ); lore = arrayListOf(
                "${ChatColor.GRAY}중단거리 공격에 특화된 활"
            )
                isUnbreakable = true
            }
        }, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.BLUE}${ChatColor.BOLD}폭풍 화살"
                ); lore = arrayListOf(
                "${ChatColor.GOLD}화살 1개를 소모함", "", "${ChatColor.GRAY}화살을 발사하여 적중한 적에게 10의 피해를 입힙니다."
            )
            }
        }, ItemStack(Material.RED_DYE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.YELLOW}${ChatColor.BOLD}암살"
                ); lore = arrayListOf(
                "${ChatColor.GOLD}화살 3개를 소모함", "", "${ChatColor.GRAY}화살 3개를 동시에 발사하여 적중한 적에게 20의 피해를 입힙니다."
            )
            }
        }, ItemStack(Material.WHITE_DYE, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.RED}${ChatColor.BOLD}생존 본능"
                ); lore = arrayListOf(
                "${ChatColor.GRAY}게임당 한 번, 피격될 때 체력이 50% 이하로 내려간다면 그 공격을 무효로 하고 4초 동안 ${invisibilityStateKeyword()}가 됩니다."
            )
            }
        }), arrayListOf(ItemStack(Material.ARROW, 30).apply {
            itemMeta = itemMeta.apply {
                setDisplayName(
                    "${ChatColor.BOLD}화살"
                ); lore = arrayListOf(
                "${ChatColor.GRAY}활을 쏠 때 필요합니다."
            )
            }
        })
        )

        val firewizard = Class(
            "${ChatColor.BOLD}불마법사", arrayListOf(
                "${ChatColor.AQUA}근거리 마법사", "${ChatColor.BLUE}마나 사용자", "", "불마법사는 스킬로 적에게 화상을 입히며 큰 피해를 줍니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}발화"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 40을 소모함",
                    "",
                    "${ChatColor.GRAY}자신 주위 적에게 10의 피해를 입히고 ${burnKeyword(3)}을 부여합니다.",
                    "",
                    "${burnKeyword()}: 수치에 비례한 시간동안 불에 타오릅니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}불기둥"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}자신 위치에 마법진을 만듭니다.",
                    "${ChatColor.GRAY}3초 후 불기둥이 떨어지며 적중한 모든 대상에게 30의 피해를 입히고 ${burnKeyword(5)}를 부여합니다.",
                    "",
                    "${burnKeyword()}: 수치에 비례한 시간동안 불에 타오릅니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}화염 장막"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}자신이 피해를 입으면 피해를 입힌 대상에게 ${burnKeyword(1)}을 부여합니다.",
                    "",
                    "${burnKeyword()}: 수치에 비례한 시간동안 불에 타오릅니다."
                )
                }
            })
        )

        val waterwizard = Class(
            "${ChatColor.BOLD}물마법사", arrayListOf(
                "${ChatColor.GREEN}서포터 마법사", "${ChatColor.BLUE}마나 사용자", "", "물마법사는 스킬로 아군을 보조하고 상대의 이동을 방해합니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}물의 결계"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 40을 소모함",
                    "",
                    "${ChatColor.GRAY}가장 가까운 아군에게 3초간 ${shieldKeyword(4)}을 부여합니다.",
                    "${ChatColor.GRAY}보호막이 파괴되면 주위 적이 2초간 ${slowKeyword(15)}됩니다.",
                    "",
                    "${ChatColor.DARK_GRAY}혼자밖에 남지 않았다면 보호막은 자신에게 부여됩니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}파도"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}바라보는 방향에 파도를 일으킵니다.",
                    "${ChatColor.GRAY}파도는 구조물, 물에 닿을 때까지 나아가며 수평으로만 나아갑니다.",
                    "${ChatColor.GRAY}파도에 적중한 적은 3초간 ${slowKeyword(40)}되고 파도에 밀려납니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}해왕성"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 증가합니다."
                )
                }
            })
        )

        val timemanipulator = Class(
            "${ChatColor.BOLD}시간 조작자", arrayListOf(
                "${ChatColor.GREEN}유틸 서포터", "${ChatColor.GOLD}시간 사용자", "", "시간 조작자는 여러 방면에서 활용도가 높으며, 높은 숙련도가 요구됩니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}프로젝트-T"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}시간 2를 얻음", "", "${ChatColor.GRAY}2초간 ${shieldKeyword(4)}을 얻습니다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}시간의 특이점"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}시간 3을 소모함", "", "${ChatColor.GRAY}주위에 시간의 파동을 만들어 적중한 적에게 10의 피해를 입힙니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}Time Modifier"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}시간을 전부 소모함",
                    "",
                    "${ChatColor.GRAY}소모한 ${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY}만큼 아래의 효과를 얻습니다.",
                    "${ChatColor.GRAY}   -5   : 자신이 사망합니다.",
                    "${ChatColor.GRAY}   -4 ~ -1   : 자신이 피해 10을 입습니다.",
                    "${ChatColor.GRAY}    0   : 아무 효과가 없습니다.",
                    "${ChatColor.GRAY}    1 ~ 4   : 모든 적에게 3의 피해를 입힙니다.",
                    "${ChatColor.GRAY}    5   : 게임을 ${ChatColor.GREEN}${ChatColor.GOLD}초기 상태로 되돌리고${ChatColor.RESET}${ChatColor.GRAY} 아군의 ${
                        healKeyword(5)
                    }시킵니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}시간 조작자"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}시간 조작자는 ${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY} 상태이상을 얻거나 소모할 수 있습니다.",
                    "${ChatColor.GRAY}자신이 보유한 ${ChatColor.GOLD}${ChatColor.BOLD}시간${ChatColor.RESET}${ChatColor.GRAY}은 표시되지 않으며, -5 ~ 5까지 얻을 수 있습니다."
                )
                }
            })
        )

        val landwizard = Class(
            "${ChatColor.BOLD}대지 마법사", arrayListOf(
                "${ChatColor.DARK_BLUE}탱킹형 마법사", "${ChatColor.BLUE}마나 사용자", "", "대지 마법사는 적에게 진동 수치를 누적시켜 피해를 입힙니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}지진"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 20을 소모함",
                    "",
                    "${ChatColor.GRAY}주변에 지진을 일으켜 적중한 적에게 2의 피해를 입히고 ${vibrationKeyword(2)}를 부여합니다.",
                    "",
                    "${vibrationKeyword()}: ${vibrationExplosionKeyword()}이 적용되면 수치 x 2만큼 피해를 입고 수치를 초기화합니다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}탄성 반발"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 60을 소모함",
                    "",
                    "${ChatColor.GRAY}3초간 ${shieldKeyword(4)}을 얻고 주위 적에게 ${vibrationExplosionKeyword()}을 적용합니다.",
                    "",
                    "${vibrationKeyword()}: ${vibrationExplosionKeyword()}이 적용되면 수치 x 2만큼 피해를 입고 수치를 초기화합니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}대지진"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}맵 전체에 대지진을 일으켜 모든 적에게 ${vibrationExplosionKeyword()}을 적용하고 3의 피해를 입힙니다.",
                    "",
                    "${vibrationKeyword()}: ${vibrationExplosionKeyword()}이 적용되면 수치 x 2만큼 피해를 입고 수치를 초기화합니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}암석화"
                    ); lore = arrayListOf(
                    "${damageReductionKeyword(40)} 감소하고 지진의 ${ChatColor.BOLD}재사용 대기시간${ChatColor.RESET}${ChatColor.GRAY}이 감소합니다."
                )
                }
            })
        )

        val windwizard = Class(
            "${ChatColor.BOLD}바람 마법사", arrayListOf(
                "${ChatColor.GREEN}서포터 마법사", "${ChatColor.BLUE}마나 사용자", "", "바람 마법사는 아군의 이동을 돕고 상대의 이동을 억제하는 마법사입니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}윈드 필드"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 40을 소모함",
                    "",
                    "${ChatColor.GRAY}자신의 위치에 ${ChatColor.GOLD}${ChatColor.BOLD}대기${ChatColor.RESET}${ChatColor.GRAY}를 형성합니다.",
                    "${ChatColor.GOLD}${ChatColor.BOLD}대기${ChatColor.RESET}${ChatColor.GRAY}에 닿은 플레이어는 ${ChatColor.YELLOW}${ChatColor.BOLD}위로 떠오릅니다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}윈드 스톰"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 60을 소모함",
                    "",
                    "${ChatColor.GRAY}토네이도를 만들어 발사합니다.",
                    "${ChatColor.GRAY}토네이도는 주위 적을 끌어당겨 고정시킵니다.",
                    "${ChatColor.GRAY}이 스킬로 적을 전장 밖으로 밀어내면 적은 사망합니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}바람의 길"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}전장에 바람을 일으켜 5초간 모든 아군의 ${moveSpeedKeyword(40)} 증가합니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}대기 순환"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}자신이 낙하할 때 느리게 낙하합니다."
                )
                }
            })
        )

        val gravitationalmanipulator = Class(
            "${ChatColor.BOLD}중력 조작자", arrayListOf(
                "${ChatColor.GREEN}디버퍼", "${ChatColor.GOLD}중력 사용자", "", "중력 조작자는 중력을 변환하여 디버프를 적용합니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}중력 조작 - 반전"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}중력 3을 소모함",
                    "",
                    "${ChatColor.GRAY}3초간 전장의 ${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RESET}${ChatColor.GRAY}을 반전시킵니다.",
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}중력 조작 - 강하"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}중력 5를 소모함",
                    "",
                    "${ChatColor.GRAY}전장의 ${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RESET}${ChatColor.GRAY}을 극대화 시켜",
                    "${ChatColor.GRAY}모든 플레이어가 3초간 땅으로 하강합니다.",
                    "",
                    "${ChatColor.GRAY}적의 경우 5의 피해를 입으나, 공중에 떠있는 상태였다면 15의 피해를 입습니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}만유인력"
                    ); lore = arrayListOf(
                    "${ChatColor.GOLD}중력 10을 소모함", "", "${ChatColor.GRAY}바라보는 대상과 자신을 연결하여 서로 끌어당기게 합니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}중력 조작자"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}중력 조작자는 특수한 상태이상인 ${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RESET}${ChatColor.GRAY}을 사용합니다.",
                    "${ChatColor.GOLD}${ChatColor.BOLD}중력${ChatColor.RESET}${ChatColor.GRAY}은 0에 가까울수록 ${ChatColor.WHITE}${ChatColor.BOLD}점프 높이${ChatColor.RESET}${ChatColor.GRAY}가 증가합니다."
                )
                }
            })
        )

        val gambler = Class(
            "${ChatColor.BOLD}도박사", arrayListOf(
                "${ChatColor.YELLOW}특수 승리자", "", "도박사는 카드를 모아 특수 승리를 노립니다."
            ), woodensword, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}손패 섞기"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}보유한 카드중 ${ChatColor.YELLOW}${ChatColor.BOLD}특수 승리 카드${ChatColor.RESET}${ChatColor.GRAY}를 제외한 무작위 ${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY} 한 장을 버립니다.",
                    "${ChatColor.GRAY}이후 ${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY} 한 장을 뽑습니다.",
                    "",
                    "${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY}: 게임 시작시 덱에 카드 50장을 추가하고, 덱에서 카드 5장을 뽑아 게임을 시작한다.",
                    "${ChatColor.GRAY}52장중 5장은 ${ChatColor.YELLOW}${ChatColor.BOLD}특수 승리 카드${ChatColor.RESET}${ChatColor.GRAY}이며, 5장을 모두 뽑으면 승리한다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}버림패"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}보유한 카드중 ${ChatColor.YELLOW}${ChatColor.BOLD}특수 승리 카드${ChatColor.RESET}${ChatColor.GRAY}를 제외한 무작위 ${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY} 두 장을 버립니다.",
                    "${ChatColor.GRAY}이후 ${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY} 세 장을 뽑습니다.",
                    "",
                    "${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY}: 게임 시작시 덱에 카드 50장을 추가하고, 덱에서 카드 5장을 뽑아 게임을 시작한다.",
                    "${ChatColor.GRAY}52장중 5장은 ${ChatColor.YELLOW}${ChatColor.BOLD}특수 승리 카드${ChatColor.RESET}${ChatColor.GRAY}이며, 5장을 모두 뽑으면 승리한다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}도박수"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}현재 덱을 섞습니다.",
                    "${ChatColor.GRAY}이후 ${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY} 다섯 장을 뽑습니다.",
                    "",
                    "${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY}: 게임 시작시 덱에 카드 50장을 추가하고, 덱에서 카드 5장을 뽑아 게임을 시작한다.",
                    "${ChatColor.GRAY}52장중 5장은 ${ChatColor.YELLOW}${ChatColor.BOLD}특수 승리 카드${ChatColor.RESET}${ChatColor.GRAY}이며, 5장을 모두 뽑으면 승리한다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}선택과 집중"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}완벽한 승리를 위해 일부 카드를 버립니다.",
                    "${ChatColor.GRAY}카드를 버릴 때마다 아래의 효과중 무작위 효과를 얻습니다.",
                    "",
                    "${ChatColor.GRAY}3초간 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도가 20%${ChatColor.RESET}${ChatColor.GRAY} 증가함",
                    "${ChatColor.GOLD}${ChatColor.BOLD}카드${ChatColor.RESET}${ChatColor.GRAY}를 한 장 뽑음",
                    "${ChatColor.GREEN}${ChatColor.BOLD}체력 3${ChatColor.RESET}${ChatColor.GRAY}을 회복함"
                )
                }
            })
        )

        val knight = Class(
            "${ChatColor.BOLD}기사", arrayListOf(
                "${ChatColor.YELLOW}근거리 전사", "", "전사는 검으로 출혈을 일으켜 지속 피해를 입힙니다."
            ), woodensword, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}내려치기"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}2칸 내의 바라보는 적에게 7의 피해를 입히고 ${bleedingKeyword(3)}을 부여합니다.",
                    "",
                    "${bleedingKeyword()}: 3초간 달릴 때 수치에 비례한 피해를 입습니다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}흘리기"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}공격 받기 직전에 상대의 공격을 흘려냅니다.",
                    "${ChatColor.GRAY}완벽히 흘려내면 상대는 2초간 ${stunKeyword()}합니다.",
                    "${ChatColor.GRAY}흘려내지 못하면 다음 공격의 피해를 2배로 받습니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}빈틈 찌르기"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}5칸 내의 바라보는 적에게 돌진하여 검을 내지릅니다.",
                    "${ChatColor.GRAY}대상은 10의 피해를 입히나, 대상이 ${stunKeyword()} 상태였다면 20의 피해를 입히고 ${bleedingKeyword(6)}을 부여합니다.",
                    "",
                    "${bleedingKeyword()}: 3초간 달릴 때 수치에 비례한 피해를 입습니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}피로 벼려낸 검"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}기본 공격이 적중하면 대상에게 ${bleedingKeyword(1)}을 부여합니다.",
                    "",
                    "${bleedingKeyword()}: 3초간 달릴 때 수치에 비례한 피해를 입습니다."

                )
                }
            })
        )

        val spaceoperator = Class(
            "${ChatColor.BOLD}공간 조작자", arrayListOf(
                "${ChatColor.YELLOW}근거리 전사", "${ChatColor.BLUE}충전 사용자", "", "공간 조작자는 충전을 모아 스킬을 강화하여 피해를 입힙니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}절단"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전 40을 소모하여 스킬을 ${ChatColor.YELLOW}${ChatColor.BOLD}강화${ChatColor.RESET}${ChatColor.BLUE}함",
                    "",
                    "${ChatColor.GRAY}2칸 내의 바라보는 대상을 베어 적중한 적에게 10의 피해를 입힙니다.",
                    "",
                    "${ChatColor.YELLOW}${ChatColor.BOLD}강화${ChatColor.RESET}${ChatColor.GRAY}: 4칸 내의 바라보는 대상을 베어 적중한 적에게 15의 피해를 입힙니다.",
                    "",
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}: 웅크려서 충전하고, 소모하여 스킬의 위력을 높입니다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}도약"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전 40을 소모하여 스킬을 ${ChatColor.YELLOW}${ChatColor.BOLD}강화${ChatColor.RESET}${ChatColor.BLUE}함",
                    "",
                    "${ChatColor.GRAY}바라보는 방향으로 4칸 도약힙니다.",
                    "${ChatColor.GRAY}도약 중지 지점에 있는 적에게 7의 피해를 입힙니다.",
                    "",
                    "${ChatColor.YELLOW}${ChatColor.BOLD}강화${ChatColor.RESET}${ChatColor.GRAY}: 도약 거리 사이에 있는 모든 적에게 10의 피해를 입힙니다.",
                    "",
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}: 웅크려서 충전하고, 소모하여 스킬의 위력을 높입니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}공간 조작"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전 100을 소모하고 ${ChatColor.RED}${ChatColor.BOLD}더 이상 충전을 얻을 수 없음.",
                    "",
                    "${ChatColor.GRAY}공간을 조작하여 바라보는 대상과 함께 7초 동안 독립된 공간으로 이동합니다.",
                    "",
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}: 웅크려서 충전하고, 소모하여 스킬의 위력을 높입니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}에너지 보존과 전환"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}을 소모할 때 사용하는 에너지의 일부를 전환합니다.",
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}을 소모하면 3초간 ${ChatColor.GOLD}${ChatColor.BOLD}공격력${ChatColor.RESET}${ChatColor.GRAY}이 증가합니다.",
                    "",
                    "${ChatColor.BLUE}${ChatColor.BOLD}충전${ChatColor.RESET}${ChatColor.GRAY}: 웅크려서 충전하고, 소모하여 스킬의 위력을 높입니다."
                )
                }
            })
        )

        val lightningwizard = Class(
            "${ChatColor.BOLD}번개 마법사", arrayListOf(
                "${ChatColor.BLUE}지역 점거형 마법사", "${ChatColor.BLUE}마나 사용자", "", "번개 마법사는 표식을 생성하고 표식에 있는 적에게 피해를 입힙니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}적란운"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 20을 소모함",
                    "",
                    "${ChatColor.GRAY}현재 위치에 ${ChatColor.GOLD}${ChatColor.BOLD}표식${ChatColor.RESET}${ChatColor.GRAY}을 최대 3개까지 생성합니다.",
                    "${ChatColor.GRAY}이미 3개를 설치했다면 가장 나중에 설치한 ${ChatColor.GOLD}${ChatColor.BOLD}표식${ChatColor.RESET}${ChatColor.GRAY}을 제거하고 설치합니다."
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}낙뢰"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 40을 소모함",
                    "",
                    "${ChatColor.GOLD}${ChatColor.BOLD}표식${ChatColor.RESET}${ChatColor.GRAY}에 낙뢰를 떨어트립니다.",
                    "${ChatColor.GRAY}낙뢰에 적중한 적에게 5의 피해를 입히고 ${electricKeyword()}을 부여합니다.",
                    "",
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}감전${ChatColor.RESET}${ChatColor.GRAY}: 10초 후 사라집니다.",
                    "${ChatColor.GRAY}지속 시간 동안 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 약간 감소합니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}천뢰"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}모든 ${ChatColor.GOLD}${ChatColor.BOLD}표식${ChatColor.RESET}${ChatColor.GRAY}을 파괴합니다.",
                    "${ChatColor.GRAY}표식 주위에 있던 적에게 10의 피해를 입힙니다.",
                    "",
                    "${ChatColor.GRAY}만약, 대상에게 ${electricKeyword()}이 있다면 ${electricKeyword()}을 다시 적용하고",
                    "${ChatColor.GRAY}대상 주위 적에게 5의 피해를 입히며 그 적에게도 ${electricKeyword()}을 적용합니다.",
                    "",
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}감전${ChatColor.RESET}${ChatColor.GRAY}: 10초 후 사라집니다.",
                    "${ChatColor.GRAY}지속 시간 동안 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 약간 감소합니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}암페어"
                    ); lore = arrayListOf(
                    "${electricKeyword()} 상태인 적에게 다시 ${electricKeyword()}이 적용되면 3초간 ${stunKeyword()}합니다.",
                    "",
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}감전${ChatColor.RESET}${ChatColor.GRAY}: 10초 후 사라집니다.",
                    "${ChatColor.GRAY}지속 시간 동안 ${ChatColor.GOLD}${ChatColor.BOLD}이동 속도${ChatColor.RESET}${ChatColor.GRAY}가 약간 감소합니다."
                )
                }
            })
        )

        val lightwizard = Class(
            "${ChatColor.BOLD}빛 마법사", arrayListOf(
                "${ChatColor.BLUE}누킹형 마법사", "${ChatColor.BLUE}마나 사용자", "", "빛 마법사는 적의 위치를 밝히고 지속적 피해를 입힙니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}제네시스"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 40을 소모함",
                    "",
                    "${ChatColor.GRAY}바라보는 적에게 ${brillianceKeyword()}를 적용합니다.",
                    "${ChatColor.GRAY}이미 적용된 적에게는 10의 피해를 입힙니다.",
                    "",
                    "${brillianceKeyword()}: 5초간 위치를 드러냅니다.",
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}빛의 광선"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 40을 소모함",
                    "",
                    "${ChatColor.GRAY}바라보는 방향으로 3초간 광선을 발사합니다.",
                    "${ChatColor.GRAY}적중한 적에게 초당 4의 피해를 입히고 ${brillianceKeyword()}를 적용합니다.",
                    "",
                    "${ChatColor.DARK_GRAY}광선을 발사하는 도중 다른 스킬을 사용할 수 없습니다.",
                    "",
                    "${brillianceKeyword()}: 5초간 유지되며 유지 시간동안 위치를 드러냅니다.",
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}빅뱅"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}전장에 태초의 빛을 구현합니다.",
                    "${ChatColor.GRAY}모든 적에게 ${ChatColor.WHITE}${ChatColor.BOLD}광휘 상태인 적의 수에 비례하여${ChatColor.RESET}${ChatColor.GRAY} 최대 20의 피해를 입힙니다.",
                    "",
                    "${brillianceKeyword()}: 5초간 유지되며 유지 시간동안 위치를 드러냅니다.",
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}루멘"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}어두운 곳을 밝게 볼 수 있습니다."
                )
                }
            })
        )

        val darkwizard = Class(
            "${ChatColor.BOLD}어둠 마법사", arrayListOf(
                "${ChatColor.GREEN}디버퍼", "${ChatColor.BLUE}마나 사용자", "", "어둠 마법사는 적의 색적 능력을 떨어트립니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}검은 연기"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 40을 소모함",
                    "",
                    "${ChatColor.GRAY}자신 위치에 3초간 유지되는 ${ChatColor.GOLD}${ChatColor.BOLD}연기${ChatColor.RESET}${ChatColor.GRAY}를 생성합니다.",
                    "${ChatColor.GOLD}${ChatColor.BOLD}연기${ChatColor.RESET}${ChatColor.GRAY}에 적이 들어오면 그 적은 모습이 드러납니다.",
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}잠식"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 60을 소모함",
                    "",
                    "${ChatColor.GRAY}바라보는 방향으로 잠식된 연기를 발사합니다.",
                    "${ChatColor.GRAY}적중한 적에게 10의 피해를 입히고 ${abyssKeyword()}을 적용합니다.",
                    "",
                    "${abyssKeyword()}: 2초간 시야가 극도로 좁아집니다.",
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}심연의 공포"
                    ); lore = arrayListOf(
                    "${ChatColor.BLUE}${ChatColor.BOLD}마나 100을 소모함",
                    "",
                    "${ChatColor.GRAY}5초간 전장을 연기로 가득 채웁니다.",
                    "${ChatColor.GRAY}한 번이라도 ${abyssKeyword()}상태였던 적은 ${ChatColor.GOLD}${ChatColor.BOLD}공포${ChatColor.RESET}${ChatColor.GRAY} 상태가 됩니다.",
                    "",
                    "${abyssKeyword()}: 2초간 시야가 극도로 좁아집니다.",
                    "${ChatColor.GOLD}${ChatColor.BOLD}공포${ChatColor.RESET}${ChatColor.GRAY}: ${abyssKeyword()}의 지속 시간이 증가하며 침묵되고, 알 수 없는 소리가 들립니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}칸델라"
                    ); lore = arrayListOf(
                    "${abyssKeyword()} 상태인 적은 달릴 수 없고 치명타 공격 또한 봉쇄됩니다.",
                    "",
                    "${abyssKeyword()}: 2초간 시야가 극도로 좁아집니다.",
                )
                }
            })
        )

        val priests = Class(
            "${ChatColor.BOLD}사제", arrayListOf(
                "${ChatColor.YELLOW}치유형 서포터", "", "아군을 치유합니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}치유"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}바라보는 아군의 ${healKeyword(5)}시킵니다.",
                    "",
                    "${ChatColor.DARK_GRAY}바라보는 대상이 없으면 자신에게 시전합니다.",
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}정화"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}바라보는 아군을 정화합니다.",
                    "${ChatColor.GRAY}해당 아군에게 적용된 기절, 공포를 해제합니다.",
                    "",
                    "${ChatColor.DARK_GRAY}바라보는 대상이 없으면 자신에게 시전합니다.",
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}죽음 방비"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}바라보는 아군은 3초간 사망하지 않으며 이후 3초간 ${untargetabilityKeyword()} 상태가 됩니다.",
                    "",
                    "${untargetabilityKeyword()}: 스킬의 대상이 될 수 없습니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}기도"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}게임 시작시 모든 아군이 ${shieldKeyword(4)}을 얻고 게임을 시작합니다."
                )
                }
            })
        )

        val warlock = Class(
            "${ChatColor.BOLD}워락", arrayListOf(
                "${ChatColor.GRAY}암흑 계열 마법사", "", "워락은 자신의 생명력을 대가로 강력한 마법을 사용합니다."
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}저주"
                    ); lore = arrayListOf(
                    "${ChatColor.DARK_RED}체력 10을 잃음",
                    "",
                    "${ChatColor.GRAY}바라보는 대상에게 5초간 ${ChatColor.RED}${ChatColor.BOLD}치명적 상처${ChatColor.RESET}${ChatColor.GRAY}를 적용합니다.",
                    "",
                    "${ChatColor.RED}${ChatColor.BOLD}치명적 상처${ChatColor.RESET}${ChatColor.GRAY}: ${
                        damageReductionKeyword(10)
                    } 증가합니다.",
                    "${ChatColor.GRAY}이 효과가 적용중인 대상이 받는 피해의 절반 만큼 워락은 ${healKeyword()}합니다.",
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}역병"
                    ); lore = arrayListOf(
                    "${ChatColor.DARK_RED}체력 10을 잃음",
                    "",
                    "${ChatColor.GRAY}바라보는 블럭에 역병을 일으킵니다.",
                    "",
                    "${ChatColor.GRAY}역병이 일어난 지역에 들어온 모든 대상은 초당 8의 피해를 입습니다.",
                    "",
                    "${ChatColor.DARK_GRAY}이미 역병을 사용한 상태라면 원래 역병 지역을 제거하고 다시 생성합니다.",
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}죽음의 원"
                    ); lore = arrayListOf(
                    "${ChatColor.DARK_RED}체력 20을 잃음",
                    "",
                    "${ChatColor.GRAY}바라보는 대상에게 10초간 죽음의 원을 만듭니다.",
                    "",
                    "${ChatColor.GRAY}죽음의 원을 기준으로 파동이 펼쳐지며 이 파동은 시간이 지날수록 점점 커집니다.",
                    "${ChatColor.GRAY}파동에 닿은 대상은 초당 4의 피해를 입으며 자신은 이 스킬로 입힌 피해의 절반 만큼 ${healKeyword()}합니다.",
                    "",
                    "${ChatColor.DARK_GRAY}바라보는 대상이 없다면 자신에게 시전합니다.",
                    "${ChatColor.DARK_GRAY}죽음의 원의 기준점이 되는 대상은 피해를 입지 않습니다.",
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}저편의 계약"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}스킬 시전시 소모값을 지불할 수 없다면 게임당 최대 2번, 소모값을 무시하고 스킬을 사용할 수 있습니다.",
                )
                }
            })
        )

        val mathematician = Class(
            "${ChatColor.BOLD}수학자", arrayListOf(
                "${ChatColor.GRAY}학문 계열 마법사", "", "수학자는 공간 구조의 원리를 이용하여 공격합니다. 상당한 숙련도를 요구합니다., "
            ), wizardweapon, arrayListOf(ItemStack(Material.BLACK_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.BLUE}${ChatColor.BOLD}좌표축 설정"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}바라보는 블럭에 좌표축을 설정합니다.",
                    "${ChatColor.GRAY}좌표축은 최대 2개까지 존재할 수 있습니다.",
                    "",
                    "${ChatColor.DARK_GRAY}단, 좌표축과 좌표축 사이를 선분으로 이어 직육면체로 만들었을 때, 부피가 125를 초과할 수 없습니다.",
                    "${ChatColor.DARK_GRAY}부피는 X축의 길이 x Y축의 길이 x Z축의 길이입니다.",
                    "${ChatColor.DARK_GRAY}이미 최대로 설정했다면, 가장 나중에 설정한 좌표축을 제거하고 다시 설정합니다.",
                )
                }
            }, ItemStack(Material.ORANGE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.GREEN}${ChatColor.BOLD}직육면체"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}좌표축에 선분을 이어 붙여 직육면체로 만듭니다.",
                    "${ChatColor.GRAY}직육면체 내부에 존재하는 적에게 ${ChatColor.GOLD}${ChatColor.BOLD}직육면체의 부피 / 5 만큼의 피해${ChatColor.RESET}${ChatColor.GRAY}를 입힙니다. (최대 25)",
                    "${ChatColor.GRAY}이 스킬의 종료 이후에도 직육면체는 그대로 남아있으나, 효과가 중복 적용되지 않습니다."
                )
                }
            }, ItemStack(Material.RED_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}직육면체 전개"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}현재 존재하는 모든 직육면체를 전개하고 제거합니다.",
                    "${ChatColor.GRAY}직육면체 내부에 존재하던 적에게 ${ChatColor.GOLD}${ChatColor.BOLD}모든 직육면체의 개수 x 3${ChatColor.RESET}${ChatColor.GRAY}의 피해를 입힙니다."
                )
                }
            }, ItemStack(Material.WHITE_DYE, 1).apply {
                itemMeta = itemMeta.apply {
                    setDisplayName(
                        "${ChatColor.RED}${ChatColor.BOLD}난제"
                    ); lore = arrayListOf(
                    "${ChatColor.GRAY}스킬로 생성되는 직육면체가 겹쳐지면 모든 좌표축을 제거하고 그 두 직육면체를 제거합니다.",
                )
                }
            })
        )

        val classList: MutableList<String> = mutableListOf(
            berserker.name,
            archer.name,
            firewizard.name,
            waterwizard.name,
            timemanipulator.name,
            landwizard.name,
            windwizard.name,
            gravitationalmanipulator.name,
            gambler.name,
            knight.name,
            spaceoperator.name,
            lightningwizard.name,
            lightwizard.name,
            darkwizard.name,
            priests.name,
            warlock.name,
            mathematician.name
        )
    }
}