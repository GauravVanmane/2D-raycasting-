import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class RectangleObject implements Drawable {
    private double x;
    private double y;
    private double w;
    private double h;
    private Color color;


    public RectangleObject(double x, double y, double w, double h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public Point2D.Double rectIntersection(final Ray ray) {
        Line2D.Double[] sides = {
                new Line2D.Double(x,y,x + w, y), // top
                new Line2D.Double(x,y,x,y + h), // left
                new Line2D.Double(x + w, y + h, x, y + h),  // bottom
                new Line2D.Double(x + w, y + h, x + w, y) // right
        };

        Line2D.Double rayLine = new Line2D.Double(ray.getStartX(),ray.getStartY(),ray.getEndX(),ray.getEndY());

        double distance = 99999999;
        Point2D.Double intersectionPoint = null;

        for (Line2D.Double side: sides) {
            Point2D.Double intersection = RaycasterUtils.intersection(rayLine,side);

            if (intersection != null) {
                double thisDist = intersection.distance(ray.getStartX(), ray.getStartY());

                if (thisDist < distance) {
                    distance = thisDist;
                    intersectionPoint = intersection;
                }
            }
        }
        return intersectionPoint;
    }

    @Override
    public void drawObject(final Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect((int) x,(int) y,(int) w,(int) h);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getW() {
        return w;
    }
    public double getH() {
        return h;
    }
    public Color getColor() {
        return this.color;
    }


}