package jp.co.mihajipo.presenter

import android.hardware.SensorManager
import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.model.MihajipoModel
import jp.co.mihajipo.model.MihajipoModel.StatusType
import jp.co.mihajipo.utility.PedometerDynamicUtility

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
        PedometerDynamicUtility.start(presenter = this)
    }

    override fun pause() {
        model.updateStatus(StatusType.PAUSED)
        PedometerDynamicUtility.stop()
    }

    override fun restart() {
        model.updateStatus(StatusType.DOING)
        PedometerDynamicUtility.reStart(presenter = this)
    }

    override fun stop() {
        model.updateStatus(StatusType.STOPPED)
        PedometerDynamicUtility.cancel()
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
