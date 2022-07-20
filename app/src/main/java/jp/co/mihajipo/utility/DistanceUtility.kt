package jp.co.mihajipo.utility

import android.location.Location
import android.util.Log
import jp.co.mihajipo.MihajipoApplication
import kotlin.math.floor

/**
 * 距離計測共通処理を提供するクラス
 */
object DistanceUtility {

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

        val res = floor(results[0].toDouble()).toInt().toString()
        Log.d("debug", "2地点間の距離：{$res}")
        return res
    }

    /**
     * 移動距離を返却
     *
     * @param previousLocation 前回位置情報
     * @return 2地点間の距離（m）
     */
    fun getDistance(previousLocation: List<String>): String =
        SharedPreferencesUtility.instance(MihajipoApplication.applicationContext())
            .loadCurrentLocation()
            .let { currentLocation ->
                calcBetweenTwoPoints(
                    previousLocation[0],
                    previousLocation[1],
                    currentLocation[0],
                    currentLocation[1]
                )
            }
}

