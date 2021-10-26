package top.sunhy.talking.routers

object LoginRouter {
    private const val LOGIN_BASE = "/login"
    private const val ACTIVITY = "$LOGIN_BASE/activity"
    private const val FRAGMENT = "$LOGIN_BASE/fragment"

    const val ACTIVITY_MAIN = "$ACTIVITY/main"
    const val FRAGMENT_MAIN = "$FRAGMENT/main"

    const val ACTIVITY_REGIST = "$ACTIVITY/regist"
    const val ACTIVITY_RESET = "$ACTIVITY/reset"
}