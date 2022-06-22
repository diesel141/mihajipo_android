package jp.co.mihajipo.utility

import jp.co.mihajipo.MihajipoApplication

/**
 * 速度共通処理を提供するクラス
 */
object SpeedUtility {

    /**
     * 現在速度（時速）を返却
     *
     * @param previousLocation 前回位置情報
     */
    fun getSpeed(previousLocation: List<String>): String =
        SharedPreferencesUtility.instance(MihajipoApplication.applicationContext())
            .loadCurrentLocation()
            .let { currentLocation ->
                DistanceUtility.calcBetweenTwoPoints(
                    previousLocation[0],
                    previousLocation[1],
                    currentLocation[0],
                    currentLocation[1]
                ).let { meters ->
                    (meters.toFloat() * 2 * 60).toString()
                }
            }
}
