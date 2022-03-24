package jp.co.mihajipo.utility

import jp.co.mihajipo.presenter.MihajipoPresenter
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * タイマー共通処理を提供するクラス
 */
object TimerUtility {

    private const val REPEAT = 1000L
    private const val THIRTY = 30
    private val coroutineScope: CoroutineScope = GlobalScope
    private val keepRunning = AtomicBoolean(true)
    private var job: Job? = null
    private var seconds = 0

    /**
     * 経過時間を(x:xx:xx)形式で返却します
     */
    fun getSeconds(): String {
        // 時分秒計算
        val hour = (seconds / 3600).toString()
        var min = ((seconds % 3600) / 60).toString()
        var sec = (seconds % 60).toString()
        // 0埋め
        min = min.padStart(2, '0')
        sec = sec.padStart(2, '0')
        return "$hour:$min:$sec"
    }

    /**
     * タイマーを開始する
     * @param isReset リセットフラグ
     * @param presenter Mihajipoのpresenter
     */
    fun startTimer(
        isReset: Boolean = true,
        presenter: MihajipoPresenter?
    ) {
        if (isReset) seconds = 0
        keepRunning.set(true)
        job = coroutineScope.launch {
            delay(REPEAT)
            while (keepRunning.get()) {
                ++seconds
                if (seconds % THIRTY == 0) {
                    presenter?.updatePerSecond()
                } else {
                    presenter?.updatePerThirtySecond()
                }
                delay(REPEAT)
            }
        }
    }

    /**
     * タイマーを再始動する
     * @param presenter Mihajipoのpresenter
     */
    fun reStartTimer(
        presenter: MihajipoPresenter
    ) {
        startTimer(false, presenter)
    }

    /**
     * タイマーを停止・一時停止する
     */
    fun stopTimer() {
        keepRunning.set(false)
    }

    /**
     * タイマーをキャンセル(破棄)する
     */
    fun cancelTimer() {
        stopTimer()
        seconds = 0
        job?.cancel()
    }
}
