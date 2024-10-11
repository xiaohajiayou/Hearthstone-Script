package club.xiaojiawei.hsscript.strategy.phase

import club.xiaojiawei.bean.Entity
import club.xiaojiawei.bean.isValid
import club.xiaojiawei.hsscript.bean.log.ExtraEntity
import club.xiaojiawei.hsscript.bean.log.TagChangeEntity
import club.xiaojiawei.config.log
import club.xiaojiawei.hsscript.consts.ScriptStaticData
import club.xiaojiawei.enums.StepEnum
import club.xiaojiawei.hsscript.enums.TagEnum
import club.xiaojiawei.enums.WarPhaseEnum
import club.xiaojiawei.status.War.currentPhase
import club.xiaojiawei.status.War.currentPlayer
import club.xiaojiawei.status.War.firstPlayerGameId
import club.xiaojiawei.status.War.me
import club.xiaojiawei.status.War.player1
import club.xiaojiawei.status.War.player2
import club.xiaojiawei.status.War.rival
import club.xiaojiawei.hsscript.strategy.AbstractPhaseStrategy
import org.apache.logging.log4j.util.Strings

/**
 * 抽起始牌阶段
 * @author 肖嘉威
 * @date 2022/11/27 13:35
 */
object DrawnInitCardPhaseStrategy : AbstractPhaseStrategy() {

    /**
     * SHOW_ENTITY解析来的reverse为false
     * TAG_CHANGE解析来的reverse为true
     * @param playerId
     * @param reverse
     */
    fun verifyPlayer(playerId: String, reverse: Boolean) {
        var newPlayerId = playerId
        if (reverse) {
            newPlayerId = if (newPlayerId == "1") "2" else "1"
        }
        if (!me.isValid() && Strings.isNotBlank(newPlayerId)) {
            when (newPlayerId) {
                "1" -> {
                    me = player1
                    rival = player2
                    log.info { "确定双方玩家号，我方1号，对方2号" }
                }

                "2" -> {
                    me = player2
                    rival = player1
                    log.info { "确定双方玩家号，我方2号，对方1号" }
                }

                else -> log.warn { "不支持的playId" }
            }
        }
    }

    override fun dealTagChangeThenIsOver(line: String, tagChangeEntity: TagChangeEntity): Boolean {
        if (tagChangeEntity.tag == TagEnum.ZONE) {
            verifyPlayer(tagChangeEntity.playerId, true)
        } else if (tagChangeEntity.tag == TagEnum.NEXT_STEP && tagChangeEntity.value == StepEnum.BEGIN_MULLIGAN.name) {
            currentPhase = WarPhaseEnum.REPLACE_CARD
            return true
        }
        return false
    }

    override fun dealShowEntityThenIsOver(line: String, extraEntity: ExtraEntity): Boolean {
        if (extraEntity.entityName == Entity.UNKNOWN_ENTITY_NAME) {
            verifyPlayer(extraEntity.playerId, false)
        }
        return false
    }

    /**
     * 确定一方玩家的游戏id，[.verifyPlayer]方法绝对会在此方法执行前执行
     * @param line
     * @param extraEntity
     * @return
     */
    override fun dealFullEntityThenIsOver(line: String, extraEntity: ExtraEntity): Boolean {
        val card = ScriptStaticData.CARD_AREA_MAP[extraEntity.entityId]?.findByEntityId(extraEntity.entityId)
        card?:let {
            log.warn { "card【entityId:${extraEntity.entityId}】不应为null" }
            return false
        }
        if (card.entityName == Entity.UNKNOWN_ENTITY_NAME || card.entityName == "幸运币") {
            card.entityName = "幸运币"
            if (Strings.isNotBlank(card.cardId)) {
                rival.gameId = firstPlayerGameId
                log.info { "对方游戏id：$firstPlayerGameId" }
            } else {
                me.gameId = firstPlayerGameId
                log.info { "我方游戏id：$firstPlayerGameId" }
            }
            if (me.gameId == firstPlayerGameId
                || (rival.gameId.isNotBlank() && rival.gameId != firstPlayerGameId)
            ) {
                currentPlayer = me
            } else {
                currentPlayer = rival
            }
        }
        return false
    }
}