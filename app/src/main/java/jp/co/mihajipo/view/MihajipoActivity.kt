package jp.co.mihajipo.view

import android.content.Context
import android.hardware.SensorManager
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
}
