// PlotterPanel.java
package plotter;

import javax.swing.*;
import java.awt.*;

public class PlotterPanel extends JPanel {

    private Color lineColor = Color.RED;
    private boolean drawBars = false;
    private int animatedIndex = 0;
    private Timer timer;
    Datacontainer data;

    private boolean normalizeValues = false;  // <-- جديد

    public void setNormalizeValues(boolean normalize) {
        this.normalizeValues = normalize;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background dotted grid
        g2d.setColor(new Color(200, 200, 200));
        for (int x = 20; x < getWidth(); x += 40) {
            for (int y = 20; y < getHeight(); y += 40) {
                g2d.fillOval(x, y, 2, 2);
            }
        }

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(20, getHeight() - 20, getWidth() - 40, getHeight() - 20);
        g2d.drawLine(20, getHeight() - 20, 20, 20);

        // Axis arrows
        g2d.drawPolygon(new int[]{getWidth() - 40, getWidth() - 45, getWidth() - 45},
                        new int[]{getHeight() - 20, getHeight() - 25, getHeight() - 15}, 3);
        g2d.drawPolygon(new int[]{15, 20, 25},
                        new int[]{20, 10, 20}, 3);

        if (data != null && data.getLength() > 0) {
            int[] yData = data.getIntData();
            int n = yData.length;
            int spacing = (getWidth() - 80) / n;
            int startX = 30;

            double maxY = data.getMax();

            // تطبيع القيم إذا مفعّل
            double[] normalizedData = new double[n];
            if (normalizeValues) {
                int maxVal = data.getIntMax();
                int minVal = data.getIntMin();
                int range = maxVal - minVal;
                if (range == 0) range = 1; // لتجنب القسمة على صفر

                for (int i = 0; i < n; i++) {
                    normalizedData[i] = (yData[i] - minVal) * 1.0 / range * (getHeight() - 40);
                }
                maxY = getHeight() - 40;
            } else {
                for (int i = 0; i < n; i++) {
                    normalizedData[i] = yData[i];
                }
            }

            g2d.setColor(lineColor);
            g2d.setStroke(new BasicStroke(2));
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));

            for (int i = 0; i < n && i < animatedIndex; i++) {
                int x = startX + i * spacing;
                int y = getHeight() - 20 - (int)normalizedData[i];

                if (drawBars) {
                    g2d.fillRect(x - 5, y, 10, (int)normalizedData[i]);
                } else if (i < n - 1 && i + 1 < animatedIndex) {
                    int yNext = getHeight() - 20 - (int)normalizedData[i + 1];
                    g2d.drawLine(x, y, x + spacing, yNext);
                }

                // Draw dot
                g2d.fillOval(x - 3, y - 3, 6, 6);

                // Draw label
                String label = normalizeValues ?
                    String.format("%.1f", normalizedData[i]) :
                    String.valueOf(yData[i]);
                g2d.drawString(label, x - 10, y - 5);
            }

            // Y axis labels - تعديلها لتناسب التطبيع
            g2d.setColor(Color.BLACK);
            for (int i = 0; i <= 5; i++) {
                int val;
                int y;
                if (normalizeValues) {
                    val = (int)(maxY * i / 5);
                    y = getHeight() - 20 - (getHeight() - 40) * i / 5;
                    g2d.drawString(String.valueOf(val), 2, y);
                } else {
                    val = (int)(maxY * i / 5);
                    y = getHeight() - 20 - (int)((maxY) * i / 5);
                    g2d.drawString(String.valueOf(val), 2, y);
                }
            }

            // X axis labels
            for (int i = 0; i < n; i++) {
                int x = startX + i * spacing;
                g2d.drawString(String.valueOf(i + 1), x, getHeight() - 5);
            }
        }
    }

    public void changeColor() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA};
        int index = (java.util.Arrays.asList(colors).indexOf(lineColor) + 1) % colors.length;
        lineColor = colors[index];
        repaint();
    }

    public void toggleChartType() {
        drawBars = !drawBars;
        repaint();
    }

    public void setData(Datacontainer values) {
        this.data = values;
        animatedIndex = 0;

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(100, e -> {
            animatedIndex++;
            if (animatedIndex >= data.getLength()) {
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }
}
