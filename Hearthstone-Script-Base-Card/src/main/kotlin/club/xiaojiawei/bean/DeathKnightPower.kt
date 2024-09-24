package club.xiaojiawei.bean

import club.xiaojiawei.CardAction
import club.xiaojiawei.bean.abs.ClickPower

/**
 * 巫妖王技能
 * @author 肖嘉威
 * @date 2024/9/22 18:13
 */
open class DeathKnightPower : ClickPower() {

    override fun getCardId(): String {
        return "HERO_11bp"
    }

    override fun createNewInstance(): CardAction {
        return DeathKnightPower()
    }
}