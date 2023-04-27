package jp.co.mihajipo.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.util.Log
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
        Log.d("★１", "★１")
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
        presenter?.stop()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
        /*
         TODO 2回「許可しない」を選択した場合、以降のダイアログは表示されない
         設定を開くか、設定最速するダイアログ表示を追加したい
        */
    }
}
