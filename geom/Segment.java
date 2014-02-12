package geom;

/**
 * Created with IntelliJ IDEA.
 * User: Lyange Dmitriy
 * Date: 04.02.14
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
public class Segment {
    private Point point1;
    private Point point2;

    public Segment(Point point1, Point point2) {
        this.point1 = new Point(point1.getX(), point1.getY());
        this.point2 = new Point(point2.getX(), point2.getY());
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint1(Point point1) {
        this.point1 = new Point(point1.getX(), point1.getY());
    }

    public void setPoint2(Point point2) {
        this.point2 = new Point(point2.getX(), point2.getY());
    }

    public double getSize() {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
    }

    public boolean onSegment(Point point) {
        if ((Math.min(point1.getX(), point2.getX()) <= point.getX()
                && point.getX() <= Math.max(point1.getX(), point2.getX()))
                && (Math.min(point1.getY(), point2.getY()) <= point.getY()
                && point.getY() <= Math.max(point1.getY(), point2.getY()))) {
            return true;
        }
        return false;
    }

    public double direction(Point point) {
        return (point.getX() - point1.getX()) * (point2.getY() - point1.getY())
                - (point.getY() - point1.getY()) * (point2.getX() - point1.getX());
    }

    public boolean segmentIntersect(Segment segment) {
        double d1, d2, d3, d4;
        d1 = segment.direction(point1);
        d2 = segment.direction(point2);
        d3 = this.direction(segment.getPoint1());
        d4 = this.direction(segment.getPoint2());
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0)
                || (d3 < 0 && d4 > 0))) {
            return true;
        } else if (d1 == 0 && segment.onSegment(point1)) {
            return true;
        } else if (d2 == 0 && segment.onSegment(point2)) {
            return true;
        } else if (d3 == 0 && this.onSegment(segment.getPoint1())) {
            return true;
        } else if (d4 == 0 && this.onSegment(segment.getPoint2())) {
            return true;
        }
        return false;
    }
}
