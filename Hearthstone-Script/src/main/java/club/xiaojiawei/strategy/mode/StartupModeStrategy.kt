package club.xiaojiawei.strategy.mode

import club.xiaojiawei.strategy.AbstractModeStrategy

/**
 * 准备界面
 * @author 肖嘉威
 * @date 2023/7/6 16:28
 */
object StartupModeStrategy : AbstractModeStrategy<Any?>() {
    override fun wantEnter() {
    }

    override fun afterEnter(t: Any?) {
    }
}
