package jp.co.mihajipo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.mihajipo.databinding.ActivityMihajipoBinding
import jp.co.mihajipo.model.MIhajipoContract
import jp.co.mihajipo.presenter.MihajipoPresenter
import jp.co.mihajipo.utility.TimerUtility

/**
 * mihajipoのメインクラス
 */
class MihajipoActivity : AppCompatActivity(), MIhajipoContract.View {

    private lateinit var binding: ActivityMihajipoBinding
    private var presenter: MihajipoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMihajipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MihajipoPresenter(this)
    }

    override fun initView() {
        // TODO 開始・一時停止・停止ボタンの切り替えとか制御とか
        binding.start.setOnClickListener {
            TimerUtility.startTimer(presenter = presenter)
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
