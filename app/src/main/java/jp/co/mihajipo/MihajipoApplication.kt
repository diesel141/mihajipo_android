package jp.co.mihajipo

import android.app.Application
import android.content.Context
import jp.co.mihajipo.utility.LocationUtility

class MihajipoApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        LocationUtility.getsInstance(this.applicationContext)?.initCurrentLocation()
    }

    companion object {
        private var instance: MihajipoApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}