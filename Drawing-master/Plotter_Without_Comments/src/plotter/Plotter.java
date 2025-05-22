// Plotter.java
package plotter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Plotter {

    JFrame mainWindow;
    JButton loadButton;
    JButton loadFileButton; // زر جديد لتحميل ملف خارجي
    JButton rndButton;
    JButton clrButton;
    JButton colorButton;
    JButton toggleChartTypeButton;
    JCheckBox normalizeCheckbox;

    PlotterPanel plotterPanel;
    JPanel controlPanel;
    JPanel dataPanel;

    JLabel maxValueLabel;
    JLabel minValueLabel;
    JLabel mediumValueLabel;

    Datacontainer data;

    public Plotter() {

        mainWindow = new JFrame("Plotter");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 600);

        loadButton = new JButton("Daten laden");
        loadFileButton = new JButton("Datei wählen"); // جديد
        rndButton = new JButton("Zufallswerte");
        clrButton = new JButton("Löschen");
        colorButton = new JButton("Farbe ändern");
        toggleChartTypeButton = new JButton("Typ wechseln");
        normalizeCheckbox = new JCheckBox("Y Werte normalisieren");

        plotterPanel = new PlotterPanel();
        controlPanel = new JPanel();
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(3, 1));

        maxValueLabel = new JLabel("Maximum: ");
        minValueLabel = new JLabel("Minimum: ");
        mediumValueLabel = new JLabel("Durchschnitt: ");

        controlPanel.setLayout(new GridLayout(7, 1)); // زودنا 1 للسطر الجديد
        controlPanel.add(loadButton);
        controlPanel.add(loadFileButton); // جديد
        controlPanel.add(rndButton);
        controlPanel.add(colorButton);
        controlPanel.add(toggleChartTypeButton);
        controlPanel.add(normalizeCheckbox);

        dataPanel.add(maxValueLabel);
        dataPanel.add(minValueLabel);
        dataPanel.add(mediumValueLabel);

        mainWindow.getContentPane().add(BorderLayout.CENTER, plotterPanel);
        mainWindow.getContentPane().add(BorderLayout.WEST, controlPanel);
        mainWindow.getContentPane().add(BorderLayout.EAST, dataPanel);
        mainWindow.getContentPane().add(BorderLayout.SOUTH, clrButton);

        loadButton.addActionListener(e -> createData(false));
        rndButton.addActionListener(e -> createData(true));
        clrButton.addActionListener(e -> clearData());
        colorButton.addActionListener(e -> plotterPanel.changeColor());
        toggleChartTypeButton.addActionListener(e -> plotterPanel.toggleChartType());

        // التطبيع
        normalizeCheckbox.addActionListener(e -> {
            plotterPanel.setNormalizeValues(normalizeCheckbox.isSelected());
        });

        // تحميل ملف خارجي
        loadFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(mainWindow);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                data = new Datacontainer(file.getAbsolutePath());
                plotterPanel.setData(data);
                updateLabels();
            }
        });

        mainWindow.setVisible(true);

        mainWindow.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                plotterPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Plotter());
    }

    private void createData(boolean randomData) {
        data = new Datacontainer(randomData);
        plotterPanel.setData(data);
        updateLabels();
    }

    private void updateLabels() {
        if (data != null) {
            maxValueLabel.setText("Maximum: " + data.getIntMax());
            minValueLabel.setText("Minimum: " + data.getIntMin());
            mediumValueLabel.setText("Durchschnitt: " + data.getIntMedium());
        }
    }

    public void clearData() {
        plotterPanel.setData(null);
        plotterPanel.repaint();
        maxValueLabel.setText("Maximum: ");
        minValueLabel.setText("Minimum: ");
        mediumValueLabel.setText("Durchschnitt: ");
    }
}
