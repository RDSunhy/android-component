package top.sunhy.network

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

/**
 * 网路请求线程池
 */
object NetworkExecutors {

    //按任务类型分配的池
    private val diskIO: Executor by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val networkIO: Executor by lazy {
        Executors.newFixedThreadPool(3)
    }

    private val mainThread: Executor by lazy {
        MainThreadExecutor()
    }


    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}