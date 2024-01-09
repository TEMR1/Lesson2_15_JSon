import java.awt.*;
import java.util.ArrayList;

public class Line {
    private Color color;
    private final ArrayList<LinePoint> points;

    Line(Color color, ArrayList<LinePoint> points) {
        this.color = color;
        this.points = points;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<LinePoint> getPoints() {
        return points;
    }
}
