package jp.co.mihajipo.model

import androidx.lifecycle.MutableLiveData
import jp.co.mihajipo.utility.PedometerDynamicUtility
import jp.co.mihajipo.utility.TimerUtility

/**
 * MihajipoのModel
 */
class MihajipoModel {

    /**
     * ステータスLiveData
     */
    fun getJobStatus(): MutableLiveData<StatusType> = jobStatus

    /**
     * ステータスを更新
     */
    fun updateStatus(_jobStatus: StatusType) {
        jobStatus.postValue(_jobStatus)
    }

    /**
     * 経過時間LiveData
     */
    fun getTimes(): MutableLiveData<String> = times

    /**
     * 経過時間を更新
     */
    fun updateTimer() {
        times.postValue(TimerUtility.getSeconds())
    }

    /**
     * 歩数LiveData
     */
    fun getSteps(): MutableLiveData<String> = steps

    /**
     * 歩数を更新
     */
    fun updateSteps() {
        steps.postValue(PedometerDynamicUtility.getSteps())
    }

    /**
     * 時速LiveData
     */
    fun getSpeed(): MutableLiveData<String> = speed

    /**
     * 時速を更新
     */
    fun updateSpeed() {
        // speed.postValue(SpeedUtility.getSpeed()) TODO
    }

    /**
     * 距離LiveData
     */
    fun getDistance(): MutableLiveData<String> = distance

    /**
     * 距離を更新
     */
    fun updateDistance() {
        // times.postValue(DistanceUtility.getDistance()) TODO
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

    /** ステータス管理 */
    private var jobStatus: MutableLiveData<StatusType> = MutableLiveData(StatusType.STOPPED)

    /** 経過時間 */
    private var times: MutableLiveData<String> = MutableLiveData()

    /** 経過時間 */
    private var steps: MutableLiveData<String> = MutableLiveData()

    /** 経過時間 */
    private var speed: MutableLiveData<String> = MutableLiveData()

    /** 経過時間 */
    private var distance: MutableLiveData<String> = MutableLiveData()
}
