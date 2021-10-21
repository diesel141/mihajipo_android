package jp.co.mihajipo.model

/**
 * MihajipoのModel
 */
class MihajipoModel : MIhajipoContract.Model {

    override fun getTimes(): String {
        // TODO Utilityから経過時間を取得
        return ""
    }

    override fun getSteps(): String {
        // TODO Utilityから歩数を取得
        return ""
    }

    override fun getSpeed(): String {
        // TODO Utilityから時速を取得
        return ""
    }

    override fun getDistance(): String {
        // TODO Utilityから距離を取得
        return ""
    }
}
