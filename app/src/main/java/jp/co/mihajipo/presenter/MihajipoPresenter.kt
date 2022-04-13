package jp.co.mihajipo.presenter

import android.hardware.SensorManager
import jp.co.mihajipo.MihajipoApplication
import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.model.MihajipoModel
import jp.co.mihajipo.model.MihajipoModel.StatusType
import jp.co.mihajipo.utility.LocationUtility
import jp.co.mihajipo.utility.PedometerDynamicUtility
import jp.co.mihajipo.utility.TimerUtility

/**
 * MIhajipoのPresenter
 * @param _view View
 */
open class MihajipoPresenter(_view: MIhajipoContract.View) :
    MIhajipoContract.Presenter {

    lateinit var sensorManager: SensorManager
    private var view: MIhajipoContract.View = _view
    private var model: MihajipoModel = MihajipoModel()

    init {
        view.initView()
    }

    override fun start() {
        model.updateStatus(StatusType.DOING)
        TimerUtility.startTimer(presenter = this)
        PedometerDynamicUtility.start(presenter = this)
        LocationUtility.getsInstance(MihajipoApplication.applicationContext())?.savePlace(
            LocationUtility.LocationType.START, view
        )
    }

    override fun pause() {
        model.updateStatus(StatusType.PAUSED)
        TimerUtility.stopTimer()
        PedometerDynamicUtility.stop()
    }

    override fun restart() {
        model.updateStatus(StatusType.DOING)
        TimerUtility.reStartTimer(this)
        PedometerDynamicUtility.reStart(presenter = this)
    }

    override fun stop() {
        model.updateStatus(StatusType.STOPPED)
        TimerUtility.cancelTimer()
        PedometerDynamicUtility.cancel()

        // リセット後の表示更新
        updatePerSecond()
        updatePerThirtySecond()
        updateForNeed()
    }

    override fun updatePerSecond() {
        model.updateTimer()
    }

    override fun updatePerThirtySecond() {
        model.updateTimer()
        model.updateSpeed()
        model.updateDistance()
    }

    override fun updateForNeed() {
        model.updateSteps()
    }

    /**
     * ステータスを返却
     * @return ステータス
     */
    fun getJobStatus() = model.getJobStatus()

    /**
     * 経過時間を返却
     * @return 経過時間
     */
    fun getTimes() = model.getTimes()

    /**
     * 歩数を返却
     * @return 歩数
     */
    fun getSteps() = model.getSteps()

    /**
     * 時速を返却
     * @return 時速
     */
    fun getSpeed() = model.getSpeed()

    /**
     * 距離を返却
     * @return 距離
     */
    fun getDistance() = model.getDistance()
}
