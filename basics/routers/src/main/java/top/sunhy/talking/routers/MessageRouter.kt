package top.sunhy.talking.routers

object MessageRouter {
    private const val HOME_BASE = "/message"
    private const val ACTIVITY = "$HOME_BASE/activity"
    private const val FRAGMENT = "$HOME_BASE/fragment"

    const val ACTIVITY_MAIN = "$ACTIVITY/main"
    const val FRAGMENT_MAIN = "$FRAGMENT/main"
}