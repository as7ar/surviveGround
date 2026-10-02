package kr.asxar.surviveground.queue

enum class GameMode {
    PLAYERS3vs3vs3,
    PLAYERS5vs5,
    PLAYERS3vs3,
    PLAYERS1vs1;

    override fun toString(): String {
        return when(this) {
            PLAYERS3vs3vs3 -> "3 vs 3 vs 3"
            PLAYERS5vs5 -> "5 vs 5"
            PLAYERS3vs3 -> "3 vs 3"
            PLAYERS1vs1 -> "1 vs 1"
        }
    }
}