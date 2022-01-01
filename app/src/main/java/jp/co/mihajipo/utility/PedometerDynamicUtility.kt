package jp.co.mihajipo.utility

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import jp.co.mihajipo.presenter.MihajipoPresenter
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 歩数計共通処理を提供するクラス
 */
object PedometerDynamicUtility {

    private var _presenter: MihajipoPresenter? = null
    private val keepRunning = AtomicBoolean(true)
    private var sensorListener: SensorEventListener? = null
    var steps = 0

    fun start(isReset: Boolean = true, presenter: MihajipoPresenter?) {
        if (isReset) steps = 0
        keepRunning.set(true)
        _presenter ?: run {
            _presenter = presenter
            initSensorListener()
        }
    }

    fun reStart(presenter: MihajipoPresenter) {
        start(false, presenter)
    }

    /**
     * 停止・一時停止
     */
    fun stop() {
        keepRunning.set(false)
    }

    fun cancel() {
        _presenter?.sensorManager?.unregisterListener(sensorListener)
        _presenter = null
    }

    /**
     * 歩数イベントリスナ
     * 歩数計センサー非搭載端末でも加速度センサーで検知できる仕様
     */
    private fun initSensorListener() {
        _presenter?.sensorManager?.let {
            sensorListener = object : SensorEventListener {

                /** 前回計測値 */
                private var prevScalar = 0.0

                /** 待機平均 */
                private var weightedAverage = 0.0

                private val ratio = 0.8
                private var isReadyCount = true
                private val gateUp = 130.0
                private val gateDown = 80.0

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                    // ignore
                }

                override fun onSensorChanged(event: SensorEvent) {
                    val acceleratedEvents = event.values
                    val accelScalar =
                        acceleratedEvents[0] * acceleratedEvents[0] + acceleratedEvents[1] * acceleratedEvents[1] + acceleratedEvents[2] * acceleratedEvents[2]

                    weightedAverage = ratio * accelScalar + (1 - ratio) * prevScalar
                    if (isReadyCount && weightedAverage > gateUp) {
                        steps++
                        _presenter?.updateForNeed()
                        isReadyCount = false
                    } else if (!isReadyCount && weightedAverage < gateDown) {
                        isReadyCount = true
                    }
                    prevScalar = accelScalar.toDouble()
                }
            }

            it.registerListener(
                sensorListener,
                it.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    /**
     * 歩数を返却します
     */
    fun getSteps(): String {
        return steps.toString()
    }
}
