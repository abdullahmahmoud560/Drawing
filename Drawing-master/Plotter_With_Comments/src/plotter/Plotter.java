package plotter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Plotter {

    JFrame mainWindow; // نافذة التطبيق الرئيسية
    JButton loadButton; // زر تحميل البيانات
    JButton rndButton; // زر لتوليد بيانات عشوائية
    JButton clrButton; // زر لمسح البيانات

    PlotterPanel plotterPanel; // اللوحة التي سيتم رسم البيانات عليها
    JPanel controlPanel; // لوحة للتحكم تحتوي على الأزرار
    JPanel dataPanel; // لوحة لعرض الإحصائيات مثل الحد الأقصى والحد الأدنى والمتوسط

    JLabel maxValueLabel; // نص لعرض الحد الأقصى
    JLabel minValueLabel; // نص لعرض الحد الأدنى
    JLabel mediumValueLabel; // نص لعرض المتوسط

    Datacontainer data; // متغير لتخزين البيانات الحالية التي سيتم رسمها

    public Plotter() {

        // إعداد نافذة التطبيق
        mainWindow = new JFrame("Plotter");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // عند إغلاق النافذة، يغلق التطبيق
        mainWindow.setSize(800, 600); // تعيين حجم النافذة

        // إعداد الأزرار
        loadButton = new JButton("Daten laden"); // زر تحميل البيانات
        rndButton = new JButton("Zufallswerte"); // زر لتوليد بيانات عشوائية
        clrButton = new JButton("Löschen"); // زر لمسح البيانات

        // إعداد اللوحات (Panels)
        plotterPanel = new PlotterPanel(); // لوحة الرسم
        controlPanel = new JPanel(); // لوحة التحكم التي تحتوي على الأزرار
        dataPanel = new JPanel(); // لوحة البيانات التي تحتوي على الإحصائيات
        dataPanel.setLayout(new GridLayout(3, 1)); // تنسيق العمود الواحد في لوحة البيانات

        // إعداد النصوص الخاصة بالإحصائيات
        maxValueLabel = new JLabel("Maximum: "); // نص لعرض الحد الأقصى
        minValueLabel = new JLabel("Minimum: "); // نص لعرض الحد الأدنى
        mediumValueLabel = new JLabel("Durchschnitt: "); // نص لعرض المتوسط

        // إعداد لوحة التحكم وتوزيع الأزرار فيها
        controlPanel.setLayout(new GridLayout(3, 1)); // تنسيق الأزرار عموديًا
        controlPanel.add(loadButton); // إضافة زر تحميل البيانات
        controlPanel.add(rndButton); // إضافة زر توليد بيانات عشوائية

        // إضافة النصوص إلى لوحة البيانات
        dataPanel.add(maxValueLabel);
        dataPanel.add(minValueLabel);
        dataPanel.add(mediumValueLabel);

        // إضافة اللوحات إلى النافذة الرئيسية
        mainWindow.getContentPane().add(BorderLayout.CENTER, plotterPanel); // إضافة لوحة الرسم إلى الوسط
        mainWindow.getContentPane().add(BorderLayout.WEST, controlPanel); // إضافة لوحة التحكم إلى الجهة اليسرى
        mainWindow.getContentPane().add(BorderLayout.EAST, dataPanel); // إضافة لوحة البيانات إلى الجهة اليمنى
        mainWindow.getContentPane().add(BorderLayout.SOUTH, clrButton); // إضافة زر مسح البيانات إلى الأسفل

        // إضافة مستمعين (listeners) للأزرار
        loadButton.addActionListener(e -> createData(false)); // عند الضغط على "تحميل البيانات"، سيتم تحميل بيانات عادية
        rndButton.addActionListener(e -> createData(true)); // عند الضغط على "توليد بيانات عشوائية"، سيتم توليد بيانات عشوائية
        clrButton.addActionListener(e -> clearData()); // عند الضغط على "مسح البيانات"، سيتم مسح البيانات

        mainWindow.setVisible(true); // جعل النافذة مرئية

        // إعادة الرسم عند تغيير حجم النافذة
        mainWindow.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                plotterPanel.repaint(); // إعادة رسم لوحة الرسم عند تغيير حجم النافذة
            }
        });
    }

    public static void main(String[] args) {
        // تشغيل التطبيق عند بدء البرنامج
        SwingUtilities.invokeLater(() -> new Plotter());
    }

    private void createData(boolean randomData) {
        // توليد البيانات بناءً على ما إذا كانت بيانات عشوائية أم لا
        data = new Datacontainer(randomData); // استخدام الكلاس Datacontainer لتحميل البيانات
        plotterPanel.setData(data); // تمرير البيانات إلى لوحة الرسم
        plotterPanel.repaint(); // إعادة رسم اللوحة

        // تحديث النصوص المعروضة في لوحة البيانات بناءً على البيانات
        maxValueLabel.setText("Maximum: " + data.getIntMax());
        minValueLabel.setText("Minimum: " + data.getIntMin());
        mediumValueLabel.setText("Durchschnitt: " + data.getIntMedium());
    }

    public void clearData() {
        // مسح البيانات من لوحة الرسم
        plotterPanel.setData(null); // مسح البيانات
        plotterPanel.repaint(); // إعادة رسم اللوحة بعد المسح

        // إعادة تعيين النصوص المعروضة في لوحة البيانات
        maxValueLabel.setText("Maximum: ");
        minValueLabel.setText("Minimum: ");
        mediumValueLabel.setText("Durchschnitt: ");
    }
}
