package jp.co.mihajipo.utility

import android.location.Location
import android.util.Log

/**
 * 距離共通処理を提供するクラス
 */
object DistanceUtility {

    /**
     * 2地点間の距離を算出する
     * @args tmpFastLocation 始点location
     * @args tmpNextLocation 終点location
     * @return String 2地点間の距離（m）
     */
    fun calcBetweenTwoPoints(tmpFastLocation: Location, tmpNextLocation: Location): String {
        val results = FloatArray(1)
        Location.distanceBetween(
            tmpFastLocation.latitude,
            tmpFastLocation.longitude,
            tmpNextLocation.latitude,
            tmpNextLocation.longitude,
            results
        );
        Log.d("debug", "距離確認：" + (results[0]/100).toString())
        return (results[0]/100).toString()
    }
}