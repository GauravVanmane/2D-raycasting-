import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Displays and updates the logic for the top-down raycasting implementation.
 * This class is where the collision detection and movement occurs, whereas the
 * RaycasterPerspectivePanel just projects it to a pseudo-3d environment.
 */
public final class RaycasterPanel extends JPanel {

    /**
     * We need to keep a reference to the parent swing app for sizing and
     * other bookkeeping.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Number of rays to fire from the camera.
     */
    private final int RESOLUTION;

    public final ArrayList<RectangleObject> WALLS;

    public final Camera CAMERA;

    public ArrayList<Color> COLORS;

    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RESOLUTION = this.getPreferredSize().width;
        this.requestFocusInWindow(true);

        // initializing walls list
        this.WALLS = new ArrayList<>();

        // adding 10 walls of random size and color to the plane
        for (int i = 0; i < 10; i++) {
            double x = RaycasterUtils.randomDouble(25, (this.RUNNER.getWidth() / 2.0) - 25);
            double y = RaycasterUtils.randomDouble(25, this.RUNNER.getHeight() - 25);
            double w = 64;
            double h = 64;
            Color color = new Color(RaycasterUtils.randomInt(0,255),RaycasterUtils.randomInt(0,255),RaycasterUtils.randomInt(0,255));

            RectangleObject wall = new RectangleObject(x,y,w,h,color);
            WALLS.add(wall);
        }

        // adding a camera
        this.CAMERA = new Camera(this.RUNNER.getWidth() / 4.0,this.RUNNER.getHeight() / 2.0, 270);
        this.addMouseMotionListener(CAMERA.getCameraMotionListener());
        this.addKeyListener(CAMERA.getCameraAngleListener());

    }

    public void update() {
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,RUNNER.getWidth() / 2,RUNNER.getHeight());

        // drawing the walls
        for (RectangleObject wall: WALLS) {
            wall.drawObject(g2d);
        }

        computeRays();
        drawRays(g2d);

        // drawing a camera
        g2d.setColor(Color.yellow);
        g2d.fillOval((int) CAMERA.getX() - 10,(int) CAMERA.getY() - 10,20,20);
    }

    public void computeRays() {
        CAMERA.getRAYS().clear();

        final double FOV = 60;
        final double theta = CAMERA.getCurrAngle();

        final int p = 1280;
        double currAngle;

        this.COLORS = new ArrayList<>();
        for (int i = 1; i < RESOLUTION; i++) {
            currAngle = RaycasterUtils.normalize(i, 1, RESOLUTION,(theta - (FOV/2)),(theta + (FOV/2)));
            double endX = p * Math.cos(Math.toRadians(currAngle));
            double endY = p * Math.sin(Math.toRadians(currAngle));
            double distance = p;

            Ray ray = new Ray(CAMERA.getX(), endX, CAMERA.getY(), endY, currAngle, p);
            Point2D.Double closest = new Point2D.Double(endX, endY);

            // rectangle object to call getColor() on later
            RectangleObject hitWall = new RectangleObject(0,0,0,0,Color.WHITE);

            for (RectangleObject wall : WALLS) {
                Point2D.Double intersectionPoint = wall.rectIntersection(ray);

                if (intersectionPoint != null) {
                    double thisDistance = intersectionPoint.distance(ray.getStartX(), ray.getStartY());

                    if (thisDistance < distance) {
                        distance = thisDistance;
                        closest = intersectionPoint;
                        ray.setDistance(distance);

                        hitWall = wall;
                    }
                }
            }
            ray.setEndX(closest.getX());
            ray.setEndY(closest.getY());

            // SHADING (sometimes is spotty, need to fix)
            if (closest.getX() == hitWall.getX() || closest.getX() == hitWall.getX() + hitWall.getW()){
                COLORS.add(hitWall.getColor().darker());
            } else if (closest.getX() == hitWall.getX() + 3 || closest.getX() == hitWall.getX() + hitWall.getW() - 3) {
                COLORS.add(hitWall.getColor().darker());
            } else {
                COLORS.add(hitWall.getColor());
            }


            CAMERA.addRay(ray);
        }
    }

    public void drawRays(final Graphics2D g2){
        for (Ray ray: CAMERA.getRAYS()) {
            ray.drawRay(g2);
        }
    }

    public Camera getCAMERA() {
        return CAMERA;
    }


}