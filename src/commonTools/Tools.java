/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commonTools;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
*
* @author zhang
*/
/**
* @author acer
*
*/
public class Tools {

	/**
	 * 
	 * @param num 最大值
	 * @return [0,num)中的随机数
	 */
    public static int rand(int num) {
        Random r = new Random();
        return r.nextInt(num);
    }

    /**
     * 随机生成一个小数
     * @return
     */
    public static float randFloat() {
        Random r = new Random();
        return r.nextFloat();
    }
    
    /**
     * 输入格式 yyyy-MM-dd HH:mm:ss
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return 时间间隔，单位s
     */
    public static long caculateTimeInterval(String startTime, String endTime) {
        SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = (sfTime.parse(endTime).getTime() - sfTime.parse(startTime).getTime()) / 1000;
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return time;
    }
    
    /**
     * 解析时间
     * @param time 时间戳 (距离1970年的毫秒数)
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseTime(String time){
        SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = null;
        Date date = new Date(Long.parseLong(time)*1000);
        result = sfTime.format(date);
        return result;
        
    }
    
    /**
     * 计算和基准时间相差interval秒的时间
     * @param time 基准时间
     * @param interval 时间间隔
     * @return 计算时间
     */
    public static String getPreOrNextSecond(String time, Integer interval) {
        SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sfTime.parse(time));
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.add(Calendar.SECOND, interval);
        return sfTime.format(c.getTime());
    }
    
    /**
     * 算和基准时间相差interval天的时间
     * @param curDay基准时间
     * @param interval 时间间隔
     * @return 计算时间
     */
    public static String getPreOrNextDay(String curDay, int interval) {
        SimpleDateFormat sfDay = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String day;
        try {
            c.setTime(sfDay.parse(curDay));
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.add(Calendar.DAY_OF_MONTH, interval);
        day = sfDay.format(c.getTime()).toString();
        return day;
    }

    /**
     * 计算和基准时间相差interval分钟的时间
     * @param time 基准时间
     * @param interval 时间间隔
     * @return 计算时间
     */
    public static String getPreOrNextMinute(String time, Integer interval) {
        SimpleDateFormat sfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sfMinute.parse(time));
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.add(Calendar.MINUTE, interval);
        return sfMinute.format(c.getTime());
    }

    /**
     * 获取小时数
     * @param time 时间
     * @return 小时
     */
    public static int getHour(String time) {
        SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sfTime.parse(time));
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取天数
     * @param time 时间
     * @return 日期(天)
     */
    public static int getDay(String time) {
        SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sfTime.parse(time));
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 格式化时间
     * @param time
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formartDate(String time) {
        SimpleDateFormat sfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sfDay.parse(time);
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sfDay.format(date);

    }
    /**
     * 格式化时间
     * @param time
     * @return yyyy-MM-dd
     */
    public static String formartDate2(String time) {
        SimpleDateFormat sfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sfDay.parse(time);
        } catch (ParseException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sfDay.format(date);

    }
    /**
     * 频度统计
     * @param list 数据列表(float)
     * @param precision 精度
     * @return 数值->频度
     */
    public static HashMap<BigDecimal, Integer[]> caculatePoweLaw(ArrayList<Float> list, Integer precision) {
        HashMap<BigDecimal, Integer[]> powerLawHash = new HashMap<>();
        float num;
        BigDecimal result;
        for (int i = 0; i < list.size(); i++) {
            num = (float) list.get(i);
            result = new BigDecimal(num).setScale(precision, BigDecimal.ROUND_HALF_UP);
            if (powerLawHash == null) {
                powerLawHash = new HashMap<BigDecimal, Integer[]>();
            }
            if (!powerLawHash.containsKey(result)) {
                Integer[] temp = new Integer[1];
                temp[0] = 0;
                powerLawHash.put(result, temp);
            }
            powerLawHash.get(result)[0]++;

        }
        return powerLawHash;
    }

    /**
     * 输出频度统计结果
     * @param powerLawHash 频度统计结果集
     */
    public static void printPowerLaw(HashMap<BigDecimal, Integer[]> powerLawHash) {
        Iterator<Map.Entry<BigDecimal, Integer[]>> powerLawHash_iterator = powerLawHash.entrySet().iterator();
        BigDecimal num;
        while (powerLawHash_iterator.hasNext()) {
            Map.Entry<BigDecimal, Integer[]> powerLawHash_next = powerLawHash_iterator.next();
            num = powerLawHash_next.getKey();
//            if(num.toString().equals("1")){
            System.out.println(powerLawHash_next.getKey() + "\t" + powerLawHash_next.getValue()[0]);
//            }

        }
    }

    public static List<Entry<String, Float>> rankHashMap(HashMap<String, Float> topicPostCount) {
        List<Map.Entry<String, Float>> temp = new ArrayList<Entry<String, Float>>(topicPostCount.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Float>>() {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                if (o2.getValue().compareTo(o1.getValue()) > 0f) {
                    return 1;
                } else if (o2.getValue().compareTo(o1.getValue()) < 0f) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return temp;
    }

    /**
     *
     * @param topicPostCount
     */
    public static List<Entry<String, Float[]>> rankHashMap2(HashMap<String, Float[]> topicPostCount) {
        List<Map.Entry<String, Float[]>> temp = new ArrayList<Entry<String, Float[]>>(topicPostCount.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Float[]>>() {
            @Override
            public int compare(Map.Entry<String, Float[]> o1, Map.Entry<String, Float[]> o2) {
                if (o2.getValue()[0].compareTo(o1.getValue()[0]) > 0f) {
                    return 1;
                } else if (o2.getValue()[0].compareTo(o1.getValue()[0]) < 0f) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return temp;
    }

    /**
     *
     * @param topicPostCount
     * @return ranked map by value
     */
    public static List<Entry<String, Integer[]>> rankHashMap_Int(HashMap<String, Integer[]> topicPostCount) {
        List<Map.Entry<String, Integer[]>> temp = new ArrayList<Entry<String, Integer[]>>(topicPostCount.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Integer[]>>() {
            @Override
            public int compare(Map.Entry<String, Integer[]> o1, Map.Entry<String, Integer[]> o2) {
                if (o2.getValue()[0].compareTo(o1.getValue()[0]) > 0f) {
                    return 1;
                } else if (o2.getValue()[0].compareTo(o1.getValue()[0]) < 0f) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return temp;
    }
    /**
     * 降序
     * @param map
     * @return
     */
    public static List<Entry<String, Double>> rankHashMap_Double(HashMap<String, Double> map) {
        List<Map.Entry<String, Double>> temp = new ArrayList<Entry<String, Double>>(map.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (o2.getValue().compareTo(o1.getValue()) > 0f) {
                    return 1;
                } else if (o2.getValue().compareTo(o1.getValue()) < 0f) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return temp;
    }

    /**
     *
     * @param topicPostCount
     * @return ranked keyList
     */
    public static ArrayList<String> rankHashMap_Int_List(HashMap<String, Integer[]> topicPostCount) {
        @SuppressWarnings("unchecked")
		List<Map.Entry<String, Integer[]>> temp = new ArrayList(topicPostCount.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Integer[]>>() {
            @Override
            public int compare(Map.Entry<String, Integer[]> o1, Map.Entry<String, Integer[]> o2) {
                if (o2.getValue()[0].compareTo(o1.getValue()[0]) > 0f) {
                    return 1;
                } else if (o2.getValue()[0].compareTo(o1.getValue()[0]) < 0f) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        String tempName;
        ArrayList<String> list = new ArrayList<String>();
//        for (Entry<String, Integer[]> entry : topicPostCount.entrySet()) {
//            tempName = entry.getKey();
////            Integer[] integers = entry.getValue();
//            list.add(tempName);
//        }
        for (int i = 0; i < temp.size(); i++) {
			tempName=temp.get(i).getKey();
			list.add(tempName);
		}
        return list;
    }
    /**
     * 排序Map，返回前num个
     * @param topicPostCount HashMap<String, Integer[]>
     * @param num
     * @return ArrayList<String>
     */
    public static ArrayList<String> rankHashMap_Int_List(HashMap<String, Integer[]> topicPostCount, int num) {
        @SuppressWarnings("unchecked")
		List<Map.Entry<String, Integer[]>> temp = new ArrayList(topicPostCount.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Integer[]>>() {
            @Override
            public int compare(Map.Entry<String, Integer[]> o1, Map.Entry<String, Integer[]> o2) {
                if (o2.getValue()[0].compareTo(o1.getValue()[0]) > 0) {
                    return 1;
                } else if (o2.getValue()[0].compareTo(o1.getValue()[0]) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        String tempName;
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        for (Entry<String, Integer[]> entry : temp) {
            tempName = entry.getKey();
//            System.out.println(tempName+"\t"+entry.getValue()[0]);
//            Integer[] integers = entry.getValue();
            list.add(tempName);
            i++;
            if (i == num) {
                break;
            }
        }
        return list;
    }

    public static ArrayList<String> rankHashMap_Int_ListWithCondition(HashMap<String, Integer[]> topicPostCount, int num) {
        List<Map.Entry<String, Integer[]>> temp = new ArrayList(topicPostCount.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Integer[]>>() {
            @Override
            public int compare(Map.Entry<String, Integer[]> o1, Map.Entry<String, Integer[]> o2) {
                if (o2.getValue()[0].compareTo(o1.getValue()[0]) > 0f) {
                    return 1;
                } else if (o2.getValue()[0].compareTo(o1.getValue()[0]) < 0f) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        String tempName;
        ArrayList<String> list = new ArrayList<String>();
//        int i=0;
        int tempNum;
        for (Entry<String, Integer[]> entry : topicPostCount.entrySet()) {
            tempName = entry.getKey();
            tempNum = entry.getValue()[0];
            if (tempNum >= num) {
                list.add(tempName);
            }
        }
        return list;
    }

    public static float getFloat(float num, int precision) {
        BigDecimal result;
        result = new BigDecimal(num).setScale(precision, BigDecimal.ROUND_HALF_UP);
        float parseFloat = Float.parseFloat(result.toString());
        return parseFloat;
    }

    public static void multpleRegression(ArrayList<Double> Y, ArrayList<Double> X) {
        if (Y.size() != X.size()) {
            System.out.println("Y.size()!=X.size()");
            return;
        }
        double avg_X, avg_Y;
        double b1, b2;
        double r2;
        double r;
        ArrayList<Double> X2 = new ArrayList<Double>(), XY = new ArrayList<Double>();
        double whole_Y = 0, whole_X = 0;
        double whole_X2 = 0, whole_Y2 = 0, whole_XY = 0;
        int i;
        for (i = 0; i < X.size(); i++) {
            whole_X += X.get(i);
            whole_Y += Y.get(i);
        }
        avg_X = (double) whole_X / X.size();
        avg_Y = (double) whole_Y / Y.size();
//        System.out.println("avg_Y = " + avg_Y + "avg_X = " + avg_X);
        for (i = 0; i < X.size(); i++) {
//            X2.add((X.get(i)-avg_X)*(X.get(i)-avg_X));              
//            Z2.add((Z.get(i)-avg_Z)*(Z.get(i)-avg_Z));              
//            XY.add((X.get(i)-avg_X)*(Y.get(i)-avg_Y));              
//            XZ.add((X.get(i)-avg_X)*(Z.get(i)-avg_Z));              
//            YZ.add((Y.get(i)-avg_Y)*(Z.get(i)-avg_Z));  
            whole_X2 += (X.get(i) - avg_X) * (X.get(i) - avg_X);
            whole_Y2 += (Y.get(i) - avg_Y) * (Y.get(i) - avg_Y);
            whole_XY += (X.get(i) - avg_X) * (Y.get(i) - avg_Y);
        }
//        System.out.print("whole_X2 = " + whole_X2 + "\twhole_Y2 = " + whole_Y2);
//        System.out.println("\twhole_XY = " + whole_XY);

        b2 = whole_XY / whole_X2;
        b1 = avg_Y - b2 * avg_X;

        r2 = whole_XY * whole_XY / (whole_X2 * whole_Y2);
        r = whole_XY / Math.sqrt(whole_X2 * whole_Y2);

//        System.out.println("b1 = \t" + b1);
//        System.out.println("b2 = \t" + b2);
//        System.out.println("r2 = \t" + r2);
//        System.out.println("r = \t" + r);
//        System.out.println("Y = " + b1 + "+" + b2 + "*X1");
        System.out.println(b2 + "\t" + b1 + "\t" + r2);
    }

    public static double multpleRegression2(ArrayList<Double> Y, ArrayList<Double> X) {
        if (Y.size() != X.size()) {
            System.out.println("Y.size()!=X.size()");
            return 0;
        }
        double avg_X, avg_Y;
        double b1, b2;
        double r2;
        double r;
        ArrayList<Double> X2 = new ArrayList<Double>(), XY = new ArrayList<Double>();
        double whole_Y = 0, whole_X = 0;
        double whole_X2 = 0, whole_Y2 = 0, whole_XY = 0;
        int i;
        for (i = 0; i < X.size(); i++) {
            whole_X += X.get(i);
            whole_Y += Y.get(i);
        }
        avg_X = (double) whole_X / X.size();
        avg_Y = (double) whole_Y / Y.size();
//        System.out.println("avg_Y = " + avg_Y + "avg_X = " + avg_X);
        for (i = 0; i < X.size(); i++) {
//            X2.add((X.get(i)-avg_X)*(X.get(i)-avg_X));              
//            Z2.add((Z.get(i)-avg_Z)*(Z.get(i)-avg_Z));              
//            XY.add((X.get(i)-avg_X)*(Y.get(i)-avg_Y));              
//            XZ.add((X.get(i)-avg_X)*(Z.get(i)-avg_Z));              
//            YZ.add((Y.get(i)-avg_Y)*(Z.get(i)-avg_Z));  
            whole_X2 += (X.get(i) - avg_X) * (X.get(i) - avg_X);
            whole_Y2 += (Y.get(i) - avg_Y) * (Y.get(i) - avg_Y);
            whole_XY += (X.get(i) - avg_X) * (Y.get(i) - avg_Y);
        }
//        System.out.print("whole_X2 = " + whole_X2 + "\twhole_Y2 = " + whole_Y2);
//        System.out.println("\twhole_XY = " + whole_XY);

        b2 = whole_XY / whole_X2;
        b1 = avg_Y - b2 * avg_X;

        r2 = whole_XY * whole_XY / (whole_X2 * whole_Y2);
        r = whole_XY / Math.sqrt(whole_X2 * whole_Y2);

//        System.out.println("b1 = \t" + b1);
//        System.out.println("b2 = \t" + b2);
//        System.out.println("r2 = \t" + r2);
//        System.out.println("r = \t" + r);
//        System.out.println("Y = " + b1 + "+" + b2 + "*X1");
//        System.out.println(b2+"\t"+b1+"\t"+r2);
        return b2;
    }

    public static void multpleRegression(ArrayList<Double> Y, ArrayList<Double> X, ArrayList<Double> Z) {
        if (Y.size() != Z.size() || Y.size() != X.size()) {
            System.out.println("Y.size()!=Z.size()||Y.size()!=X.size()");
            return;
        }
        double avg_X, avg_Z, avg_Y;
        double b1, b2, b3;
        double r2;
        ArrayList<Double> X2 = new ArrayList<Double>(), Z2 = new ArrayList<Double>(), XZ = new ArrayList<Double>(), XY = new ArrayList<Double>(), YZ = new ArrayList<Double>();
        double whole_Y = 0, whole_X = 0, whole_Z = 0;
        double whole_X2 = 0, whole_Z2 = 0, whole_Y2 = 0, whole_XY = 0, whole_XZ = 0, whole_YZ = 0;
        int i;
        for (i = 0; i < X.size(); i++) {
            whole_X += X.get(i);
            whole_Z += Z.get(i);
            whole_Y += Y.get(i);
        }
        avg_X = (double) whole_X / X.size();
        avg_Z = (double) whole_Z / Z.size();
        avg_Y = (double) whole_Y / Y.size();
        System.out.println("avg_Y = " + avg_Y + "avg_X = " + avg_X + "avg_Z = " + avg_Z);
        for (i = 0; i < X.size(); i++) {
//            X2.add((X.get(i)-avg_X)*(X.get(i)-avg_X));              
//            Z2.add((Z.get(i)-avg_Z)*(Z.get(i)-avg_Z));              
//            XY.add((X.get(i)-avg_X)*(Y.get(i)-avg_Y));              
//            XZ.add((X.get(i)-avg_X)*(Z.get(i)-avg_Z));              
//            YZ.add((Y.get(i)-avg_Y)*(Z.get(i)-avg_Z));  
            whole_X2 += (X.get(i) - avg_X) * (X.get(i) - avg_X);
            whole_Y2 += (Y.get(i) - avg_Y) * (Y.get(i) - avg_Y);
            whole_Z2 += (Z.get(i) - avg_Z) * (Z.get(i) - avg_Z);
            whole_XY += (X.get(i) - avg_X) * (Y.get(i) - avg_Y);
            whole_XZ += (X.get(i) - avg_X) * (Z.get(i) - avg_Z);
            whole_YZ += (Y.get(i) - avg_Y) * (Z.get(i) - avg_Z);
        }
        System.out.print("whole_X2 = " + whole_X2 + "\twhole_Z2 = " + whole_Z2 + "\twhole_Y2 = " + whole_Y2);
        System.out.println("\twhole_XY = " + whole_XY + "\twhole_XZ = " + whole_XZ + "\twhole_YZ = " + whole_YZ);

        System.out.println("whole_X2*whole_Z2-whole_XZ*whole_XZ = " + ((whole_X2 * whole_Z2) - (whole_XZ * whole_XZ)));

        System.out.println("whole_X2*whole_Z2= " + ((whole_X2 * whole_Z2)));
        System.out.println("whole_XZ*whole_XZ = " + (whole_XZ * whole_XZ));

        b2 = ((whole_XY * whole_Z2) - (whole_YZ * whole_XZ)) / (whole_X2 * whole_Z2 - whole_XZ * whole_XZ);
        b3 = ((whole_YZ * whole_X2) - (whole_XY * whole_XZ)) / (whole_X2 * whole_Z2 - whole_XZ * whole_XZ);
        b1 = avg_Y - b2 * avg_X - b3 * avg_Z;

        r2 = (b2 * whole_XY + b3 * whole_YZ) / whole_Y2;

        System.out.println("b1 = \t" + b1);
        System.out.println("b2 = \t" + b2);
        System.out.println("b3 = \t" + b3);
        System.out.println("r2 = \t" + r2);
        System.out.println("Y = " + b1 + "+" + b2 + "*X1+" + b3 + "*X2");
    }

    public static String getSystemTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ly_time = sdf.format(new java.util.Date());
        return ly_time;
    }
    public static String getSystemTimeDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ly_time = sdf.format(new java.util.Date());
        return ly_time;
    }
    public static String getSystemTimeMondayOfWeek() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar calendar=Calendar.getInstance(Locale.CHINA);
    	calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	String format = sdf.format(calendar.getTime()).replaceAll("\\d{2}:\\d{2}:\\d{2}", "00:00:00");
        return format;
    }
    public static String getSystemTimeYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String ly_time = sdf.format(new java.util.Date());
        return ly_time;
    }

    public static int calculateHashCount(HashMap<String, Integer[]> temp) {
        int sum = 0;
        Collection<Integer[]> values = temp.values();
        for (Integer[] integers : values) {
            sum += integers[0];
        }
        return sum;
    }

    public static HashMap<Integer, Integer[]> caculatePoweLaw_IntList(ArrayList<Integer> list) {
        HashMap<Integer, Integer[]> powerLawHash = new HashMap<Integer, Integer[]>();
        int num;
        BigDecimal result;
        for (int i = 0; i < list.size(); i++) {
            num = list.get(i);
            if (powerLawHash == null) {
                powerLawHash = new HashMap<Integer, Integer[]>();
            }
            if (!powerLawHash.containsKey(num)) {
                Integer[] temp = new Integer[1];
                temp[0] = 0;
                powerLawHash.put(num, temp);
            }
            powerLawHash.get(num)[0]++;
        }
        return powerLawHash;
    }

    public static void printPowerLaw_IntList(HashMap<Integer, Integer[]> powerLawHash) {
        Iterator<Map.Entry<Integer, Integer[]>> powerLawHash_iterator = powerLawHash.entrySet().iterator();
        Integer num;
        while (powerLawHash_iterator.hasNext()) {
            Map.Entry<Integer, Integer[]> powerLawHash_next = powerLawHash_iterator.next();
            num = powerLawHash_next.getKey();
//            if(num.toString().equals("1")){
            System.out.println(powerLawHash_next.getKey() + "\t" + powerLawHash_next.getValue()[0]);
//            }

        }
    }
    public static HashMap<Float, Integer[]> caculatePoweLawFloat(ArrayList<Float> list, float percision) {
        HashMap<Float, Integer[]> powerLawHash = new HashMap<Float, Integer[]>();
        float num;
        float result;
        int tempNum;
        for (int i = 0; i < list.size(); i++) {
            num = (float) list.get(i);
            tempNum = (int) (num / percision);
            result = (float) (tempNum * percision);
            if (powerLawHash == null) {
                powerLawHash = new HashMap<Float, Integer[]>();
            }
            if (!powerLawHash.containsKey(result)) {
                Integer[] temp = new Integer[1];
                temp[0] = 0;
                powerLawHash.put(result, temp);
            }
            powerLawHash.get(result)[0]++;

        }
        return powerLawHash;
    }

    public static void printPowerLawFLoat(HashMap<Float, Integer[]> powerLawHash) {
        Iterator<Map.Entry<Float, Integer[]>> powerLawHash_iterator = powerLawHash.entrySet().iterator();
        Float num;
        while (powerLawHash_iterator.hasNext()) {
            Map.Entry<Float, Integer[]> powerLawHash_next = powerLawHash_iterator.next();
            num = powerLawHash_next.getKey();
//            if(num.toString().equals("1")){
            System.out.println(powerLawHash_next.getKey() + "\t" + powerLawHash_next.getValue()[0]);
//            }

        }
    }
    public static int getNextMonth(int month) {
    	int next;
    	next=month+1;
    	if(month>12){
    		next=next-12;
    	}
		return month;
	}
    public static ArrayList<String> timeList(int interval,String endTime) {
		String preTime="";
		ArrayList<String> temp=new ArrayList<String>();
		for (int i = 0; i < interval; i++) {
			preTime=Tools.getPreOrNextDay(endTime, -i);
			temp.add(preTime);
		}
		Collections.sort(temp);
		return temp;
	}
    
//    
    public static String MD5(String s){
    	char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
         // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
         // 使用指定的字节更新摘要
            mdInst.update(btInput);
         // 获得密文
            byte[] md = mdInst.digest();
         // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public static void main(String[] args){
//    	String s = "http://weibo.cn/comment/CeBNWn0Er?uid=1204312575&rl=1&vt=4#cmtfrm";
//    	
//    	Tools tools = new Tools();
//    	String md5 = tools.MD5(s);
//    	System.out.println(s.length() + "\t" + md5.length());
//    }
    public static void main(String[] args){
    	System.out.println(Tools.getSystemTimeMondayOfWeek());
    }

}

//    public static ArrayList<Float> readExcel(String path,int sheetNum){
//        ArrayList<Float> result;
//        result=new ArrayList();
//        InputStream inp = null;
//        try {
//            inp = new FileInputStream(path);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Workbook wb = null;
//        try {
//            wb = WorkbookFactory.create(inp);
//        } catch (IOException | InvalidFormatException ex) {
//            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Sheet sheet;
//        sheet = wb.getSheetAt(sheetNum);
//        float p=0;
//        int lastRowNum = sheet.getLastRowNum();
//        for (int i = 0; i < lastRowNum; i++) {
//            Row row=sheet.getRow(i);
//            Cell cell=row.getCell(0);
//            String f = cell.toString();
//            p=Float.valueOf(f);
//            result.add(p);
//        }
//        return result;
//    }
//}
//main
