package geom;

/**
 * Created with IntelliJ IDEA.
 * User: Lyange Dmitriy
 * Date: 04.02.14
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    private int x;
    private int y;
    private Segment segment; // only for points-parts of segment
    private boolean isLeft; // only for points-parts of segment

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public boolean getIsLeft() {
        return isLeft;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public static int direction(Point a, Point b, Point c) {
        int count = (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
        if (count < 0) {
            return -1; // clockwise
        } else if (count > 0) {
            return 1; // counter-clockwise
        } else {
            return 0; // collinear
        }
    }

    @Override
    public boolean equals(Object point) {
        if (point.getClass().getName() != "geom.Point") {
            return false;
        }
        return (this.getX() == ((Point) point).getX()) && (this.getY() == ((Point) point).getY());
    }

}