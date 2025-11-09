package poker.fivecardstats.model

data class User(
        val id: Long,
        val name: String,
        val points: Int
) {
    /**
     * @return name + " " + points
     */
    override fun toString(): String = "$name $points"
}