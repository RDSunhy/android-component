package top.sunhy.talking.event

import com.jeremyliao.liveeventbus.core.LiveEvent

/**
 * App 前后台切换 event 消息
 */
class AppStateEvent constructor(state: String) : LiveEvent {
    companion object{
        const val FOREGROUND = "FOREGROUND" //App回到前台
        const val BACKGROUND = "BACKGROUND" //App切换到后台
    }
    var state: String = state
}