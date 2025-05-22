package plotter;

import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

public class Datacontainer {
  
  private ArrayList<Double> data;

  public Datacontainer() {
    this(true);  // استدعاء constructor آخر مع تحديد أن البيانات عشوائية
  }

  public Datacontainer(boolean randomData) {
    data = new ArrayList<Double>();
    if (randomData) {
      createRandomData(false);  // إنشاء بيانات عشوائية
    } else {
        loadData();  // تحميل البيانات من ملف
    } 
  }

  /**
   * دالة لتحميل البيانات من ملف "data.txt" باستخدام BufferedReader
   */
private void loadData() {
    try {
        BufferedReader reader = new BufferedReader(new FileReader("src/plotter/data.txt"));
        String line;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();  // إزالة المسافات الزائدة

            // استبدال الفاصلة بـ نقطة
            line = line.replace(",", ".");
            
            try {
                // محاولة تحويل السطر إلى Double وإضافته للقائمة
                data.add(Double.parseDouble(line));
            } catch (NumberFormatException e) {
                // إذا كانت القيمة غير صالحة، عرض رسالة خطأ والتوقف
                JOptionPane.showMessageDialog(null, "Invalid data: " + line, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        reader.close();
    } catch (Exception e) {
        // التعامل مع جميع الأخطاء (ملف غير موجود أو خطأ في القراءة أو أخطاء أخرى)
        JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        createRandomData(false);  // استخدام بيانات عشوائية إذا حدث أي خطأ
    }
}

  /**
   * دالة لإنشاء بيانات عشوائية (10 قيم بين 0 و 300)
   */
  private void createRandomData(boolean reset) {
    if (reset) {
      data.clear();  // إذا تم تحديد إعادة تعيين البيانات، نقوم بتصفية البيانات السابقة
    }
    for (int i = 0; i < 10; i++) {
      data.add(Math.random() * 300);  // إضافة 10 أرقام عشوائية بين 0 و 300
    }
  }

  /**
   * دالة لإرجاع البيانات كـ Array من النوع Double
   */
  public double[] getData () {
    double[] result = new double[data.size()];  // إنشاء مصفوفة جديدة بحجم البيانات
    for (int i = 0; i < data.size(); i++) {
      result[i] = data.get(i);  // إضافة البيانات إلى المصفوفة
    }
    return result;
  }

  /**
   * دالة لإرجاع البيانات كـ Array من النوع Integer
   */
  public int[] getIntData() {
    int[] intData = new int[data.size()];  // إنشاء مصفوفة جديدة بحجم البيانات
    for (int i = 0; i < data.size(); i++) {
      intData[i] = (int) Math.round(data.get(i));  // تحويل القيم إلى أعداد صحيحة باستخدام التقريب
    }
    return intData;
  }

  /**
   * دالة لإرجاع القيمة العظمى في البيانات
   */
  public double getMax() {
    double max = data.get(0);  // تعيين أول قيمة كأكبر قيمة مبدئيًا
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i) > max) {
        max =  data.get(i);  // تحديث القيمة العظمى إذا وجدنا قيمة أكبر
      } 
    }
    return max;
  }

  /**
   * دالة لإرجاع القيمة العظمى كـ Integer
   */
  public int getIntMax() {
    return (int) Math.round(getMax());  // تحويل القيمة العظمى إلى Integer باستخدام التقريب
  }

  /**
   * دالة لإرجاع القيمة الصغرى في البيانات
   */
  public double getMin() {
    double min = data.get(0);  // تعيين أول قيمة كأصغر قيمة مبدئيًا
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i) < min) {
        min =  data.get(i);  // تحديث القيمة الصغرى إذا وجدنا قيمة أصغر
      } 
    }
    return min;
  }

  /**
   * دالة لإرجاع القيمة الصغرى كـ Integer
   */
  public int getIntMin() {
    return (int) Math.round(getMin());  // تحويل القيمة الصغرى إلى Integer باستخدام التقريب
  }

  /**
   * دالة لإرجاع المتوسط الحسابي للبيانات
   */
  public double getMedium() {
    double medium = 0;
    for (int i = 0; i < data.size(); i++) {
      medium = medium + data.get(i);  // جمع جميع القيم
    }
    return medium / data.size();  // حساب المتوسط
  }

  /**
   * دالة لإرجاع المتوسط الحسابي كـ Integer
   */
  public int getIntMedium() {
    return (int) Math.round(getMedium());  // تحويل المتوسط إلى Integer باستخدام التقريب
  }

  /**
   * دالة لإرجاع حجم البيانات (عدد القيم في ArrayList)
   */
  public int getLength() {
    return data.size();  // إرجاع عدد العناصر في البيانات
  }

  /**
   * استثناء مخصص للبيانات التي ليست من نوع Double
   */
  private class NoDoubleValueException extends Exception {
    public NoDoubleValueException() {
      super("Kein Double-Wert gefunden");  // رسالة الاستثناء
    }
  } 
}
