package jp.co.mihajipo.utility

import android.location.Location
import android.util.Log

/**
 * 距離計測共通処理を提供するクラス
 */
object DistanceUtility {

    /**
     * 2地点間の距離を算出する
     * @param tmpFastLocation 始点location
     * @param tmpNextLocation 終点location
     * @return 2地点間の距離（m）
     */
    fun calcBetweenTwoPoints(tmpFastLocation: Location, tmpNextLocation: Location): String {
        val results = FloatArray(1)
        Location.distanceBetween(
            tmpFastLocation.latitude,
            tmpFastLocation.longitude,
            tmpNextLocation.latitude,
            tmpNextLocation.longitude,
            results
        )
        Log.d("debug", "距離確認：" + results[0].toString())
        return results[0].toString()
    }

    /**
     * 2地点間の距離を算出
     *
     * @param latitude1 始点緯度
     * @param longitude1 始点経度
     * @param latitude2 終点緯度
     * @param longitude2 終点経度
     * @return 2地点間の距離（m）
     */
    fun calcBetweenTwoPoints(
        latitude1: String,
        longitude1: String,
        latitude2: String,
        longitude2: String
    ): String {
        val results = FloatArray(1)
        Location.distanceBetween(
            latitude1.toDouble(),
            longitude1.toDouble(),
            latitude2.toDouble(),
            longitude2.toDouble(),
            results
        )
        Log.d("debug", "2地点間の距離：" + results[0].toString())
        return results[0].toString()
    }
}
