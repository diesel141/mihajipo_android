package jp.co.mihajipo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Mihajipoの基底Activity
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initPresenter()
        initObserver()
        showLocationPermissionDialog()
    }

    /**
     * bindingを初期化するIF
     */
    abstract fun initBinding()

    /**
     * presenterを初期化するIF
     */
    abstract fun initPresenter()

    /**
     * observerを初期化するIF
     */
    abstract fun initObserver()

    /**
     * showLocationPermissionDialogを初期化するIF
     */
    abstract fun showLocationPermissionDialog()
}
