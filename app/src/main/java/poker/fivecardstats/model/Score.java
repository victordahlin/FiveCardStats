package poker.fivecardstats.model;

public class Score {
    private Long id;
    private Long userId;
    private String type;
    private int score;

    public Score(Long id, Long userId, String type, int score) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() { return userId; }

    public String getType() { return type; }

    public int getScore() {
        return score;
    }

    /**
     * @return id + " " + userId + " " + type + " " + score
     */
    public String toString() {
        return id + " " + score;
    }




}
