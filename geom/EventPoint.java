package geom;

import geom.Point;

/**
 * Created with IntelliJ IDEA.
 * User: Lyange Dmitriy
 * Date: 05.03.14
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class EventPoint {
    public enum Type {
        LEFT,
        INTERSECTION,
        RIGHT
    }
    private Type type;
    private Point point;

    public EventPoint(Point point, Type type) {
        this.point = point;
        this.type = type;
    }

    public Point getPoint() {
        return this.point;
    }

    public Type getType() {
        return this.type;
    }

}
