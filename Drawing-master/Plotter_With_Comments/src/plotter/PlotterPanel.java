package plotter;

import javax.swing.*;
import java.awt.*;

public class PlotterPanel extends JPanel {

    // تعريف المتغير لتخزين البيانات التي سيتم رسمها
    Datacontainer data;

    // إعادة تعريف دالة paintComponent للرسم داخل اللوحة
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // استدعاء الدالة الأساسية من JPanel لرسم العناصر الافتراضية (مثل الخلفية)

        // تحويل الكائن Graphics إلى Graphics2D لأن Graphics2D يوفر ميزات إضافية للرسم المتقدم
        Graphics2D g2d = (Graphics2D) g;

        // رسم المحاور X و Y
        g2d.drawLine(20, getHeight() - 20, getWidth() - 40, getHeight() - 20);  // رسم محور X من (20, height - 20) إلى (width - 40, height - 20)
        g2d.drawLine(20, getHeight() - 20, 20, 20);  // رسم محور Y من (20, height - 20) إلى (20, 20)

        // رسم رؤوس الأسهم على المحاور لتوضيح الاتجاه
        g2d.drawPolygon(new int[]{getWidth() - 40, getWidth() - 45, getWidth() - 45}, // رأس السهم لمحور X
                        new int[]{getHeight() - 20, getHeight() - 25, getHeight() - 15}, 3); // رأس السهم لمحور X

        g2d.drawPolygon(new int[]{15, 20, 25},  // رأس السهم لمحور Y
                        new int[]{20, 10, 20}, 3); // رأس السهم لمحور Y

        // إذا كانت البيانات موجودة، يتم رسمها على اللوحة
        if (data != null && data.getLength() > 0) {
            // الحصول على بيانات Y من الكائن Datacontainer
            int[] yData = data.getIntData();  // هذه الدالة ترجع البيانات كـ مصفوفة من الأعداد الصحيحة
            int n = yData.length;  // عدد النقاط (الطول)
            int spacing = (getWidth() - 80) / n;  // المسافة بين النقاط على محور X
            int startX = 30;  // تحديد النقطة الأولى على محور X

            g2d.setColor(Color.RED);  // تحديد اللون الأحمر لرسم البيانات
            g2d.setStroke(new BasicStroke(2));  // تحديد سمك الخط بمقدار 2 (خط سميك)

            // رسم الخط البياني بين النقاط
            for (int i = 0; i < n - 1; i++) {
                // حساب إحداثيات Y للنقطتين المتتاليتين (من yData)
                int y1 = getHeight() - 20 - yData[i];  // النقطة الأولى
                int y2 = getHeight() - 20 - yData[i + 1];  // النقطة الثانية

                // رسم خط بين النقطتين على اللوحة
                g2d.drawLine(startX + i * spacing, y1, startX + (i + 1) * spacing, y2);  // رسم الخط من النقطة الأولى إلى الثانية
            }
        }
    }

    // دالة لتعيين البيانات التي سيتم رسمها
    public void setData(Datacontainer values) {
        this.data = values;  // تخزين البيانات المُراد رسمها
    }
}
