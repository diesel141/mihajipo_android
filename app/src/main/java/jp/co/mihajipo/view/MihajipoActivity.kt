package jp.co.mihajipo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.mihajipo.databinding.ActivityMihajipoBinding
import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.presenter.MihajipoPresenter

/**
 * mihajipoのメインクラス
 */
class MihajipoActivity : AppCompatActivity(), MIhajipoContract.View {

    private lateinit var binding: ActivityMihajipoBinding
    private var presenter: MihajipoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMihajipoBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        presenter = MihajipoPresenter(this).also {
            it.getJobStatus().observe(this@MihajipoActivity, { status ->
                binding.status = status
            })
        }

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

    override fun updateTimer(time: String) {
        runOnUiThread {
            binding.times.text = time
        }
    }

    override fun updateSteps(steps: String) {
        runOnUiThread {
            binding.steps.text = steps
        }
    }

    override fun updateSpeed(speed: String) {
        runOnUiThread {
            binding.speed.text = speed
        }
    }

    override fun updateDistance(distance: String) {
        runOnUiThread {
            binding.distance.text = distance
        }
    }
}
