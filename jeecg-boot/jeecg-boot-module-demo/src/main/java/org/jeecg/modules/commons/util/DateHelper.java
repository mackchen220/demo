package org.jeecg.modules.commons.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Log4j2
public class DateHelper {
	public static String DATETIME_FORMAT        = "yyyy-MM-dd HH:mm:ss";
	public static String DATETIME_FORMAT2        = "yyyy-MM-dd HH:mm:ss.SSS";
	public static String DATEMIN_FORMAT        = "yyyy-MM-dd HH:mm";
	public static String DAY_FORMAT        = "yyyy-MM-dd";
	public static String DAY_FORMAT_NOT        = "yyyyMMdd";
	public static String DATETIME_FORMAT_ONE = "yyyyMMddHHmmss";
	public static String DEFUALT_TIME_START  =" 00:00:00";
	public static String DEFUALT_TIME_END  =" 23:59:59";
	public static String DATETIME_FORMAT_ZH        = "yyyy年MM月dd日HH时mm分ss秒";
	public static String DAY_FORMAT_MONTH        = "yyyy-MM";
	public static String DAY_FORMAT_ZH        = "yyyy年MM月dd日";
	public static String DAY_FORMAT_DAY        = "MM月dd日";

	public static String DATETIME_FORMAT_HMS    = "HH:mm:ss";

	public static String MYSQL_DATETIME_FORMAT       = "%Y-%m-%d %H:%i:%s";                     //mysql数据库时间格式化
    public static String MYSQL_DATE_FORMAT           = "%Y-%m-%d";
    public static String MYSQL_DATETIME_FORMAT_MI    = "%Y-%m-%d %H:%i";
    public static String MYSQL_DATETIME_FORMAT_MM    = "%Y-%m";

	/**
	 * 取得时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date,String format){
		DateFormat toDateFormat = new SimpleDateFormat(format);
		String d =  toDateFormat.format(date);
		return d;
	}

	/**
	 * 取得时间
	 * @return
	 */
	public static String getToday(){
		return getDate(new Date(), DAY_FORMAT);
	}

	/**
	 * 取得时间
	 * @return
	 */
	public static String getTodayTime(){
		return getDate(new Date(), DATETIME_FORMAT);
	}

	/**
	 * 取得时间
	 * @return
	 */
	public static String getTodayTime2(){
		return getDate(new Date(), DATETIME_FORMAT2);
	}

	/**
	 * 取得时间
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getDate(String dateStr,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(dateStr);
			return date;
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算指定两日期相隔的天数,精确到天
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return bea 相隔天数
	 */
	public static int dateCompareGetDay(String startDate, String endDate) {
		int bea = 0;
		SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
		Date dCur;
		Date dRol;
		try {
			dCur = sdf_d.parse(startDate);
			dRol = sdf_d.parse(endDate);
			bea = (int) ((dCur.getTime() - dRol.getTime()) / (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
		}
		return bea;
	}
	   /**
     * 计算指定两日期相隔的天数,精确到分钟
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return bea 相隔天数
     */
    public static int dateCompareGetMin(String startDate, String endDate) {
        int bea = 0;
        SimpleDateFormat sdf_d = new SimpleDateFormat(DATETIME_FORMAT);
        Date dCur;
        Date dRol;
        try {
            dCur = sdf_d.parse(startDate);
            dRol = sdf_d.parse(endDate);
            bea = (int) ((dCur.getTime() - dRol.getTime()) / (60 * 1000));
        } catch (ParseException e) {
        }
        return bea;
    }

	/**
	 * 计算与当前时间相隔的秒数  当前时间减去参数时间
	 * @param reqNum
	 * @return 相隔的秒数
	 */
	public static long checkReqNum(String reqNum) throws JeecgBootException {
		SimpleDateFormat sdf_d = new SimpleDateFormat(DATETIME_FORMAT);
		Date dCur = null;
		Date dRol;
		try {
            dCur = sdf_d.parse(reqNum);
        } catch (ParseException e) {
            throw new JeecgBootException("参数异常");
        }
		dRol = new Date();
		long time = (dRol.getTime() - dCur.getTime())/1000;
		return time;
	}


	/**
	 * @description 参数时间减去当前时间
	 * @param: reqNum
	 * @return long
	 * @date 2020/1/15/015
	 */
	public static long calculateTime(String reqNum) throws  JeecgBootException{
		SimpleDateFormat sdf_d = new SimpleDateFormat(DATETIME_FORMAT);
		Date dCur = null;
		Date dRol;
		try {
			dCur = sdf_d.parse(reqNum);
		} catch (ParseException e) {
			throw new JeecgBootException("参数异常");
		}
		dRol = new Date();
		long time = (dCur.getTime() - dRol.getTime())/1000;
		return time;
	}


	/**
	 * 根据生日记算年龄
	 * @param dateStr  生日
	 * @return
	 */
	public static int getAge(String dateStr) {
		if (dateStr == null || dateStr.trim().equals("")) {
			throw new IllegalArgumentException("dateStr 参数异常");
		}
		DateFormat format1 = new SimpleDateFormat(DAY_FORMAT);
		Date date1 = null;
		try {
			date1 = format1.parse(dateStr);
			Date sysdate = new Date();
			Long time = sysdate.getTime() - date1.getTime();
			Double d1 = time / new Double(1000 * 60 * 60 * 24) + 1;
			String i = new DecimalFormat("#0.00").format((d1 / 365f));
			String age[] = i.split("\\.");
			return Integer.parseInt(age[0].replace("", "").length() < 1 ? "0": age[0]);
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 将日期进行加减运算
	 * @param startdate
	 * @param days 天数
	 * @return
	 * @throws ParseException
	 */
    public static String plusDate(String startdate,int days,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(startdate);
			long ltime = date.getTime();
			long ldays = days * (1000L * 60L * 60L * 24L);
			Date newDay =  new Date(ltime + ldays);
			return getDate(newDay, format);
		} catch (ParseException e) {
			return null;
		}
    }

    /**
	 * 将日期进行加减运算
	 * @param startdate
	 * @param days 天数
	 * @return
	 * @throws ParseException
	 */
    public static String plusDate(String startdate,int days){
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		try {
			Date date = sdf.parse(startdate);
			long ltime = date.getTime();
			long ldays = days * (1000L * 60L * 60L * 24L);
			Date newDay =  new Date(ltime + ldays);
			return getDate(newDay, DAY_FORMAT);
		} catch (ParseException e) {
			return null;
		}
    }

    /**
	 * 时间加减
	 * @param startDate
	 * @param type  1年，２月，３日，４时，５分，６秒
	 * @param num
	 * @param format
	 * @return
	 */
	public static String plusDate(String startDate, int type, int num,
			String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		Date s = getDate(startDate, format);
//		log.info(s);
		c.setTime(s);
		if (type == 1) {
			c.add(Calendar.YEAR, num);
		} else if (type == 2) {
			c.add(Calendar.MONTH, num);
		} else if (type == 3) {
			c.add(Calendar.DAY_OF_YEAR, num);
		} else if (type == 4) {
			c.add(Calendar.HOUR_OF_DAY, num);
		} else if (type == 5) {
			c.add(Calendar.MINUTE, num);
		} else if (type == 6) {
			c.add(Calendar.SECOND, num);
		}
		return f.format(c.getTime());
	}


    /**
	 * 将日期进行加减运算
	 * @param startdate
	 * @param times  格式时间截
	 * @return
	 * @throws ParseException
	 */
    public static String addDatetime(Date startdate,long times) throws ParseException{
		long ltime = startdate.getTime();
		Date newDay =  new Date(ltime + times);
		return getDate(newDay, DAY_FORMAT);
    }

    /**
     * 获取res随机码
     * @param size
     * @return
     */
    public static String createResNum(int size) {
		int num = 2;
		if(size > 14){
			num = size - 14;
		}
		String str = DateHelper.getDate(new Date(), DateHelper.DATETIME_FORMAT_ONE).concat(nextNumberString(num)) ;
		return str ;
	}

    /**
	 * 不同时间格式化
	 *
	 * @param date
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 */
	public static String changeTimeFormat(String date, String fromFormat,
			String toFormat) {
		DateFormat format1 = new SimpleDateFormat(fromFormat);
		Date date1 = null;
		try {
			date1 = format1.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("date 参数异常");
		}
		DateFormat toDateFormat = new SimpleDateFormat(toFormat);
		try {
			return toDateFormat.format(date1);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("toFormat 参数异常");
		}
	}

	/**
	 * 获取随机时间
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getRadomDateTime(int begin,int end){
		int num = nextNumber(begin, end);
		Date sysdate = new Date();
		long aaa = num * (1000L * 60L);
		Long time = sysdate.getTime() - aaa;
		Date newDay =  new Date(time);
		return getDate(newDay, DATETIME_FORMAT);
	}

	public static String getMonthFirst(String day) {
		SimpleDateFormat f = new SimpleDateFormat(DAY_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(day, DAY_FORMAT_MONTH));
		cal.set(Calendar.DAY_OF_MONTH,   1);
		return f.format(cal.getTime());
	}


	public static String getMonthLast(String day) {
		SimpleDateFormat f = new SimpleDateFormat(DAY_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(day, DAY_FORMAT_MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return f.format(cal.getTime());
	}

	/**
	 * 计算指定两日期相隔的天数,精确到天
	 * @param date1 开始时间
	 * @param date2 结束时间
	 * @return bea 相隔天数
	 */
	public static boolean compareDate(String date1, String date2) {
		int bea = 0;
		SimpleDateFormat sdf_d = new SimpleDateFormat(DATETIME_FORMAT);
		Date dCur;
		Date dRol;
		try {
			dCur = sdf_d.parse(date1);
			dRol = sdf_d.parse(date2);
			if(dCur.getTime()> dRol.getTime()){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	/** * 获取指定日期是星期几
	  * 参数为null时表示获取当前日期是星期几
	  * @param dateStr
	  * @return
	*/
	public static String getWeek(String dateStr) {
		Date date = getDate(dateStr, DAY_FORMAT);
	    String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	    Calendar calendar = Calendar.getInstance();
	    if(date != null){
	         calendar.setTime(date);
	    }
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	    if (w < 0){
	        w = 0;
	    }
	    return weekOfDays[w];
	}
	public static String getWeek1(String dateStr) {
        Date date = getDate(dateStr, DAY_FORMAT);
//        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if(date != null){
             calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return String.valueOf(w++);
    }
	/**
	 * 获取当前月的第几天
	 * @Description: TODO
	 * @Title: getFirstDayOrLastDayForMonth
	 * @return  String
	 * @date 2017-2-12
	 */
	public static String getFirstDayOrLastDayForMonth(int type,int day) {
	    SimpleDateFormat format = new SimpleDateFormat(DateHelper.DAY_FORMAT);
	    if(type == 1){
	      //获取当前月第一天：
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.MONTH, 0);
	        c.set(Calendar.DAY_OF_MONTH,day);//设置为1号,当前日期既为本月第一天
	        String first = format.format(c.getTime());
//	        log.info("===============first:"+first);
	        return first;
	    } else {
	      //获取当前月最后一天
	        Calendar ca = Calendar.getInstance();
	        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
	        String last = format.format(ca.getTime());
//	        log.info("===============last:"+last);
	        return last;
	    }
	}

	/**
	 * @description 计算当前时间与参数时间差值
	 * @param
	 * @return long
	 * @date 2019/9/7/031
	 */
	public static long calculatingTime(String cacheTime) {
		long intervalTime;
		if (StringUtils.isBlank(cacheTime)) {
			intervalTime = 0;
		} else {
			intervalTime = DateHelper.checkReqNum(cacheTime);
		}
		return intervalTime;
	}

	/**
	 * @param time1      时间1
	 * @param time2      时间2
	 * @param dateFormat 时间格式化类型
	 * @return
	 * @Title judgeTime    比较时间大小 ，如果第一个时间大于第二个时间返回true 否则 false
	 * @date 2018/6/5 16:47
	 */
	public static boolean judgeTime(String time1, String time2, String dateFormat) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			Date dt1 = df.parse(time1);//将字符串转换为date类型
			Date dt2 = df.parse(time2);
			if (dt1.getTime() > dt2.getTime())//比较时间大小,如果dt1大于dt2
			{
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean betweenTime(String startTime,String endTime,String nowTime,String dateFormat){
		if (nowTime == null || nowTime.equals("")){
			nowTime = DateHelper.getTodayTime();
		}
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			Date dt1 = df.parse(startTime);//将字符串转换为date类型
			Date dt2 = df.parse(endTime);
			Date nowDt = df.parse(nowTime);
			if (dt1.getTime() <= nowDt.getTime() && dt2.getTime() >= nowDt.getTime())//比较时间大小,如果dt1大于dt2
			{
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getWeekDate(String dateStr) {
		Date date = getDate(dateStr, DateHelper.DATETIME_FORMAT);
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){
			w = 0;
		}
		return weekDays[w];
	}
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
		return df.format(date);
	}
	public static String nextNumberString(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length 参数异常");
		} else {
			StringBuilder stringBuilder = new StringBuilder();

			for(int i = 0; i < length; ++i) {
				stringBuilder.append(new Random().nextInt(10));
			}

			return stringBuilder.toString();
		}
	}
	private static int nextNumber(int start, int end) {
		if (end < start) {
			throw new IllegalArgumentException("end 参数异常");
		} else {
			int i = new Random().nextInt(end - start + 1);
			return i + start;
		}
	}
	/**
	 * 获得该月第一天
	 *
	 */
	public static String getFirstDayOfMonth(int month) {
		Calendar cal = Calendar.getInstance();
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime())+" 00:00:00";
		return firstDayOfMonth;
	}
	/**
	 * 获得当前月第一天
	 *
	 */
	public static String getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();


		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime())+" 00:00:00";
		return firstDayOfMonth;
	}


	/**
	 * 获得该月最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int month) {
		Calendar cal = Calendar.getInstance();
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay=0;
		//2月的平年瑞年天数
		if(month==2) {
			lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
		}else {
			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime())+" 23:59:59";
		return lastDayOfMonth;
	}


	/**
	 * 获得当前月最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = 0;
		//2月的平年瑞年天数
		if (month == 2) {
			lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
		} else {
			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime()) + " 23:59:59";
		return lastDayOfMonth;
	}



	public static void main(String[] args) {
		System.out.println(DateHelper.getFirstDayOfMonth());
		System.out.println(DateHelper.getLastDayOfMonth());

		System.out.println();

	}

}
