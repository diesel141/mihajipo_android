package jp.co.mihajipo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.mihajipo.databinding.ActivityMihajipoBinding

/**
 * mihajipoのメインクラス
 */
class MihajipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMihajipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMihajipoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
