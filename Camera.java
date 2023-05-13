import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Camera {

    private double x;
    private double y;

    private ArrayList<Ray> RAYS = new ArrayList<>();
    private double currAngle;
    private final CameraMotionListener CML;
    private final CameraAngleListener CAL;


    public Camera(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.currAngle = angle;

        CML = new CameraMotionListener();
        CAL = new CameraAngleListener();
    }

    private class CameraMotionListener extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
    }

    private class CameraAngleListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent k){
            int keyCode = k.getKeyCode();

            if (keyCode == KeyEvent.VK_LEFT) {
                setCurrAngle(currAngle -= 20);
            }

            if (keyCode == KeyEvent.VK_RIGHT){
                setCurrAngle(currAngle += 20);
            }
        }
    }



    public CameraMotionListener getCameraMotionListener() {
        return CML;
    }

    public CameraAngleListener getCameraAngleListener() { return CAL; }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getCurrAngle() { return currAngle; }

    public void setCurrAngle(double a) {
        this.currAngle = a;
    }

    public ArrayList<Ray> getRAYS() { return RAYS; }

    public void addRay (Ray ray) { this.RAYS.add(ray); }



}