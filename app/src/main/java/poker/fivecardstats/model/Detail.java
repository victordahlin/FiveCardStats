package poker.fivecardstats.model;

/**
 * Created by Victor on 2016-03-28.
 */
public class Detail {
    private String type;
    private int point;

    public Detail(String type, int point) {
        this.type = type;
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public int getPoint() {
        return point;
    }

    /**
     * @return type + " " + point
     */
    public String toString() {
        return type + " " + point;
    }




}
