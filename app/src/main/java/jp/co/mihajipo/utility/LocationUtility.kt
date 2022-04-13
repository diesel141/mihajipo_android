package jp.co.mihajipo.utility

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import jp.co.mihajipo.model.MIhajipoContract
import java.io.IOException
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * 位置情報に関する機能を提供するクラス
 */
class LocationUtility private constructor(private val context: Context) {
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private var locationTimer: Timer? = null
    private var provider: String? = null
    private var time: Long = 0
    private var onProcessCallbackListener: OnProcessCallbackListener? = null
    private val handler = Handler(Looper.getMainLooper())

    /**
     * 位置情報取得後のプロセスコールバックリスナー
     */
    interface OnProcessCallbackListener {
        fun onSuccessLocation(latitude: Double, longitude: Double)
        fun onFailedLocation()
        fun onUnauthorized()
    }

    /**
     * 位置情報マネージャを初期化します
     *
     * @return
     */
    fun initCurrentLocation(): Boolean {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        provider = if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            "gps"
        } else if (locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
            "network"
        } else {
            null
        }
        return true
    }

    /**
     * 位置情報を保存します
     *
     * @param locationType 位置情報タイプ
     * @param view 参照View
     */
    fun saveLocation(
        locationType: LocationType = LocationType.CURRENT,
        view: MIhajipoContract.View
    ) {
        onProcessCallbackListener = object : OnProcessCallbackListener {
            override fun onSuccessLocation(latitude: Double, longitude: Double) {
                when (locationType) {
                    LocationType.START ->
                        SharedPreferencesUtility(context).saveStartLocation(latitude, longitude)
                    else ->
                        SharedPreferencesUtility(context).saveCurrentLocation(latitude, longitude)
                }
            }

            override fun onFailedLocation() {
                TODO("Not yet implemented")
            }

            override fun onUnauthorized() {
                view.showLocationPermissionDialog()
            }
        }
        startLocationService()
    }

    /**
     * 位置情報を取得します
     */
    private fun startLocationService() {
        stopLocationService()
        initCurrentLocation()
        val criteria = Criteria()
        criteria.isBearingRequired = false // 方位不要
        criteria.isSpeedRequired = false // 速度不要
        criteria.isAltitudeRequired = false // 高度不要
        if (provider == null) {
            AlertDialog.Builder(context)
                .setTitle("現在地機能を改善")
                .setMessage("現在、位置情報は一部有効ではないものがあります。次のように設定すると、もっともすばやく正確に現在地を検出できるようになります:\n\n● 位置情報の設定でGPSとワイヤレスネットワークをオンにする\n\n● Wi-Fiをオンにする")
                .setPositiveButton("設定", DialogInterface.OnClickListener { dialog, which ->
                    // 端末の位置情報設定画面へ遷移
                    try {
                        context.startActivity(Intent("android.settings.LOCATION_SOURCE_SETTINGS"))
                    } catch (e: ActivityNotFoundException) {
                        // 位置情報設定画面がない糞端末の場合は、仕方ないので何もしない
                    }
                })
                .setNegativeButton("スキップ", // 何も行わない
                    DialogInterface.OnClickListener { dialog, which -> })
                .create()
                .show()
            stopLocationService()
            return
        }

        // 最後に取得できた位置情報が5分以内のものであれば有効とします。
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            onProcessCallbackListener?.onUnauthorized()
            return
        }
        val lastKnownLocation = locationManager?.getLastKnownLocation(provider)
        if (lastKnownLocation != null && Date().time - lastKnownLocation.time <= 5 * 60 * 1000L) {
            setLocation(lastKnownLocation)
            return
        }
        locationTimer = Timer(true)
        time = 0L

        locationTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (time == 1000L) {
                        Toast.makeText(context, "現在地を特定しています。", Toast.LENGTH_LONG).show()
                    } else if (time >= 30 * 1000L) {
                        Toast.makeText(context, "現在地を特定できませんでした。", Toast.LENGTH_LONG).show()
                        stopLocationService()
                    }
                    time += 1000L
                }
            }
        }, 0L, 1000L)

        // 位置情報の取得を開始します。
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                setLocation(location)
            }

            override fun onProviderDisabled(provider: String) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        }
        Handler(Looper.getMainLooper()).post {
            locationManager?.requestLocationUpdates(provider, 60000, 1f, locationListener)
        }
    }

    fun stopLocationService() {
        if (locationTimer != null) {
            locationTimer?.cancel()
            locationTimer?.purge()
            locationTimer = null
        }
        if (locationManager != null) {
            if (locationListener != null) {
                locationManager?.removeUpdates(locationListener)
                locationListener = null
            }
            locationManager = null
        }
    }

    fun setLocation(location: Location) {
        stopLocationService()
        if (onProcessCallbackListener != null) {
            onProcessCallbackListener?.onSuccessLocation(location.latitude, location.longitude)
        }
    }

    /**
     * 緯度経度から住所文字列を取得し、返却します
     *
     * @param latitude
     * @param longitude
     * @return
     */
    fun getAddress(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val result = StringBuilder()
        val addresses: List<Address> = try {
            geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: IOException) {
            return ""
        }
        for (address in addresses) {
            val idx = address.maxAddressLineIndex
            // 1番目のレコードは国名のため省略
            for (i in 1..idx) {
                result.append(address.getAddressLine(i))
            }
        }
        result.append("\n\nhttps://www.google.co.jp/maps/@")
        result.append(latitude.toString())
        result.append(",")
        result.append(longitude.toString())
        return result.toString()
    }

    companion object {
        private var sInstance: LocationUtility? = null

        /**
         * 唯一のインスタンを返却します
         *
         * @param context Context
         * @return 位置情報ユーティリティインスタンス
         */
        fun getsInstance(context: Context): LocationUtility? {
            if (sInstance == null) {
                sInstance = LocationUtility(context)
            }
            return sInstance
        }


        fun distFrom(
            lat1: Float,
            lng1: Float,
            lat2: Float,
            lng2: Float
        ): Float {
            val earthRadius = 6371000.0 //meters
            val dLat = Math.toRadians((lat2 - lat1).toDouble())
            val dLng = Math.toRadians((lng2 - lng1).toDouble())
            val a = sin(dLat / 2) * sin(dLat / 2) +
                    cos(Math.toRadians(lat1.toDouble())) * cos(
                Math.toRadians(lat2.toDouble())
            ) *
                    sin(dLng / 2) * sin(dLng / 2)
            val c =
                2 * atan2(sqrt(a), sqrt(1 - a))
            return (earthRadius * c).toFloat()
        }
    }

    /**
     * 位置情報タイプの列挙型
     */
    enum class LocationType {

        /** 開始位置 */
        START,

        /** 現在位置 */
        CURRENT
    }
}
