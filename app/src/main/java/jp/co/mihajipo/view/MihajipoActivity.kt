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
        setContentView(binding.root)

        presenter = MihajipoPresenter(this)
    }

    override fun initView() {
        // TODO 開始・一時停止・停止ボタンの切り替えとか制御とか
    }

    override fun updateTimer(time: String) {
        binding.times.text = time
    }

    override fun updateSteps(steps: String) {
        binding.steps.text = steps
    }

    override fun updateSpeed(speed: String) {
        binding.speed.text = speed
    }

    override fun updateDistance(distance: String) {
        binding.distance.text = distance
    }
}
