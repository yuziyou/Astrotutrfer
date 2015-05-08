package commonTools;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	final String regex_forumName_block_userid = null;
	final String regex_forumName_content_userid = null;

	// final String regex_(论坛名)_(类型block/content)_(属性) = (正则表达式);

	/**
	 * @param forumName
	 *            论坛名
	 * @param type
	 *            类型 block/content:标题/内容
	 * @param boardName
	 *            板块名
	 * @param fileData
	 *            文件内容
	 * @param propertyName
	 *            属性名
	 * @return property 属性的键值对
	 */
	public String[][] doValueMatch(String forumName, String type,
			String boardName, String fileData, String propertyName) {
		String[][] property = null;
		// String[k][] 存主键 String[][k] 属性值
		String regex = getRegexPattern(forumName, type, boardName, propertyName);
		return property;
	}

	/**
	 * @param forumName
	 *            论坛名
	 * @param type
	 *            类型 block/content:标题/内容
	 * @param boardName
	 *            板块名
	 * @param propertyName
	 *            属性名
	 * @return regexPattern 正则模板
	 */
	public String getRegexPattern(String forumName, String type,
			String boardName, String propertyName) {
		String regexPattern = null;
		return regexPattern;
	}

	public static boolean matchPages(String str) {
		String regex = "共\\d+页";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		boolean find = matcher.find();
		return find;
	}

	public static boolean matchString(String str) {
		String regex = "^[\u4e00-\u9fa5a-zA-Z0-9\\W]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		boolean find = matcher.find();
		return find;
	}

	public static String getNetUrl(String nextUrl) {
		// ////////////////////////////////////
		String subUrl = nextUrl.split(".shtml")[0];
		String regex = "http://bbs.tianya.cn/[a-zA-Z0-9]+-[a-zA-Z0-9]+-[a-zA-Z0-9]+-";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(subUrl);
		matcher.find();
		String baseUrl = matcher.group(0);
		// string+String.valueOf(i)
		// ////////////////////////////////////////
		String regex2 = "[a-zA-Z0-9]+.shtml";
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher2 = pattern2.matcher(nextUrl);
		matcher2.find();
		String num = matcher2.group(0);
		// String temp=num.split(".")[1];
		// System.out.println(num);
		String[] split = num.split(".shtml");
		// System.out.println(split.length);
		// System.out.println(split[0]);
		int nextNUm = Integer.parseInt(split[0]) + 1;
		// /////////
		String url = baseUrl + nextNUm + ".shtml";
		return url;
	}

	public static void main(String[] args) {
		// String nextUrl =
		// "http://bbs.tianya.cn/post-numtechnoloy-187514-14.shtml";
		// String netUrl = getNetUrl(nextUrl);
		// System.out.println(netUrl);
		// String formatTime = formatTime_Taobao("发表24分钟前");
		// System.out.println(formatTime);
		boolean matchString = matchString("时间过得真快，转眼间结婚5年了，结婚时感觉自己还很小呢，"
				+ "大学时和老公恋爱，恋情持续三年，感情一直也很稳定，毕业后就直奔主题啦。因为结婚早"
				+ "，我现在仍然觉得自己还年轻，就想趁着年轻做点事情，说白了就是不想一个人在家过得太单调。"
				+ " 现在家里的一切都由我打理，老公的工资也是我管。结婚这么久，之前也一直没有工作，"
				+ "无非就是伺候伺候老公，照顾可爱的宝宝，做一些家务活，还有爱逛街吃各色小吃，"
				+ "谁让我从大学开始就是个典型的吃货呢，我们的生活很幸福自然是没的说。现在老公工作稳定"
				+ "，带宝宝也比以前轻松很多，但要考虑到宝宝以后的成长因素也很多，心理压力也是有的，"
				+ "只有基石打的牢，对小孩的成长才会更好。结婚时双方父母给置办了新房，"
				+ "我们基本没什么压力,还有一定的存款。现在有一套房，两部车。婚后老公也一直努力工作，"
				+ "生活也很幸福，家里有一部分存款。我是一名全职家庭主妇，"
				+ "自从结婚怀孕后应公公婆婆和老公的要求全职在家，现在小孩慢慢长大，"
				+ "时间说长不长说短也不短咯。有的时候在家太无聊，除了看看泡沫剧，打发时间，"
				+ "偶尔八卦出去喝朋友们喝个茶逛逛街什么的，之前宝宝小的时候不好带，觉得她是个幸福的负担，"
				+ "以前宝宝和老公都不在家的时候，也让我觉得生活是百无聊赖。");
		System.out.println(matchString);
	}

	public static String getNetUrl(String nextUrl, int i) {
		// ////////////////////////////////////
		String subUrl = nextUrl.split(".shtml")[0];
		String regex = "http://bbs.tianya.cn/[a-zA-Z0-9]+-[a-zA-Z0-9]+-[a-zA-Z0-9]+-";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(subUrl);
		matcher.find();
		String baseUrl = matcher.group(0);
		// string+String.valueOf(i)
		// ////////////////////////////////////////
		// String regex2 = "[a-zA-Z0-9]+.shtml";
		// Pattern pattern2 = Pattern.compile(regex2);
		// Matcher matcher2 = pattern2.matcher(nextUrl);
		// matcher2.find();
		// String num = matcher2.group(0);
		// // String temp=num.split(".")[1];
		// // System.out.println(num);
		// String[] split = num.split(".shtml");
		// System.out.println(split.length);
		// System.out.println(split[0]);
		// int nextNUm = Integer.parseInt(split[0]) + 1;
		// /////////
		String url = baseUrl + i + ".shtml";
		return url;
	}

	public static String getNum(String str) {
		String rs = "";
		String regex = "\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		rs = matcher.group(0);
		return rs;
	}

	public static int getNum_int(String str) {
		int num = 0;
		String rs = "";
		String regex = "(\\d+)|(-\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		rs = matcher.group(0);
		num = Integer.parseInt(rs);
		return num;
	}

	public static int getNum_int2(String str) {
		int num = 0;
		String rs = "";
		String regex = "\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		rs = matcher.group(0);
		num = Integer.parseInt(rs);
		return num;
	}

	public static String getNum_s(String str) {
		// int num=0;
		String rs = "";
		String regex = "-\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		rs = matcher.group(0);
		// num=Integer.parseInt(rs);
		return rs.substring(1);
	}

	public static String getNum_s2(String str) {
		// int num=0;
		String rs = "";
		String regex = "/\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		rs = matcher.group(0);
		// num=Integer.parseInt(rs);
		return rs.substring(1);
	}

	public static String getTime(String str) {
		String time = "";
		String regex = "\\d{4}-(\\d{2}|\\d{1})-(\\d{2}|\\d{1}) (\\d{2}|\\d{1}):(\\d{2}|\\d{1})";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		time = matcher.group(0);
		return time;
	}

	public static String getTime4(String str) {
		String time = "";
		String regex = "\\d{4}-(\\d{2}|\\d{1})-(\\d{2}|\\d{1}) (\\d{2}|\\d{1}):(\\d{2}|\\d{1}):(\\d{2}|\\d{1})";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		time = matcher.group(0);
		return time;
	}

	public static String getTime3(String str) {
		String time = "";
		String regex = "\\d{4}年\\d{2}月\\d{2}日 \\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		boolean find = matcher.find();
		if (find) {
			time = str.replace("年", "-").replace("月", "-").replace("日", "")
					+ ":00";
		}
		return time;
	}

	public static String formatTime(String str) {
		String time = "";
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		boolean find = matcher.find();
		if (find) {
			time = matcher.group(0) + ":00";
		}
		return time;
	}

	public static String getTime2(String str) {
		String time = "";
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		time = matcher.group(0);
		return time;
	}

	public static String formatTime_Taobao(String publicTime) {
		String rs = "";
		String regex_v0 = "(\\d{2}|\\d{1})分钟前";
		String regex_v1 = "(\\d{2}|\\d{1})小时(\\d{2}|\\d{1})分钟前";
		Pattern pattern_v0 = Pattern.compile(regex_v0);
		Pattern pattern_v1 = Pattern.compile(regex_v1);
		Matcher matcher = pattern_v1.matcher(publicTime);
		boolean find = matcher.find();
		Date date = new Date();
		String temp_date = date.toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String current_Time = dateFormat.format(date).toString();
		if (find) {
			// System.out.println(publicTime+":"+current_Time);
			String[] split = publicTime.split("小时");
			int hour = getNum_int2(split[0]);
			int minute = getNum_int2(split[1]);
			// System.out.println(hour + ":" + minute);
			String preTime_min = getPreOrNextMinute(current_Time, -minute);
			// System.out.println(preTime_min);
			String orignal_time = getPreOrNextHour(preTime_min, -hour);
			// System.out.println(orignal_time);
			rs = orignal_time;
		} else {
			int minute = getNum_int2(publicTime);
			String preTime_min = getPreOrNextMinute(current_Time, -minute);
			rs = preTime_min;
		}
		// if(find){
		// rs=publicTime;
		// }else {
		// Date date = new Date();
		// // String temp_date = date.toString();
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String temp_date = dateFormat.format(date).toString();
		// rs=temp_date;
		// }
		return rs;
	}

	public static String getPreOrNextMinute(String time, Integer interval) {
		SimpleDateFormat sfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sfMinute.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.MINUTE, interval);
		return sfMinute.format(c.getTime());
	}

	public static String getPreOrNextHour(String time, Integer interval) {
		SimpleDateFormat sfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sfMinute.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.HOUR, interval);
		return sfMinute.format(c.getTime());
	}

	public static int parse_floorInfo_taobao(String floor_info) {
		int rs = 0;
		String regex = "\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(floor_info);
		boolean find = matcher.find();
		if (find) {
			rs = getNum_int2(floor_info);
		} else {
			if (floor_info.equals("楼主")) {
				rs = 0;
			}
			if (floor_info.equals("沙发")) {
				rs = 1;
			}
			if (floor_info.equals("板凳")) {
				rs = 2;
			}
			if (floor_info.equals("地板")) {
				rs = 3;
			}
		}
		return rs;
	}
}

