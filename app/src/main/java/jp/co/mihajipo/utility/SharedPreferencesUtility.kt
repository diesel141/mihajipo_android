package jp.co.mihajipo.utility

import android.content.Context
import jp.co.mihajipo.extentions.string

class SharedPreferencesUtil(context: Context) {

    private val preferences = context.getSharedPreferences("mihajipo_data", Context.MODE_PRIVATE)

    // 開始位置情報
    private var startPlaces: String by preferences.string()

    // 現在位置情報
    private var currentPlaces: String by preferences.string()

    /**
     * 開始位置情報を保存します
     * @param latitude 緯度
     * @param longitude 経度
     */
    fun saveStartPlace(latitude: Double, longitude: Double) {
        startPlaces = "$latitude$SEPARATOR$longitude"
    }

    /**
     * 開始位置情報を読み込みます
     */
    fun loadStartPlaces() = startPlaces.split(SEPARATOR)

    /**
     * 現在位置情報を保存します
     * @param latitude 緯度
     * @param longitude 経度
     */
    fun saveCurrentPlace(latitude: Double, longitude: Double) {
        currentPlaces = "$latitude$SEPARATOR$longitude"
    }

    /**
     * 前回位置情報を読み込みます
     */
    fun loadCurrentPlaces() = currentPlaces.split(SEPARATOR)

    companion object {
        const val SEPARATOR = "／"
    }
}
