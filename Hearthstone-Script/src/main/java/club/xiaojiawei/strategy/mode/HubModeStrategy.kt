package club.xiaojiawei.strategy.mode

import club.xiaojiawei.bean.GameRect
import club.xiaojiawei.config.log
import club.xiaojiawei.enums.ModeEnum
import club.xiaojiawei.status.DeckStrategyManager
import club.xiaojiawei.status.Mode.prevMode
import club.xiaojiawei.status.PauseStatus
import club.xiaojiawei.strategy.AbstractModeStrategy
import club.xiaojiawei.utils.SystemUtil

/**
 * 主界面
 * @author 肖嘉威
 * @date 2022/11/25 12:36
 */
object HubModeStrategy : AbstractModeStrategy<Any?>() {

    val TOURNAMENT_MODE_RECT: GameRect = GameRect(-0.0790, 0.0811, -0.2090, -0.1737)

    //    TODO ADD
    val CLOSE_AD1_RECT: GameRect = GameRect(-0.0790, 0.0811, -0.2090, -0.1737)

    val CLOSE_AD2_RECT: GameRect = GameRect(-0.0296, 0.0431, 0.2502, 0.2552)

    override fun wantEnter() {
    }

    override fun afterEnter(t: Any?) {
        if (prevMode != ModeEnum.COLLECTIONMANAGER) {
            log.info { "点击弹窗（去除任务，活动等）" }
            for (i in 0..3) {
                if (PauseStatus.isPause) {
                    return
                }
                CLOSE_AD1_RECT.lClick()
                SystemUtil.delay(500)
            }
            CLOSE_AD2_RECT.lClick()
            SystemUtil.delay(200)
        }

        if (DeckStrategyManager.CURRENT_DECK_STRATEGY.get() != null) {
            log.info { "准备进入指定模式" }
            DeckStrategyManager.CURRENT_DECK_STRATEGY.get().runModes[0].modeEnum.modeStrategy!!.wantEnter()
        }
    }

}
