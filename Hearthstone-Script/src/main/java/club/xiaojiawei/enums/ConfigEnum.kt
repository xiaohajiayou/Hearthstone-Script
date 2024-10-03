package club.xiaojiawei.enums

import com.alibaba.fastjson.JSON

/**
 * 脚本配置信息，在[ConfigurationConfig]里写入
 * @author 肖嘉威
 * @date 2023/7/5 11:26
 */
enum class ConfigEnum(
    val group: String = "",
    val defaultValue: String = "",
) {

    /**
     * 游戏路径
     */
    GAME_PATH(group = "path", defaultValue = ""),

    /**
     * 战网路径
     */
    PLATFORM_PATH(group = "path", defaultValue = ""),

    /**
     * 工作日标记
     */
    WORK_DAY_FLAG(group = "time", defaultValue = JSON.toJSONString(listOf(true,false,false,false,false,false,false,false))),

    /**
     * 工作时间标记
     */
    WORK_TIME_FLAG(group = "time", defaultValue = JSON.toJSONString(listOf(true,false,false))),

    /**
     * 工作时间，具体时间段
     */
    WORK_TIME(group = "time", defaultValue = "[00:00-00:00,null,null]"),

    /**
     * 更新开发版
     */
    UPDATE_DEV(group = "version", defaultValue = "false"),

    AUTO_UPDATE(group = "version", defaultValue = "false"),

    /**
     * 是否执行策略
     */
    STRATEGY(group = "action", defaultValue = "true"),

    /**
     * 动作间隔/ms
     */
    MOUSE_ACTION_INTERVAL(group = "action", defaultValue = "3500"),

    /**
     * 鼠标移动间隔/ms
     */
    MOUSE_MOVE_INTERVAL(group = "action", defaultValue = "4"),

    /**
     * 适配畸变模式
     */
    DISTORTION(group = "action", defaultValue = "false"),

    /**
     * 套牌插件禁用列表
     */
    DECK_PLUGIN_DISABLED(group = "plugin"),

    /**
     * 卡牌插件禁用列表
     */
    CARD_PLUGIN_DISABLED(group = "plugin"),

    /**
     * 默认套牌(deck id)
     */
    DEFAULT_DECK_STRATEGY(group = "other", defaultValue = ""),

    /**
     * 战网密码
     */
    PLATFORM_PASSWORD(group = "other"),

    /**
     * 游戏最大日志/KB
     */
    GAME_MAX_LOG_SIZE(group = "other", defaultValue = "30720"),

    /**
     * 允许发送windows通知
     */
    SEND_NOTICE(group = "system", defaultValue = "true"),
    ;
}
