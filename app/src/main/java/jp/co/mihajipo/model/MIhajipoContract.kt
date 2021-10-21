package jp.co.mihajipo.model

import org.jetbrains.annotations.NotNull

interface MIhajipoContract {

    interface View {
        fun initView()

        /**
         * 時間更新
         * @param time 時間
         */
        @NotNull
        fun updateTimer(time: String)

        /**
         * 歩数更新
         * @param steps 歩数
         */
        @NotNull
        fun updateSteps(steps: String)

        /**
         * 時速更新
         * @param speed 時速
         */
        @NotNull
        fun updateSpeed(speed: String)

        /**
         * 距離更新
         * @param distance 距離
         */
        @NotNull
        fun updateDistance(distance: String)
    }

    interface Presenter {
        /** 1秒更新処理 */
        fun updatePerSecond()

        /** 30秒更新処理 */
        fun updatePerThirtySecond()

        /** 要求ごと更新処理 */
        fun updateForNeed()
    }

    interface Model {
        /**
         * 時間を返却
         * @return 時間
         */
        fun getTimes(): String

        /**
         * 歩数を返却
         * @return 歩数
         */
        fun getSteps(): String

        /**
         * 時速を返却
         * @return 時速
         */
        fun getSpeed(): String

        /**
         * 距離を返却
         * @return 距離
         */
        fun getDistance(): String
    }
}
