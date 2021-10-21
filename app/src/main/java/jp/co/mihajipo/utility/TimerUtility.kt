package jp.co.mihajipo.utility

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

object Timer {
    private val coroutineScope: CoroutineScope = GlobalScope
    private var countDownJob: Job? = null
    private var time = 0;
    private val isActive = AtomicBoolean(true)

    fun countDownStart() {
        /*if (countDownJob != null) { return }*/
        countDownJob = coroutineScope.launch {
            while(isActive) {
                delay(1000)
                ++time
            }
        }
    }

    fun countDownStop() {
        //countDownJob?.cancel()
    }
}