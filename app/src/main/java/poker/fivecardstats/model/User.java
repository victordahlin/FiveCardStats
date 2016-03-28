package poker.fivecardstats.model;

/**
 * Created by Victor on 2016-03-28.
 */
public class User {
    private String name;
    private int points;

    public User(String name) {
        this.name = name;
        this.points = 0;
    }

    /**
     * Adds point to current points
     * @param point to be added
     */
    public void addPoints(int point) {
        this.points += point;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    /**
     * @return name + " " + points
     */
    public String toString() {
        return name + " " + points;
    }
}
