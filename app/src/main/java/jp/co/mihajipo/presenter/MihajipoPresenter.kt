package jp.co.mihajipo.presenter

import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.model.MihajipoModel
import jp.co.mihajipo.model.MihajipoModel.StatusType

/**
 * MIhajipoのPresenter
 * @param _view View
 */
open class MihajipoPresenter(_view: MIhajipoContract.View) : MIhajipoContract.Presenter {

    private var view: MIhajipoContract.View = _view
    private var model: MIhajipoContract.Model = MihajipoModel()

    init {
        view.initView()
    }

    override fun start() {
        model.updateStatus(StatusType.DOING)
    }

    override fun pause() {
        model.updateStatus(StatusType.PAUSED)
    }

    override fun restart() {
        model.updateStatus(StatusType.DOING)
    }

    override fun stop() {
        model.updateStatus(StatusType.STOPPED)
    }

    override fun updatePerSecond() {
        view.updateTimer(model.getTimes())
    }

    override fun updatePerThirtySecond() {
        view.updateTimer(model.getTimes())
        view.updateSpeed(model.getSpeed())
        view.updateDistance(model.getDistance())
    }

    override fun updateForNeed() {
        view.updateSteps(model.getSteps())
    }

    /**
     * ステータスを返却
     * @return ステータス
     */
    fun getJobStatus() = model.getJobStatus()
}
