package jp.co.mihajipo.model

import androidx.lifecycle.MutableLiveData

/**
 * MihajipoのModel
 */
class MihajipoModel : MIhajipoContract.Model {

    /** ステータス管理 */
    private var jobStatus: MutableLiveData<StatusType> = MutableLiveData(StatusType.STOPPED)

    override fun getJobStatus(): MutableLiveData<StatusType> {
        return jobStatus
    }

    override fun updateStatus(status: StatusType) {
        jobStatus.postValue(status)
    }

    override fun getTimes(): String {
        // TODO Utilityから経過時間を取得
        return ""
    }

    override fun getSteps(): String {
        // TODO Utilityから歩数を取得
        return ""
    }

    override fun getSpeed(): String {
        // TODO Utilityから時速を取得
        return ""
    }

    override fun getDistance(): String {
        // TODO Utilityから距離を取得
        return ""
    }

    /**
     * ステータス列挙型
     */
    enum class StatusType {

        /** 停止 */
        STOPPED,

        /** 計測中 */
        DOING,

        /** 一時停止 */
        PAUSED
    }
}
