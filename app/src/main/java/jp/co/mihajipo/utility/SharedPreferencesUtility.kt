package jp.co.mihajipo.utility

import android.content.Context
import jp.co.mihajipo.extentions.string

class SharedPreferencesUtility private constructor(context: Context) {

    private val preferences = context.getSharedPreferences("mihajipo_data", Context.MODE_PRIVATE)

    // 開始位置情報
    private var startLocation: String by preferences.string()

    // 現在位置情報
    private var currentLocation: String by preferences.string()

    /**
     * 開始位置情報を保存します
     * @param latitude 緯度
     * @param longitude 経度
     */
    fun saveStartLocation(latitude: Double, longitude: Double) {
        startLocation = "$latitude$SEPARATOR$longitude"
    }

    /**
     * 開始位置情報を読み込みます
     */
    fun loadStartLocation() = startLocation.split(SEPARATOR)

    /**
     * 現在位置情報を保存します
     * @param latitude 緯度
     * @param longitude 経度
     */
    fun saveCurrentLocation(latitude: Double, longitude: Double) {
        currentLocation = "$latitude$SEPARATOR$longitude"
    }

    /**
     * 前回位置情報を読み込みます
     */
    fun loadCurrentLocation() = currentLocation.split(SEPARATOR)

    /**
     * 現在位置情報が設定されているかを返す
     */
    fun isSetCurrentLocation() = currentLocation.isNullOrEmpty()

    companion object {
        const val SEPARATOR = "／"
        private var instance: SharedPreferencesUtility? = null

        fun instance(context: Context): SharedPreferencesUtility {
            return instance ?: SharedPreferencesUtility(context).also { instance = it }
        }
    }
}
