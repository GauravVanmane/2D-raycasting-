import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RaycasterProjectionPanel extends JPanel {

    /**
     * Root driver object to keep track of sizing.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Overhead panel to access the generated rays.
     */
    private final RaycasterPanel RAYCASTER_PANEL;



    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
    }

    public void update() {
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0,0, this.RUNNER.getWidth(),this.RUNNER.getHeight() / 2);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, this.RUNNER.getHeight() / 2, this.RUNNER.getWidth(), this.RUNNER.getHeight() / 2);

        project(g2d);

    }

    public void project(Graphics2D g2d) {
        double scale = 40;
        int panelWidth = RUNNER.getWidth() / 2;
        double projectionHeight = RUNNER.getHeight();

        ArrayList<Ray> RAYS = this.RAYCASTER_PANEL.getCAMERA().getRAYS();
        ArrayList<Color> COLORS = this.RAYCASTER_PANEL.COLORS;

        for (int i = 0; i < RAYS.size(); i++) {
            Ray ray = RAYS.get(i);
            /*
            program does not seem to have major fish-eye problems, so there is no corrected angle
             */
            double distance = ray.getDistance();
            if (distance != 1280) {
                double wallX = RaycasterUtils.normalize(i, 0, RAYS.size(), 0, panelWidth);
                double wallHeight = projectionHeight * (scale / distance);
                double wallY = (projectionHeight / 2.0) - wallHeight;

                g2d.setColor(COLORS.get(i));
                g2d.fillRect((int) wallX, (int) wallY, 1, (int) wallHeight);
            }
        }
    }
}
