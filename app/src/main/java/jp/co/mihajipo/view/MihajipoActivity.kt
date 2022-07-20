package jp.co.mihajipo.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import jp.co.mihajipo.databinding.ActivityMihajipoBinding
import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.presenter.MihajipoPresenter

/**
 * mihajipoのメインクラス
 */
class MihajipoActivity : BaseActivity(), MIhajipoContract.View {

    private lateinit var binding: ActivityMihajipoBinding
    private var presenter: MihajipoPresenter? = null

    override fun initBinding() {
        binding = ActivityMihajipoBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
            setContentView(it.root)
        }
    }

    override fun initPresenter() {
        presenter = MihajipoPresenter(this).apply {
            sensorManager =
                applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            initObserver()
        }
    }

    override fun initObserver() {
        presenter?.getJobStatus()?.observe(this@MihajipoActivity, { status ->
            binding.status = status
        })
        presenter?.getTimes()?.observe(this@MihajipoActivity, { times ->
            binding.times.text = times
        })
        presenter?.getSteps()?.observe(this@MihajipoActivity, { steps ->
            binding.steps.text = steps
        })
        presenter?.getSpeed()?.observe(this@MihajipoActivity, { speed ->
            binding.speed.text = speed
        })
        presenter?.getDistance()?.observe(this@MihajipoActivity, { distance ->
            binding.distance.text = distance
        })
    }

    override fun initView() {
        binding.start.setOnClickListener {
            presenter?.start()
        }
        binding.pause.setOnClickListener {
            presenter?.pause()
        }
        binding.restart.setOnClickListener {
            presenter?.restart()
        }
        binding.stop.setOnClickListener {
            presenter?.stop()
        }
    }

    override fun showLocationPermissionDialog() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // パーミッションを持っていない場合(許可が選択されなかった場合)
        if (grantResults.all { it != PackageManager.PERMISSION_GRANTED }) {
            AlertDialog.Builder(this)
                .setTitle("位置情報の許可がありません")
                .setMessage("アプリケーションの設定から位置情報を許可してください")
                .setPositiveButton("OK") { dialog, which ->
                    // アプリケーションを終了
                    finish()
                }
                .show()
        }
    }
}
