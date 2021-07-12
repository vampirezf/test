package test.one;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;


public class Text {


    private static SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("MM");



    public static void main(String[] args) throws ParseException {


        /*int year1 = 2020, month1 = 2;
        Date beginTime = getBeginTime(year1, month1);
        System.out.println(sdf0.format(beginTime));

        Date endTime = getEndTime(year1, month1);
        System.out.println(sdf0.format(endTime));

        System.out.println("---------");
        String data = "2014-01";*/

       /* int year =Integer.parseInt(sdf1.format(new SimpleDateFormat("yyyy-MM").parse(data)));
        int mouth =Integer.parseInt(sdf1.format(new SimpleDateFormat("yyyy-MM").parse(data)));*/

       String data = "2014-04";

        /*int year = Integer.parseInt(sdf1.format(new SimpleDateFormat("yyyy-MM").parse(data)));

        int month = Integer.parseInt(sdf2.format(new SimpleDateFormat("yyyy-MM").parse(data)));
        System.out.println(year+"..."+month);*/

        Date beginTime = getBeginTime(data);
        System.out.println(beginTime);

        Date endTime = getEndTime(data);
        System.out.println(endTime);



    }
    public static Date test(int year,int month) {


        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        return Date.from(zonedDateTime.toInstant());
    }




    //传入yyyy-MM获取开始时间
    public static Date getBeginTime(String data) {

        int year = 0;
        try {
            year = Integer.parseInt(sdf1.format(new SimpleDateFormat("yyyy-MM").parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int month = 0;
        try {
            month = Integer.parseInt(sdf2.format(new SimpleDateFormat("yyyy-MM").parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        return Date.from(zonedDateTime.toInstant());
    }

    //传入yyyy-MM获取结束时间
    public static Date getEndTime(String data) {

        int year = 0;
        try {
            year = Integer.parseInt(sdf1.format(new SimpleDateFormat("yyyy-MM").parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int month = 0;
        try {
            month = Integer.parseInt(sdf2.format(new SimpleDateFormat("yyyy-MM").parse(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }


}
