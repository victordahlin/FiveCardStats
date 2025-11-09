package poker.fivecardstats.model

data class Score(
        val id: Long,
        val userId: Long,
        val type: String,
        val score: Int
) {
    /**
     * @return id + " " + userId + " " + type + " " + score
     */
    override fun toString(): String = "$id $score"
}