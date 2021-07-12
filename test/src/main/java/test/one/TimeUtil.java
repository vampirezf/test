/*
 * PublicUtil.java         2016年7月15日 <br/>
 *
 * Copyright (c) 1994-1999 AnHui LonSun, Inc. <br/>
 * All rights reserved.	<br/>
 *
 * This software is the confidential and proprietary information of AnHui	<br/>
 * LonSun, Inc. ("Confidential Information").  You shall not	<br/>
 * disclose such Confidential Information and shall use it only in	<br/>
 * accordance with the terms of the license agreement you entered into	<br/>
 * with Sun. <br/>
 */

package test.one;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtil {

    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获取下月初的第一天
     * @param date
     * @return
     */
    public static Date getNextMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);//月增加1天
        return calendar.getTime();
    }

    /**
     * 获取下周的第一天
     * @param date
     * @return
     */
    public static Date getNextWeekFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH,1);//周增加1天
        return calendar.getTime();
    }

    /**
     * 获取下年的第一天
     * @param date
     * @return
     */
    public static Date getNextYearFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,1);//年增加1天
        return calendar.getTime();
    }

    /**
     * 获取 x 个季度之前的日期
     *
     * @param x
     * @return
     */
    public static Date getBeforQuarterStartDate(Date nowDate, int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        int tempYear = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int currentQuarter = getCurrentQuarter(m);
        System.out.println(tempYear+"----"+currentQuarter);
        int tempQuarter = currentQuarter;

        for (int i = 0; i < x; i++) {
            if (i == 0) {
                tempQuarter = currentQuarter - 1;
            } else {
                tempQuarter = tempQuarter - 1;
            }
            //年度减一
            if (tempQuarter <= 0) {
                tempYear -= 1;
                tempQuarter = 4;
            }
        }
        String dateTime = tempYear + "" + getQuarterStartDate(tempQuarter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得当前季度
     *
     * @param m
     * @return
     */
    public static int getCurrentQuarter(int m) {
        if (m >= 1 && m <= 3) {
            return 1;
        } else if (m >= 4 && m <= 6) {
            return 2;
        } else if (m >= 7 && m <= 9) {
            return 3;
        } else if (m >= 10 && m <= 12) {
            return 4;
        }
        return -1;
    }

    /**
     * 获得季度的开始日期
     *
     * @param q
     * @return
     */
    public static String getQuarterStartDate(int q) {
        if (q == 1) {
            return "0101";
        } else if (q == 2) {
            return "0401";
        } else if (q == 3) {
            return "0701";
        } else if (q == 4) {
            return "1001";
        }
        return "";
    }

}