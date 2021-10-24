package jp.co.mihajipo.model

interface MIhajipoContract {

    interface View {

        /**
         * viewを初期化
         */
        fun initView()
    }

    interface Presenter {

        /** 開始 */
        fun start()

        /** 一時停止 */
        fun pause()

        /** 再開 */
        fun restart()

        /** 停止 */
        fun stop()

        /** 1秒更新処理 */
        fun updatePerSecond()

        /** 30秒更新処理 */
        fun updatePerThirtySecond()

        /** 要求ごと更新処理 */
        fun updateForNeed()
    }
}
