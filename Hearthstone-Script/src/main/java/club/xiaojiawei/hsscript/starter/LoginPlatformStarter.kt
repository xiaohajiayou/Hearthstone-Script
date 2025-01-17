package club.xiaojiawei.hsscript.starter

import club.xiaojiawei.config.EXTRA_THREAD_POOL
import club.xiaojiawei.config.log
import club.xiaojiawei.hsscript.config.StarterConfig
import club.xiaojiawei.hsscript.consts.PLATFORM_CN_NAME
import club.xiaojiawei.hsscript.dll.SystemDll
import club.xiaojiawei.hsscript.enums.ConfigEnum
import club.xiaojiawei.hsscript.utils.ConfigUtil
import club.xiaojiawei.hsscript.utils.GameUtil
import club.xiaojiawei.hsscript.utils.SystemUtil
import club.xiaojiawei.util.isTrue
import com.sun.jna.platform.win32.WinDef.HWND
import java.awt.event.KeyEvent
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * 登录战网
 * @author 肖嘉威
 * @date 2023/10/13 22:24
 */
object LoginPlatformStarter : AbstractStarter() {

    override fun execStart() {
        if (GameUtil.isAliveOfGame()) {
            startNextStarter()
            return
        } else if (ConfigUtil.getString(ConfigEnum.PLATFORM_PASSWORD).isBlank()) {
            log.info { "未配置${PLATFORM_CN_NAME}账号密码，跳过此步骤" }
            startNextStarter()
            return
        }
        val loginCount = AtomicInteger()
        addTask(
            EXTRA_THREAD_POOL.scheduleAtFixedRate({
                var loginPlatformHWND: HWND?
                if ((GameUtil.findLoginPlatformHWND().also { loginPlatformHWND = it }) == null) {
                    startNextStarter()
                } else {
                    if (loginCount.incrementAndGet() > 400) {
                        log.warn { "登录战网失败次数过多，重新执行启动器链" }
                        stop()
                        EXTRA_THREAD_POOL.schedule({
                            GameUtil.killLoginPlatform()
                            GameUtil.killPlatform()
                            loginCount.set(0)
                            StarterConfig.starter.start()
                        }, 1, TimeUnit.SECONDS)
                        return@scheduleAtFixedRate
                    }
                    inputPassword(loginPlatformHWND!!)
                    SystemUtil.delayShort()
                    clickLoginButton(loginPlatformHWND)
                }
            }, 5, 100, TimeUnit.MILLISECONDS)
        )
    }

    private fun inputPassword(loginPlatformHWND: HWND) {
        log.info { "输入战网密码中..." }
        val password = ConfigUtil.getString(ConfigEnum.PLATFORM_PASSWORD)
        password.isNotBlank().isTrue {
            SystemDll.INSTANCE.sendText(loginPlatformHWND, password, false)
        }
    }

    private fun clickLoginButton(loginPlatformHWND: HWND) {
        log.info { "点击登入按钮" }
        SystemDll.INSTANCE.clickLoginPlatformLoginBtn(loginPlatformHWND)
    }

    @Deprecated("体验差")
    private fun inputPassword() {
        log.info { "输入战网密码中..." }
        val password = ConfigUtil.getString(ConfigEnum.PLATFORM_PASSWORD)
        password.isNotBlank().isTrue {
            SystemUtil.deleteAllContent()
            SystemUtil.copyToClipboard(password)
            SystemUtil.pasteFromClipboard()
        }
    }

    @Deprecated("体验差")
    private fun clickLoginButton() {
        log.info { "点击登入按钮" }
        SystemUtil.sendKey(KeyEvent.VK_TAB)
        SystemUtil.delay(500)
        SystemUtil.sendKey(KeyEvent.VK_ENTER)
    }

}
