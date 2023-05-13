import java.awt.*;

public class Ray {

    private double startX;
    private double endX;

    private double startY;
    private double endY;

    private double angle;
    private double distance;

    public Ray(double startX, double endX, double startY, double endY, double angle, double distance){
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.angle = angle;
        this.distance = distance;
    }

    public double getStartX(){
        return startX;
    }

    public double getEndX(){
        return endX;
    }

    public void setEndX(double x) {
        this.endX = x;
    }

    public double getStartY(){
        return startY;
    }

    public double getEndY(){
        return endY;
    }

    public void setEndY(double y) {
        this.endY = y;
    }

    public double getAngle() { return angle; }

    public void setAngle(double a) {
        this.angle = a;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public void drawRay(final Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
    }
}