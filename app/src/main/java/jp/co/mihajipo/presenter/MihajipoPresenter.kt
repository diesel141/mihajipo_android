package jp.co.mihajipo.presenter

import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.model.MihajipoModel

/**
 * MIhajipoのPresenter
 */
open class MihajipoPresenter(_view: MIhajipoContract.View) : MIhajipoContract.Presenter {

    private var view: MIhajipoContract.View = _view
    private var model: MIhajipoContract.Model = MihajipoModel()

    init {
        view.initView()
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
}
