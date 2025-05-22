package plotter;

import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

public class Datacontainer {

    private ArrayList<Double> data;

    public Datacontainer() {
        this(true);
    }

    public Datacontainer(boolean randomData) {
        data = new ArrayList<>();
        if (randomData) {
            createRandomData(false);
        } else {
            loadDataFromPath("src/plotter/data.txt");
        }
    }

    // ✅ Constructor جديد يقرأ من ملف يتم اختياره من المستخدم
    public Datacontainer(String filePath) {
        data = new ArrayList<>();
        loadDataFromPath(filePath);
    }

    // ✅ طريقة مرنة لتحميل البيانات من أي مسار ملف
    private void loadDataFromPath(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim().replace(",", ".");
                if (!line.isEmpty()) {
                    try {
                        data.add(Double.parseDouble(line));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid data: " + line, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            createRandomData(false);
        }
    }

    private void createRandomData(boolean reset) {
        if (reset) {
            data.clear();
        }
        for (int i = 0; i < 10; i++) {
            data.add(Math.random() * 300);
        }
    }

    public double[] getData() {
        double[] result = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i);
        }
        return result;
    }

    public int[] getIntData() {
        int[] intData = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            intData[i] = (int) Math.round(data.get(i));
        }
        return intData;
    }

    public double getMax() {
        double max = data.get(0);
        for (double d : data) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public int getIntMax() {
        return (int) Math.round(getMax());
    }

    public double getMin() {
        double min = data.get(0);
        for (double d : data) {
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    public int getIntMin() {
        return (int) Math.round(getMin());
    }

    public double getMedium() {
        double medium = 0;
        for (double d : data) {
            medium += d;
        }
        return medium / data.size();
    }

    public int getIntMedium() {
        return (int) Math.round(getMedium());
    }

    public int getLength() {
        return data.size();
    }
}
