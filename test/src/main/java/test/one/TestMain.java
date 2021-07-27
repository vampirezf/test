package test.one;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lambda.Account;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class TestMain {

    private String REX1= "(?<=\\$\\{)(\\w+)(?=\\})";

    private String REX2= "(?=(\\$\\{\\w+}))..(\\w+)(\\})";

    @Test
    public void test() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String s1 = "2020-03";
        Date beginTime1 = Text.getBeginTime(s1);
        Date endTime2 = Text.getEndTime(s1);
        System.out.println(beginTime1);
        System.out.println(endTime2);
        String beginTime = formatter.format(beginTime1);
        String endTime = formatter.format(endTime2);
        System.out.println("------------------");
        System.out.println(beginTime);
        System.out.println(endTime);
        System.out.println("------------------");
    }

    public static void main(String[] args) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String s1 = "2020-03";
        Date beginTime1 = Text.getBeginTime(s1);
        Date endTime2 = Text.getEndTime(s1);
        String beginTime = formatter.format(beginTime1);
        String endTime = formatter.format(endTime2);


        String URL = "jdbc:mysql://127.0.0.1:3306/zf1?serverTimezone=UTC";
        String USER = "root";
        String PASSWORD = "123456";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            //String sql="select * from student where time >= '"+beginTime+"' and time <= '"+endTime+"'";
            String sql = "select * from student where id = '5' ";
            //sql=sql+"where time >= +beginTime+'' and time <= endTime";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                //System.out.println(rs.getString("phone_number") + " " + rs.getString("time"));
                String names = rs.getString("names");
                boolean empty = names.isEmpty();
                System.out.println(empty);
            }

            // 关闭资源
            conn.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        String a = "";
        System.out.println(!StringUtils.isEmpty(a));

        System.out.println(getYears("2017", "2017"));

    }

    public void t3() {
        String y = "2017";
        String beginTime = y + "-01-01 00:00:00";
        String endTime = y + "-12-31 23:59:59";
    }


    public static List<Map> getYears(String startTime, String endTime) {
        List<Map> res = new ArrayList<Map>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.YEAR, 1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                String year = dateFormat.format(tempStart.getTime());
                String first = year + "-01-01 00:00:00";
                String last = year + "-12-31 23:59:59";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("year", year);
                map.put("first", formatter.format(first));
                map.put("last", formatter.format(last));
                res.add(map);
                tempStart.add(Calendar.YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }


    @Test
    public void test3() {

        /*String a="1,2,3,4,5";
        String[] split = a.split(",");
        List<String> list=new ArrayList();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            int li = Integer.parseInt(list.get(i));
            li+=10;
            list.set(i,String.valueOf(li));
        }

        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if(i<list.size()-1){
                sb.append(list.get(i)+",");
            }else {
                sb.append(list.get(i));
            }
        }
        System.out.println(list);
        System.out.println(sb);*/

        Te te1 = new Te("1,2,3,4");
        Te te2 = new Te("5,6,7,8");

        List<Te> listTe = new ArrayList<>();
        listTe.add(te1);
        listTe.add(te2);
        //System.out.println(listTe);
        for (int i = 0; i < listTe.size(); i++) {

            String[] split = listTe.get(i).getA().split(",");

            List<String> ssss = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                ssss.add(split[j]);

            }
            for (int ii = 0; ii < ssss.size(); i++) {
                int li = Integer.parseInt(ssss.get(ii));
                li += 10;
                ssss.set(ii, String.valueOf(li));
            }
            System.out.println(ssss);
        }
        //System.out.println(listTe);

    }

    @Test
    public void test4() {
        String str = "/home/html/60.171.247.208:50014/zx/yqyw/33742512.html";
        //获得第一个点的位置
        int index = str.indexOf("/");
        System.out.println(index);
        //根据第一个点的位置 获得第二个点的位置
        index = str.indexOf("/", index + 1);
        //根据第二个点的位置 获得第三个点的位置
        index = str.indexOf("/", index + 1);
        //根据第三个点的位置，截取 字符串。
        String result = str.substring(index + 1);
        //输出结果
        System.out.println(result);
    }

    @Test
    public void test5() {
        String a = null;
        System.out.println("aaa".equals(a));
    }

    @Test
    public void test6() {
        //   http://www.szyq.gov.cn//public/content/10826521
        //   http://www.szyq.gov.cn/public/content/14626251
        //   http://www.szyq.gov.cn/public/141/14728011.html
//        String filePath = "http://www.szyq.gov.cn/public/content/14626251";
//        int str = filePath.indexOf("/");
//        str=filePath.indexOf("/",str+1);
//        str=filePath.indexOf("/",str+1);
//        str=filePath.indexOf("/",str+1);
//        str=filePath.indexOf("/",str+1)+1;
//        //int end = filePath.indexOf("/");
//        //String substring = filePath.substring(str, end);
//        String substring = filePath.substring(str);
//        System.out.println(substring);

        String filePath = "http://www.szyq.gov.cn/public/141/14728011.html";
        int str = filePath.indexOf("/");
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1) + 1;
        int end = filePath.indexOf(".html");
        String substring = filePath.substring(str, end);
        System.out.println(substring);
//        int str = filePath.indexOf("/");
//        str = filePath.indexOf("/",str+1);
//        str = filePath.indexOf("/",str+1);
//        str = filePath.indexOf("/",str+1)+1;
//
//        int end = filePath.indexOf("/",str+1);
//        String substring = filePath.substring(str,end);
//        System.out.println(substring);
    }

    @Test
    public void test7() {


        StringBuilder newFilePath = new StringBuilder();
        //String filePath = "http://www.szyq.gov.cn//public/content/10826521";
        //String filePath = "http://www.szyq.gov.cn/public/content/14626251";
        String filePath = "http://www.szyq.gov.cn/public/141/14728011.html";
        //对link进行处理
        //   http://www.szyq.gov.cn//public/content/10826521
        if (filePath.contains("http://www.szyq.gov.cn//public/content/")) {
            String oldCid = null;
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            //int end = filePath.indexOf("/");
            //String substring = filePath.substring(str, end);
            oldCid = filePath.substring(str);
            //填加埇桥增量
            int cid = Integer.parseInt(oldCid) + 121699271;

            newFilePath.append("http://www.szyq.gov.cn//public/content/").append(cid);
        }
        //  http://www.szyq.gov.cn/public/content/14626251
        else if (filePath.contains("http://www.szyq.gov.cn/public/content/")) {
            String oldCid = null;
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            //int end = filePath.indexOf("/");
            //String substring = filePath.substring(str, end);
            oldCid = filePath.substring(str);
            //contentId填加埇桥增量
            int cid = Integer.parseInt(oldCid) + 121699271;

            newFilePath.append("http://www.szyq.gov.cn/public/content/").append(cid);
        }
        //   http://www.szyq.gov.cn/public/141/14728011.html
        else if (filePath.contains("http://www.szyq.gov.cn/public/") && filePath.contains(".html")) {
            String oldOid = null;
            String oldCid = null;
            //提取oid
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            int end = filePath.indexOf("/", str + 1);
            oldOid = filePath.substring(str, end);
            // oid 141 添加埇桥增量
            int oid = Integer.parseInt(oldOid) + 6623390;

            //提取contentid
            int str2 = filePath.indexOf("/");
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1) + 1;
            int end2 = filePath.indexOf(".html");
            oldCid = filePath.substring(str2, end2);
            int cid = Integer.parseInt(oldCid) + 121699271;

            newFilePath.append("http://www.szyq.gov.cn/public/").append(oid).append("/").append(cid).append(".html");
        }

        System.out.println(newFilePath);
    }


    @Test
    public void test8() {


        StringBuilder newFilePath = new StringBuilder();
        //String filePath = "http://www.sixian.gov.cn/3/4752251.html";
        //String filePath = "http://www.sixian.gov.cn/public/content/4623711";
        String filePath = "http://www.sixian.gov.cn/3/4752251.html";
        //对link进行处理
        String oldOid = null;
        String oldCid = null;
        //提取oid
        int str = filePath.indexOf("/");
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1) + 1;
        int end = filePath.indexOf("/", str + 1);
        oldOid = filePath.substring(str, end);
        // oid 141 添加泗县增量
        int oid = Integer.parseInt(oldOid) + 23681;

        //提取contentid
        int str2 = filePath.indexOf("/");
        str2 = filePath.indexOf("/", str2 + 1);
        str2 = filePath.indexOf("/", str2 + 1);
        str2 = filePath.indexOf("/", str2 + 1) + 1;
        int end2 = filePath.indexOf(".html");
        oldCid = filePath.substring(str2, end2);
        int cid = Integer.parseInt(oldCid) + 115478151;

        newFilePath.append("http://www.sixian.gov.cn/").append(oid).append("/").append(cid).append(".html");


        System.out.println(newFilePath);
    }


    @Test
    public void test9() {
        StringBuilder newFilePath = new StringBuilder();

        String filePath = "http://www.sixian.gov.cn/public/content/4623711";

        //String filePath = "http://www.szyq.gov.cn//public/content/10826521";
        String oldCid = null;
        int str = filePath.indexOf("/");
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1);
        str = filePath.indexOf("/", str + 1) + 1;
        //int end = filePath.indexOf("/");
        //String substring = filePath.substring(str, end);
        oldCid = filePath.substring(str);
        System.out.println(oldCid);
    }

    @Test
    public void test10() {

        StringBuilder newFilePath = new StringBuilder();
        //String filePath = "http://www.sixian.gov.cn/3/4752251.html";
        //String filePath = "http://www.sixian.gov.cn/public/content/4623711";
        String filePath = "http://www.sixian.gov.cn/3/4752331.html,http://www.sixian.gov.cn/1391/5758501.html";

        //对link进行处理
        //   http://www.sixian.gov.cn/public/content/4623711
        if (filePath.contains("http://www.sixian.gov.cn/public/content/")) {
            String oldCid = null;
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            //int end = filePath.indexOf("/");
            //String substring = filePath.substring(str, end);
            oldCid = filePath.substring(str);
            //填加泗县增量
            int cid = Integer.parseInt(oldCid) + 115478151;

            newFilePath.append("http://www.sixian.gov.cn/public/content/").append(cid);
        }
        //   http://www.sixian.gov.cn/3/4752251.html
        else if (filePath.contains("http://www.sixian.gov.cn/") && filePath.contains(".html")) {
            String oldOid = null;
            String oldCid = null;
            //提取oid
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            int end = filePath.indexOf("/", str + 1);
            oldOid = filePath.substring(str, end);
            // oid 141 添加泗县增量
            int oid = Integer.parseInt(oldOid) + 23681;

            //提取contentid
            int str2 = filePath.indexOf("/");
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1) + 1;
            int end2 = filePath.indexOf(".html");
            oldCid = filePath.substring(str2, end2);
            int cid = Integer.parseInt(oldCid) + 115478151;

            newFilePath.append("http://www.sixian.gov.cn/").append(oid).append("/").append(cid).append(".html");
        }

        System.out.println(newFilePath);
    }

    @Test
    public void test11() {
        String a = "1type_gzd1";
        String b = "type_gzd";
        int i = a.indexOf(b);
        System.out.println(i);
    }


    @Test
    public void test12() throws Exception {
        MongoDatabase mongoDatabase = MongoUtilTest.getConnect();
        MongoCollection<Document> contentMongoEO = mongoDatabase.getCollection("contentMongoEO");
        System.out.println(contentMongoEO);
    }

    @Test
    public void test13() throws Exception {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId("5326bfc0e6f780b21635248f"));
        MongoDatabase mongoDatabase = MongoUtilTest.getConnect();
        MongoCollection collection = mongoDatabase.getCollection("contentMongoEO");
        MongoCursor cursor = collection.find(query).iterator();

        while (cursor.hasNext()) {
            Object next = cursor.next();
            System.out.println(next);
        }
    }

    @Test
    public void test14() throws Exception {
        int i = 0;
        Bson condition = Filters.in("_id", "11710497");
        MongoDatabase mongoDatabase = MongoUtilTest.getConnect();
        MongoCollection collection = mongoDatabase.getCollection("contentMongoEO");
        Document fetchFields = new Document();
        fetchFields.put("_id", 1);//查询的结果 不返回 _id 字段
        fetchFields.put("content", 1);//返回 uid 字段
        MongoCursor<Document> cursor = null;
        try {
            cursor = collection.find(condition).projection(fetchFields).iterator();
            long count = collection.count(condition);

            i = 0;
            if (null != cursor && count > 0) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    Object content = document.get("content");
                    System.out.println(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
    }

    @Test
    public void test15() {
        String picUrl = "/group1/M00/01/54/Cpc8Vl7PY4qAUVzhAAIkUEXVNek284.png";

        String headStr = "http://www.ahsz.gov.cn";
        if (!picUrl.isEmpty()) {
            //设置视频地址
            if (picUrl.indexOf("/") == 0) {//加上站点前缀
                picUrl = headStr.concat(picUrl);
            } else if (picUrl.indexOf("http://") == -1) {
                picUrl = headStr.concat("/mongo/").concat(picUrl);
            }
            System.out.println(picUrl);
        }
    }

    @Test
    public void test16() {
        Long sortNum = 1577754322000L;

        Date date = new Date(sortNum);
        System.out.println(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(date);
        System.out.println(format);

    }


    @Test
    public void test17() {
        String a = "showEr";
        try {
            if (!a.isEmpty()) {
                System.out.println("1111");
                return;
            }

            System.out.println("2222");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test18() {
        String a = "2020-01-01日至2020-05-31日";
        String[] b = a.split("日至");

        System.out.println(b[0] + "====" + b[1]);


    }

    @Test
    public void test19() {
        String a = "发布临时救助办理流程，救助标准；每月或季度发布困难群众临时救助花名册等。";
        if (!a.isEmpty() && a.length() > 20) {
            a = a.substring(0, 20) + "...";
        }
        System.out.println(a);
    }

    @Test
    public void test20() {
        String a = "https://www.ahghw.org";
        String s = a.split("//")[0];
        System.out.println(s);
    }

    @Test
    public void test21() throws ParseException {
        Date nowDate = new Date();

        String changeTime = "2020-07-06 10:35:00";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date changeDate = dateFormat.parse(changeTime);
        System.out.println(changeDate.getTime() - nowDate.getTime());

    }

    @Test
    public void test22() throws ParseException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayTime = dateFormat.format(calendar.getTime());
        Date yesterdayDate = dateFormat.parse(yesterdayTime);
        System.out.println(yesterdayDate);
    }

    @Test
    public void test23() {

        String a = "1,2";
        String[] split = a.split(",");
        System.out.println(split.length);

    }

    @Test
    public void test24() {
        //获取前一天的时间  2020-07-07
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, -1);
        Date yesterdayDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayTime = dateFormat.format(calendar.getTime());
        try {
            yesterdayDate = dateFormat.parse(yesterdayTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String format = formatter.format(yesterdayDate);

        System.out.println(format);
    }

    @Test
    public void Test25() {


        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        System.out.println("今天是" + weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

    }

    @Test
    public void Test26() {
        Calendar cal = Calendar.getInstance();
        //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = 1;
        String monday;
        cal.add(Calendar.DATE, n * 3);
        //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        System.out.println(monday);
    }

    @Test
    public void test26() {
        Date yesterdayDate = DateUtil.getYesterdayDate();
        System.out.println(yesterdayDate);

        String yesterdayTime = DateUtil.getYesterdayTime();
        System.out.println(yesterdayTime);

        String week = DateUtil.getWeek(new Date());
        System.out.println(week);

        String lastWeek5 = DateUtil.getLastWeek5();
        System.out.println(lastWeek5);

        String nowWeek5 = DateUtil.getNowWeek5();
        System.out.println(nowWeek5);

    }

    @Test
    public void test27() {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week - 3);
        System.out.println(c.getTime());
        System.out.println(format.format(c.getTime()));
    }

    @Test
    public void test28() throws ParseException {
        DateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, 6);
        String format1 = format.format(calendar.getTime());
        System.out.println(format1);

        Date date = dateFormat1.parse(format1);
        System.out.println(date);
    }

    @Test
    public void test29() {
        String nowWeek5 = DateUtil.getNowWeek5();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dateFormat.parse(nowWeek5);
            System.out.println(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(nowWeek5);
    }

    @Test
    public void test30() {
        Boolean showParUrl = null;
        if (showParUrl != null) showParUrl = false;

        System.out.println(showParUrl);
    }

    @Test
    public void test31() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String yesterday = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        yesterday = format.format(cal.getTime());

        System.out.println(yesterday);
    }

    @Test
    public void test32() {
        String sql = "select b.cerid As cerid,b.bdcqzh As bdcqzh,b.qlr As qlr,b.qllx As qllx,b.bdcdyh As bdcdyh,b.fdzl As fdzl,b.bz As bz,b.ggdy As ggdy,b.zxsj As zxsj,b.sjscsj As sjscsj  from bdc_zfgg b  and b.bdcqzh like '%皖（2019）%'";
        //ql = "select count(*) " + sql.substring(sql.indexOf("from"));
        sql = "select count(*) " + sql.substring(sql.indexOf("from"));
        System.out.println(sql);
    }


    /*public List<AbandonNoticeVO> test(){
        Long pageIndex =0L;
        int pageSize = 20;
        String bdcqzh ="皖（2019）";
        String sql = "select b.cerid As cerid,b.bdcqzh As bdcqzh,b.sjscsj As sjscsj  from bdc_zfgg b ";
        String newbdcqzh = "%"+bdcqzh+"%";
        sql = sql + " where b.bdcqzh like '" + newbdcqzh + "'";
        sql = sql + " order by b.sjscsj DESC ";

        Long str = (pageIndex + 1) * pageSize;
        Long end = pageIndex * pageSize;

        String nnsql = "select * from (select t.*,rownum rn from (" + sql + ") t where rownum<= "+ str+" ) where rn >"+end+"";
        List list = getJdbc().excuteQuery(nnsql,null);
        List voList = new ArrayList();
        return null;
    }*/

    @Test
    public void test33() {
        ClassLoader classLoader = TestMain.class.getClassLoader();
        URL resource = classLoader.getResource("sendsms.properties");
        String path = resource.getPath();
        System.out.println(path);
    }

    @Test
    public void test34() {
        String a = "mayor200924JB0010030002user";
        String user = a.substring(5, a.indexOf("user"));
        System.out.println(user);
    }

    @Test
    public void test35() {
        int me = 2;
        int a = me == 1 ? 0 : 1;
        System.out.println(a);

    }

    @Test
    public void test36() {
        //市 1级 -0，县区 3级-1，部门 2级-3
        int me = 2;
        //int a = me.params.level == 1 ? 0 : me.params.level == 3 ? 1 : me.params.level == 2 ? 3:0;
        //System.out.println(a);
        Long organId = 1L;
        boolean isPrivate = null != organId && organId > 0L;
        System.out.println(isPrivate);

    }

//    @Test
//    public void test37(){
//        String url = "http://60.175.228.150:8089/cns-szbmfw-rest/rest/sxCaseInfo/addCaseInfo";
//        HttpPost httpPost = new HttpPost(url);
//
//        CloseableHttpClient client = HttpClients.createDefault();
//        String respContent = null;
//        JSONObject jsonParam = new JSONObject();
//
//        JSONObject credentialsJsonParam = new JSONObject();
//
//        credentialsJsonParam.put("userGuid", "1");
//        credentialsJsonParam.put("sex","");
//        credentialsJsonParam.put("rqstType", "10");
//        credentialsJsonParam.put("rqstTitle", "测试数据7");
//        credentialsJsonParam.put("rqstSource", "SX");
//        credentialsJsonParam.put("rqstPerson", "左凡");
//        credentialsJsonParam.put("rqstContent", "测试数据7");
//        credentialsJsonParam.put("rqstAreaCode", "");
//        credentialsJsonParam.put("rqstAddress", "");
//        credentialsJsonParam.put("password", "3211");
//        credentialsJsonParam.put("number", "7");
//        credentialsJsonParam.put("linkNumber", "18196548685");
//        credentialsJsonParam.put("isSecret", "0");
//        credentialsJsonParam.put("agestage", "");
//
//        jsonParam.put("params", credentialsJsonParam);
//        jsonParam.put("token", "SZ12345");
//
//        logger.info("=============:\t" + jsonParam.toString());
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        ContentType contentType = ContentType.create("multipart/form-data", Consts.UTF_8);
//        builder.addTextBody("params", jsonParam.toString(), contentType);
//
//        HttpEntity multipart = builder.build();
//
//        HttpResponse resp = null;
//        try {
//            httpPost.setEntity(multipart);
//            resp = client.execute(httpPost);
//
//            //注意，返回的结果的状态码是302，非200
//            HttpEntity he = resp.getEntity();
//            respContent = EntityUtils.toString(he, "UTF-8");
////            if (resp.getStatusLine().getStatusCode() == 302) {
////                HttpEntity he = resp.getEntity();
////                logger.info("----------------123----------666666---");
////                respContent = EntityUtils.toString(he, "UTF-8");
////            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        logger.info("=========================:\t" + respContent);
//        logger.info("=========================:\t" + resp.getStatusLine().getStatusCode());
//
//    }

    @Test
    public void test38() {

        String type = "dr";
        String action = "num";
        if (!("dr".equals(type) && ("list".equals(action) || "num".equals(action)))) {
            System.out.println("不为空");
        }
        if ((!"dr".equals(type)) || ((!"list".equals(action)) && (!"num".equals(action)))) {
            System.out.println("不为空");
        }

    }


    @Test
    public void test39() {
        String filePath = "http://gk.ahsz.gov.cn/public/content/1179081";
        StringBuilder newFilePath = new StringBuilder();
        if (filePath.contains("http://gk.ahsz.gov.cn/public/content/")) {
            String oldBaseContentid = null;
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            oldBaseContentid = filePath.substring(str);

            int newBaseId = Integer.parseInt(oldBaseContentid);

            newFilePath.append("http://gk.ahsz.gov.cn/public/content/").append(newBaseId);
        }
    }

    @Test
    public void test40() {
        String filePath = "http://gk.ahsz.gov.cn/public/2655575/1180431.html";
        if (filePath.contains("http://gk.ahsz.gov.cn/public/") && filePath.contains(".html")) {
            String oldOrganId = null;
            String oldBaseId = null;
            StringBuilder newFilePath = new StringBuilder();
            //提取oid
            int str = filePath.indexOf("/");
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1);
            str = filePath.indexOf("/", str + 1) + 1;
            int end = filePath.indexOf("/", str + 1);
            oldOrganId = filePath.substring(str, end);
            System.out.println(oldOrganId);

            //提取contentid
            int str2 = filePath.indexOf("/");
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1);
            str2 = filePath.indexOf("/", str2 + 1) + 1;
            int end2 = filePath.indexOf(".html");
            oldBaseId = filePath.substring(str2, end2);

            newFilePath.append("http://gk.ahsz.gov.cn/public/").append(oldOrganId).append("/").append(oldBaseId).append(".html");

        }
    }

    @Test
    public void test41() {
        //定义字符串
        String str = "1";
        //截取字符串
        String[] strArr = str.split(",");
        Long[] num = new Long[strArr.length];
        for (int idx = 0; idx < strArr.length; idx++) {
            num[idx] = Long.parseLong(strArr[idx]);
        }

        for (Long aLong : num) {
            System.out.println(aLong);
        }

    }

    @Test
    public void test42() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Integer i = 0;
        for (Object o : list) {
            i += 1;
            System.out.println(i);
        }
    }

    @Test
    public void test43() {
        String title = "宿州市人民政府2019年政府信息公开工作年度报告（图片版）";

        if (title.indexOf("解") == -1) {
            System.out.println(1);
        }
    }

    @Test
    public void test44() {
        Long a = null;
        Long b = a + 1L;
        System.out.println(b);
    }

    @Test
    public void test45() {
        String a = "5f2a193de4b0cda058dc460a.pdf";
        int str = a.indexOf(".pdf");
        a = a.substring(0, str);
        System.out.println(a);
    }

    @Test
    public void test46() {
        List<String> list = new ArrayList();
        list.add("屯溪区人民政府办公室2019年度政府信息公开年报（图解）");
        list.add("屯溪区人民政府办公室2019年度政府信息公开年报");
        list.add("屯溪区人民政府2019年度政府信息公开年报");
        String specialTitle = "";
        for (String o : list) {
            if (!specialTitle.isEmpty()) {
                if (o.indexOf("政府办公室") != -1 && o.indexOf("图解") == -1 && o.indexOf("解读") == -1 && o.indexOf("媒体") == -1 && o.indexOf("动漫微信") == -1) {
                    System.out.println("111111" + o);
                    break;
                }
            } else {
                if (o.indexOf("图解") == -1 && o.indexOf("解读") == -1 && o.indexOf("媒体") == -1 && o.indexOf("动漫微信") == -1 && o.indexOf("政府办公室") == -1) {
                    System.out.println("222222" + o);
                    break;
                }
            }
        }
    }

    @Test
    public void test47() {
        String htmlContent = "<p style=\"margin: 0px 0px 20px; padding: 0px; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimSun, Arial, Helvetica, Tahoma, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial; text-indent: 43px;\"><span style=\"font-size: 20px; font-family: 宋体, SimSun;\">PDF下载：<img border=\"0\" src=\"/assets/images/files2/pdf.gif\"><a href=\"/download/5f979718e4b0c2cf781ae453.pdf\" target=\"_blank\" data-file-ext=\"PDF\">宿州市制止餐饮浪费行为条例.pdf</a></span></p>";
        String regEx_script = "<\\s*img(.+?)src=[\"'](.*?)[\"']\\s*/?\\s*>";
        Pattern p_html = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlContent);
        htmlContent = m_html.replaceAll("");
        System.out.println(htmlContent);
    }

    @Test
    public void test48() {
        String a = "ssss.pdf";
        int i = a.indexOf(".pdf");
        System.out.println(i);
        String b = a.substring(i);
        System.out.println(b);
    }

    @Test
    public void test49() {
        String a = "2";
        String b = "2";
        Long c = 1L;
        List<String> aa = new ArrayList<>();
        List<String> bb = new ArrayList<>();
        List<Long> cc = new ArrayList<>();
        aa.add(a);
        aa.add(a);
        aa.add(a);
        List<Long> collect = aa.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());

        bb.add(b);
        bb.add(b);
        bb.add(b);

        cc.add(c);
        cc.add(c);
        cc.add(c);
        boolean disjoint = Collections.disjoint(collect, cc);
        System.out.println(disjoint);
    }

    @Test
    public void test50() {
        String a = "123abc";
        String regex = "^[0-9]+abc$";
        boolean matches = a.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void test51() {
        String organId = "1";
        String type = "12313";
        String catId = "123134411";
        String target = "_dsd";
        String link = "<a href=\"/zwgk/public/column/%s?type=%s&catId=%s&action=list\" data-organId=\"%s\" data-catId=\"%s\" target=\"%s\">%s</a>";
        String xxx = String.format(link, organId, type, catId, organId, catId, target, "xxx");
        System.out.println(xxx);
    }

    @Test
    public void test52() {
        String a = "167459291_11708048_sz-a-gova";
        String[] split = a.split(",");
        String b = "1";
        for (int i = 1; i < split.length; i++) {
            b += ",1";
        }
        System.out.println(b);

    }

    @Test
    public void test53() {
        Integer anInt = getInt(5);
        System.out.println(anInt);
    }

    public Integer getInt(Integer a) {
        if (a == 1) {
            return 1;
        } else {
            return (a + 2);
        }
    }

    @Test
    public void test54() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(2);
        }
    }

    @Test
    public void test55() {
        String a = "1";
        Long b = 1L;
        boolean equals = a.equals(b.toString());
        System.out.println(equals);
    }

    @Test
    public void test56() {
        String a = "abc";
        boolean a1 = a.indexOf("a") == 0;
        int b = a.indexOf("a");
        System.out.println(b);
        System.out.println(a1);
        String c= "";
        int length = c.length();
        System.out.println(length);
    }

    @Test
    public void test57() {
        Random rand = new Random();
        char[] letters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'r',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String str = "";
        int index;
        boolean[] flags = new boolean[letters.length];//默认为false
        for (int i = 0; i < 5; i++) {
            do {
                index = rand.nextInt(letters.length);
            } while (flags[index] == true);
            char c = letters[index];
            str += c;
            flags[index] = true;
        }
        System.out.println(str);
    }

    @Test
    public void test58() {
        String html = "<title></title>\n" +
                "<link href=\"/cloudstore/resource/pc/ckeditor-4.6.2/weaver/contents.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "<link href=\"/cloudstore/resource/pc/ckeditor-4.6.2/plugins/copyformatting/styles/copyformatting.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "<link href=\"/cloudstore/resource/pc/ckeditor-4.6.2/plugins/tableselection/styles/tableselection.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "<link href=\"/cloudstore/resource/pc/ckeditor-4.6.2/plugins/widget/styles/widget.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "<link href=\"/cloudstore/resource/pc/ckeditor-4.6.2/plugins/image2/styles/image2.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "<p style=\"font-size:14px; text-align:justify; font-family:Calibri\"><span style=\"font-size:21px; font-family:仿宋_GB2312\">&nbsp;&nbsp;&nbsp; 3月25日下午，省电子工会赴合肥维信诺科技有限公司进行调研。</span></p>\n" +
                "<p style=\"font-size:14px; text-align:justify; font-family:Calibri\"><span style=\"font-size:21px; font-family:仿宋_GB2312\">&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-family:仿宋_GB2312\">调研过程中，省电子工会一行在企业工会主席陪同下，参观了维信诺公司的企业展厅，详细了解了企业发展历程、产业生态以及企业主要产品与技术等。据悉该公司成立于</span>2018年，是清华大学控股的一家高新技术企业，主要从事OLED柔性显示屏的研发和生产。</span></p>\n" +
                "<p style=\"font-size:14px; text-align:justify; font-family:Calibri\"><span style=\"font-size:21px; font-family:仿宋_GB2312\">&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-family:仿宋_GB2312\">参观结束后，省电子工会听取了公司工会工作的汇报，并就劳动竞赛、职工入会等事宜与企业工会开展了交流。</span></span></p>";

        String regEx = "(?!<(img|p|/p|span|/span|br|/br).*?>)<.*?>";
        Pattern phtml = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher mhtml = phtml.matcher(html);
        String ans = mhtml.replaceAll("");
        ans = ans.trim();
//        System.out.println(ans);
        String regEx2 = " style=\"(.*?)\"";
        Pattern phtml2 = Pattern.compile(regEx2, Pattern.CASE_INSENSITIVE);
        Matcher mhtml2 = phtml2.matcher(ans);
        String ans2 = mhtml2.replaceAll("");
        System.out.println(ans2);
    }

    @Test
    public void test59() {
        int temp[] = {2, 7, 11, 15};
        int target = 9;
        int resultArray[] = twoSum(temp, target);
        System.out.println(resultArray);
        for (int i : resultArray) {
            System.out.println(i);
        }
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    @Test
    public void test60() {
        int temp[] = {2, 7, 11, 15};
        int target = 9;
        int resultArray[] = twoSum2(temp, target);

    }

    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        System.out.println(map);
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                System.out.println(i);
            }
        }
        return null;
    }

    public int lengthOfLongestSubstring(String s) {
        //滑动窗口求解
        int length = 0;
        //起始结束下标
        int start = 0;
        int end = 0;
        Set<Character> set = new HashSet<>();
        while (start<s.length() && end <s.length()){
            //如果重复 窗口左侧右移
            if(set.contains(s.charAt(end))){
                set.remove(s.charAt(start++));
            }
            //不重复 窗口右侧右移
            else {
                set.add(s.charAt(end++));
                //比较当前长度与临时存储长度取最大值
                length = Math.max(length,end-start);
            }
        }

        return length;
    }

    @Test
    public void test61(){
        int length = lengthOfLongestSubstring("");
        System.out.println(length);
    }

    @Test
    public void test62(){
        int[] nums1= {1,2,3};
        int[] nums2= {4,5,6};
        double medianSortedArrays = findMedianSortedArrays(nums1, nums2);
        System.out.println(medianSortedArrays);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //合并两个数组
        int[] newArray = Arrays.copyOf(nums1,nums1.length+nums2.length);
        for (int i = 0; i < nums2.length; i++) {
            newArray[nums1.length+i]=nums2[i];
        }
        //排序
        Arrays.sort(newArray);
        if(newArray.length ==0){
            return Double.valueOf(0);
        }else if(newArray.length ==1){
            return newArray[0];
        }else if(newArray.length % 2 ==1){
            return Double.valueOf(newArray[newArray.length/2]);
        }else {
            return Double.valueOf((newArray[newArray.length/2 ] + newArray[newArray.length/2 -1]) /2.0);
        }
    }

    @Test
    public void test63(){
        int f = f(10);
        System.out.println(f);
    }
    public static int f(int n){
        if(n==1){
            return 1;
        }
        return f(n-1)+n;
    }





    @Test
    public void test64(){
        solve(3, "a柱", "b柱", "c柱");
    }
    public void solve(int n, String src, String transfer, String dst) {
        if(n == 1) {
            move(n, src, dst);
        } else {
            solve(n-1, src, dst, transfer);
            move(n, src, dst);
            solve(n-1, transfer, src, dst);
        }
    }
    public void move(int n, String src, String dst) {
        System.out.println(n + " " + src + " -----> " + dst);
    }


    @Test
    public void test65(){
        String s = "a";
        String s1 = longestPalindrome(s);
        System.out.println(s1);
    }
    public String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int max = 1;
        int start = 0;
        //end 初始化 1防止截取为空返回
        int end =1;
        //奇数回文串 例：aba
        for (int i = 0; i < s.length(); i++) {
            int left = i-1;
            int right = i +1;
            while (left>=0 &&right<s.length()&&chars[left]==chars[right]){
                if(right-left+1 >max){
                    start =left;
                    end = right+1;
                    max=right-left+1;
                }
                left--;
                right++;
            }
        }
        //偶数回文串 例：bb
        for (int i = 0; i < s.length(); i++) {
            int left = i;
            int right = i +1;
            while (left>=0 &&right<s.length()&&chars[left]==chars[right]){
                if(right-left+1 >max){
                    start =left;
                    end = right+1;
                    max=right-left+1;
                }
                left--;
                right++;
            }
        }
        return s.substring(start,end);
    }

    @Test
    public void test66(){
        String s = "bacad";
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println(String.valueOf(i)+"....."+chars[i]);
        }
        String substring = s.substring(1, 3);
        System.out.println(substring);
    }

    @Test
    public void test67(){
        String a = "a18196548685a";
        String r = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(a);
        System.out.println(m.find());
    }

    @Test
    public void test68(){
        String a = "<div style=\"TEXT-INDENT: 2em\">因季节变化，我市城区很多道路两侧落叶纷纷，这给环卫保洁工作带来不小困难。为确保落叶不影响城区的整体环境卫生水平，近日，市环卫处采取多项措施，加大道路清扫力度。\n" +
                "道路树叶往往是扫了又落，边扫边落，遇到刮风下雨，更是难以清扫。对此，环卫保洁人员适当改变作业方式，增加保洁频率，在天晴风小的时候先把落叶扫成堆，然后迅速清理到垃圾车内，同时加大循环保洁力度，控制路面落叶量。由于大量落叶造成垃圾运输、处置的压力增大，市环卫处出动垃圾压缩车2台、每班次组织人员200余人每天从7：00至工作晚上21：00，对城区环城路、汴河路、西昌路、东昌路、宿怀路等重点路段加大人工、机械投入，进行拉网式巡回收集落叶，自11月21日到25日共清运法梧落叶1000余方，有力确保文明创建成果。<p align=\"center\"><img border=\"0\" src=\"http://www.ahszcg.gov.cn/UploadFiles/2013/0/2013112711384921177.jpg\" width=\"362\" height=\"268\"></p></div>";
        String r = "1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(a);
        System.out.println(m.find());
    }

    @Test
    public void test69(){
        String s = "PAYPALISHIRING";
        int numRows = 4;
        String convert = convert(s, numRows);
        System.out.println(convert);
    }

    public String convert(String s, int numRows) {
        /*
            以下为最终排列结果，数字为原字符位置对应下标
            规律：以4行为例  第一行，最后一行 依次增加 2 * (numRows - 1)->6个位下标；
                            中间行 第一次增量 hf-2*i ->4 后续依次为 hf - mid
            P0    A4    H8      N12
            A1 P3 L5 S7 I9  I11 G13
            Y2    I6    R10

            4行排列：
            P0      I6       N12
            A1   L5 S7   I11 G13
            Y2 A4   H8  R10
            P3      I9
         */
        //单列或单行
        if(s.length()<=numRows||numRows==1){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        //首尾增量
        int hf = 2 * (numRows - 1);
        for (int i = 0; i < numRows; i++) {
            int index = i;
            //首尾行
            if(i==0||i==numRows-1){
                while (index<s.length()){
                    sb.append(s.charAt(index));
                    index = index + hf;
                }
            }else {
                //中间行增量
                int mid = hf-2*i;
                while (index < s.length()) {
                    sb.append(s.charAt(index));
                    index = index + mid;
                    mid = hf - mid;
                }
            }
        }
        return sb.toString();
    }

    @Test
    public void test70(){
        System.out.println(reverse(1534236469));
    }

    public int reverse(int x) {
        //注意捕获异常
        if(x>0){
            StringBuffer s=new StringBuffer(String.valueOf(x));
            s = s.reverse();
            try {
                return Integer.parseInt(String.valueOf(s));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        if(x<0){
            StringBuffer s=new StringBuffer(String.valueOf((0-x)));
            s =s.reverse();
            try {
                return (0-Integer.parseInt(String.valueOf(s)));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return x;
    }

    @Test
    public void test71(){
        Long l = Long.parseLong("9646324351");
        System.out.println(l);
    }

    @Test
    public void test72(){
        boolean palindrome = isPalindrome(10);
        System.out.println(palindrome);
    }
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);

        int l = 0;
        int r = s.length()-1;
        while (l<r){
            if(s.charAt(l) !=s.charAt(r)){
                return false;
            }
            l++;
            r--;

        }
        return true;
    }

    @Test
    public void test73() {

        try {
            List<Account> accounts =new ArrayList<>();
            Account account = new Account(1,"aa",2D);
            Account account2 = new Account(3,"bb",3D);
            accounts.add(account);
            accounts.add(account2);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(accounts);//序列化
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            List<Account> newAccounts = (List<Account>) oi.readObject();//反序列化
            newAccounts.stream().forEach(e-> System.out.println(e.getId()+"=="+e.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test74(){
        String columnTypeCode = "collectInfo";
        Set<String> labelSet = new HashSet<>();
        labelSet.add("articleNewsPageList");
        labelSet.add("pictureNewsPageList");
        labelSet.add("videoNewsPageList");
        labelSet.add("guestBookPageList");
        labelSet.add("fileDownloadPageList");
        labelSet.add("collectInfoPageList");
        labelSet.add("ideaCollectPageList");
        labelSet.add("surveyPageList");
        for (String labelName : labelSet) {
            if((labelName.indexOf("ideaCollectPageList") > -1) &&("survey".equals(columnTypeCode)||"collectInfo".equals(columnTypeCode))){
                System.out.println("ideaCollectPageList");
                break;
            }

            if (labelName.indexOf(columnTypeCode) > -1) {
                System.out.println(labelName);
                break;
            }
        }

    }

    @Test
    public void test75(){
        String columnTypeCode = "collectInfo";
        Set<String> labelSet = new HashSet<>();
        labelSet.add("articleNewsPageList");
        labelSet.add("pictureNewsPageList");
        labelSet.add("videoNewsPageList");
        labelSet.add("guestBookPageList");
        labelSet.add("fileDownloadPageList");
        labelSet.add("collectInfoPageList");
        labelSet.add("ideaCollectPageList");
        labelSet.add("surveyPageList");
        for (String s : labelSet) {
            int ideaCollectPageList = s.indexOf("ideaCollectPageList");
            System.out.println(ideaCollectPageList);
        }
    }


    public static List<String> getSrcHrefList(String content) {
        List<String> list = new ArrayList<>();
        //开始匹配content中的src,href
        Pattern patten = Pattern.compile("(src|SRC|href|HREF|Href)=(\"|\')(.*?)(\"|\')");
        Matcher matcher = patten.matcher(content);
        boolean result = matcher.find();
        if (result) {
            while (result) {
                //获取到匹配的内容
                String str = matcher.group(3);
                list.add(str);
                //匹配content中是否存在下一个标签，有则继续以上步骤匹配标签中的src
                result = matcher.find();
            }
        }
        return list;
    }
    public String oldContentToNew(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        List<String> srcHrefList = getSrcHrefList(content);
        for (String s : srcHrefList) {
            String replace = s;
            if (s.contains("https://zhglxt.ahszgjj.com/")) {
                replace = replace.replace("https://zhglxt.ahszgjj.com/", "/ahszgjjoldfiles/" );
            }
            content = content.replace(s, replace);
        }
        return content;
    }

    @Test
    public void test76(){
        String s = oldContentToNew("<div class=\"text\">\n" +
                "                                <p style=\"text-indent: 43px; text-align: left;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\">5月6日下午，我市2020年住房公积金管理委员会第一次全体委员（扩大）会议在市政府第一会议室圆满召开，</span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">会议由我</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">中心主任王波主持，</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">市政府</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">副市长张海虹出席会议并讲话。</span></span></p><p style=\"text-indent: 43px; text-align: left;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">会议书面通报</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">了</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">市住房公积金</span>2019年年度报告、市应对新冠肺炎疫情住房公积金阶段性支持政策实施办法，书面审议并通过</span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">了</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\">2019年度市住房公积金归集、使用计划和增值收益分配方案执行情况的报告。</span></p><p style=\"text-indent: 43px; text-align: left;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">会上，听取并审议通过</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">了</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">《关于</span>2019年度市住房公积金工作任务完成情况及2020年工作计划安排的报告》、《关于编制2020年住房公积金归集、使用计划和增值收益分配方案的意见》和《关于2020年向商业银行融资的意见》。</span></p><p style=\"text-indent: 43px; text-align: left;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">张</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">市长</span></span><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">对</span>2019年度住房公积金在服务大局、改善民生和促进房地产市场平稳健康发展中发挥的重要作用给予充分肯定</span><strong><span style=\"font-family: 仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">，</span></span></strong><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">对做好</span>2020年住房公积金工作，提出要充分认识住房公积金阶段性支持政策对促进我市经济社会发展的重要作用，务必落实落细阶段性支持政策，切实保障缴存职工权益。同时强调，要持续抓好归集扩面工作，在扩大公积金制度覆盖面上实现新突破；要持续围绕管好用好资金，在提高住房公积金使用率上做好新谋划；要持续注重抓好队伍建设，在提升服务管理质量水平上再上新台阶。</span></p><p style=\"text-indent: 43px; text-align: left;\"><span style=\"font-family: 仿宋_GB2312;font-size:18px;\">会议应当委员25人，实到委员23人。</span><span style=\"font-family: 仿宋_GB2312;font-size:18px;\">我中</span><span style=\"font-family: 仿宋_GB2312;font-size:18px;\">心科级以上干部列席本次会议，《拂晓报》、宿州电视台等新闻媒体应邀委派记者列会。</span></p><p style=\"text-indent: 43px; text-align: center;\"><span style=\"font-family: 仿宋_GB2312;font-size:18px;\"></span></p><p style=\"text-align: center;\"><img src=\"https://zhglxt.ahszgjj.com/Content/Images/ueditor/20200508/6372453566373336665520303.JPG\" style=\"\" title=\"1.JPG\"/></p><p style=\"text-align: center;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\">2020年全市住房公积金管理委员会第一次全体委员（扩大）会议在市政府第一会议室召开</span></p><p style=\"text-align: center;\"><img src=\"https://zhglxt.ahszgjj.com/Content/Images/ueditor/20200508/6372453566919023548289275.JPG\" style=\"\" title=\"2.JPG\"/></p><p style=\"text-align:center;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">中心党组书记、主任王波作工作报告</span></span></p><p style=\"text-align: center;\"><img src=\"https://zhglxt.ahszgjj.com/Content/Images/ueditor/20200508/6372453567426383247170984.jpg\" title=\"3.jpg\"/><br/></p><p style=\"text-align:center;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">会议审议有关事项</span></span></p><p style=\"text-align: center;\"><img src=\"https://zhglxt.ahszgjj.com/Content/Images/ueditor/20200508/6372453567932241884296031.JPG\" style=\"\" title=\"4.JPG\"/></p><p style=\"text-align: center;\"><span style=\"font-family:仿宋_GB2312;font-size:18px;\"><span style=\"font-family:仿宋_GB2312;\">市政府副市长、管委会主任张海虹同志讲话</span></span></p>\n" +
                "                            </div>");
        System.out.println(s);
    }

    @Test
    public void test77(){
        /*Person person = new Person.PersonBuilder()
                .id(1L)
                .name("小白").build();
        String string = person.toString();
        System.out.println(string);*/
    }

    @Test
    public void test78(){
        String a  = ".sss";
        boolean sss = a.contains("sss");
        System.out.println(sss);
    }

    @Test
    public void test79(){
        String rex = "(?<=id=)\\d{0,20}";
        String a = "<a href=\"http://files.huangshan.gov.cn:81/download?siteId=1&id=19753\" target=\"_blank\">";
        Pattern ps = Pattern.compile(rex);
        Matcher matchers = ps.matcher(a);
        System.out.println(matchers.find());
        System.out.println(matchers.group(0));


    }
    private final String REX_HREF = "<a.{0,50}http://files.huangshan.gov.cn:81/.*?(>)";

    private final String SITE_ID = "(?<=siteId=)\\d{0,20}";
    private final String ID = "(?<=id=)\\d{0,20}";
    @Test
    public void test80() {
        Pattern p = Pattern.compile(REX_HREF);
        Matcher matcher = p.matcher("　　本报讯 记者祁俊报道 4月13日，市委书记王福宏在市委中心组理论学习会上指出，二季度是实现全年目标的重要时期，希望全市上下一定要增强发展信心，鼓足发展干劲，咬定目标，提振信心，奋力赶超，确保时间过半、任务过半，为全面完成年度目标任务奠定更加坚实的基础，以优异成绩迎接建国60周年。<br>\n" +
                "&nbsp; &nbsp;会议按照学习实践活动第一阶段要求，集中学习了中央有关文件、《深入学习实践科学发展观活动领导干部学习文件选编》、《毛泽东、邓小平、江泽民论科学发展》和《科学发展观重要论述摘编》、胡锦涛在纪念党的十一届三中全会召开30周年大会上的讲话（节选）；通报了一季度全市经济形势，各牵头责任领导结合分管工作解析并汇报了中央、省、市政策要点及争取落实情况。<br>\n" +
                "&nbsp; &nbsp;王福宏在会上作重要讲话。他说，召开中心组理论学习会，通过学习、分析、部署和传达“四位一体”，起到了促进学习、推动工作、规范运作、提高效益、增长才干的作用。相信此次会议一定能成为进一步提振信心的“强化剂”、进一步加快发展的“助推器”、进一步做好各项工作的“加油站”。全市上下要正视不足，以利再战。今年以来，面对复杂严峻的国内外发展形势，各地各部门坚决贯彻落实“保增长、保民生、保稳定”的一系列政策措施，按照年初确立的工作目标，迎难而上，积极作为，全市经济起步总体平稳，主要经济指标增势趋于好转，各项工作扎实推进，对这些成绩应予充分肯定。<br>\n" +
                "&nbsp; &nbsp;王福宏指出，要提振信心，强化责任。要着力解决好工作中存在的信心不足、责任心不强、解决问题不力、协同力不够等问题。心中始终装满信心，时刻把责任顶在头上。分析形势，研究问题，不仅要看纵向，而且要看横向，要看到差距、看到不足，在比较中学习借鉴，树立标杆，奋力追赶；不仅要看面上，而且要看重点，只有抓住重点，用力的方向才会明确，采取的措施才会对路，困难的局面才能打破，发展的效果才会增强；不仅要看当前，分析已经发生的和正在发生的情况，更要善于把握趋势、寻求规律，加强规律性分析，增强工作前瞻性、预见性，牢牢把握发展的主动权，并最大限度地减少不利因素、扩大有利因素，按照规律促使事物不断向好的方面转化；不仅要看成绩，更要清醒地看到不足和问题，只有这样才能保持清醒头脑，增强工作的主动性、针对性和有效性；不仅要分析造成不足的客观原因，更要注重从主观上找差距、找问题。面对困难决不能畏难，困难加畏难，越走越难；面对差距决不能差劲，差距加差劲，越干越没劲。能够清醒地认识不足，是有境界的表现；主动从主观上、工作上找差距，是有勇气的表现；如果在今后的工作中认真有效地改进这些不足、弥补这些差距，则是有能力、有水平的表现，以此来推动事业不断发展、工作不断进步。<br>\n" +
                "&nbsp; &nbsp;王福宏要求，要突出重点，狠抓落实，着力抓好年初布置的11件大事和学习实践科学发展观活动中要见成效的10个方面工作。主线就是以深入开展学习实践科学发展观活动为动力，抢抓国家扩大内需政策机遇，确保实现“三保”目标任务，并为推进更高水平、持续快速发展创造和积累条件。一是努力形成一整套符合科学发展观要求的、能有效促进黄山崛起的发展理念，并不断予以完善；二是确保经济平稳较快增长，全年各项发展目标任务圆满完成，并力争完成得更好、发展得更快；三是“十大工程”要全面推进，尤其是要确保三条高铁年内开工建设；四是认真研究谋划“四区建设”和“四大体系”，形成文件并付诸实施；五是扎实推进30项民生工程，确保我市民生工程建设继续走在全省前列；六是确保黄山口岸开放获得批准、黄山服务业改革综合试点城市争取成功、皖南旅游文化示范区建设和“四大整治”等重点工作取得重大进展；七是建立健全符合科学发展观的考核评价体系，并加以施行；八是建立健全科学发展的决策体系；九是加强政治生态建设，着力解决干部作风上存在的五个方面突出问题；十是按照“六个凡是”的要求，确保各项工作争先进位。<br>\n" +
                "&nbsp; &nbsp;陈强就做好二季度工作提出了明确要求，强调要强化项目建设和工业发展，项目建设是拉动黄山发展的主动力，要抓住当前晴好天气，力争在建项目本月底百分百复工，新上项目二季度全面开工，对未落实项目要紧追不放，进一步做好企业帮扶工作；要突出旅游营销和招商引资工作，“双十”计划要强力推进，黄山国际旅游节等三项活动要加紧筹备，重点要抓好长三角、珠三角和香港、台湾等地招商引资工作；要抓紧春茶生产和防汛工作，目前春茶生产开局良好，要抓好茶叶的收购、制作和营销三个环节，特别要注重茶叶的市场营销和拓展，严防制假、掺假、贩假，确保黄山茶叶的高品质，防汛工作要立足于早、立足于防，特别是要做好对病险水库、地质灾害点的检查、巡查工作，做好防汛器材、人力和应急预案的准备工作；要主动抓好规划谋划和融资增资工作，做好规划是立足当前着眼长远、既有即期效益又有长远效益的根本问题，要拓展思路做好融资增资工作；要夯实民计民生和社会稳定工作，加大宣传力度，让更多群众知晓和参与民生工程，对安全生产、防火防灾等工作要高度重视，加强社会治安综合治理，重视做好手足口病防控工作。全市上下要下定决心，认真负起责任，抓好落实，抢抓进度，能快则快，能上快上，以保证全年既定目标任务圆满完成。<br>\n" +
                "&nbsp; &nbsp;市各套班子领导，各区县党政负责同志、市直有关部门负责人等参加会议。<br>\n" +
                "<p>附件:<br>\n" +
                "<a href=\"http://files.huangshan.gov.cn:81/download?siteId=1&id=19753\" target=\"_blank\">行政权力清单和责任清单.xls【0.00 KB】</a></p>");
        //内容

        if (matcher.find()) {
            //System.out.println(matcher.groupCount());
            for (int i = 0; i < matcher.groupCount(); i++) {
                String oldHref = matcher.group(i);
                System.out.println(oldHref);
                //提取siteId
                Pattern ps = Pattern.compile(SITE_ID);
                Matcher matchers = ps.matcher(oldHref);
                System.out.println(matchers.find());
                System.out.println(matchers.group(0));
                //提取id
                /*Pattern pi = Pattern.compile(ID);
                Matcher matcheri = pi.matcher(oldHref);
                System.out.println(matcheri.find());
                for (int j = 0; j < matcheri.groupCount(); j++) {
                    System.out.println(matcheri.group(i));
                }*/
            }
        }
    }

    @Test
    public void test81(){
        //  /8/201712
        String a = "http://files.huangshan.gov.cn/8/201712/201712/201712/";
        int str = a.indexOf("/");
        str = a.indexOf("/",str+1);
        str = a.indexOf("/",str+1);

        int end = a.indexOf("/",str+1);
        end = a.indexOf("/",end+1);
        String substring = a.substring(str, end);
        System.out.println(substring);

    }

    @Test
    public void test82(){
        String a = "http://files.huangshan.gov.cn/8/201712/201712/201712/";
        int s = StringUtils.ordinalIndexOf(a, "/", 3);
        int e = StringUtils.ordinalIndexOf(a, "/", 5);
        String substring = a.substring(s, e);
        System.out.println(substring);
    }

    @Test
    public void test83(){
        String a =null;
        boolean isExist  = a!=null;
        System.out.println(isExist);
    }

    @Test
    public void test84(){
        Random random = new Random();
        int a = random.nextInt(10)+1;
        System.out.println(a);
    }

    @Test
    public void test85(){
        List<String> list = new ArrayList<>();
        System.out.println(list.size());
    }

    @Test
    public void test86(){
        Pattern p = Pattern.compile(REX1);
        Matcher matcher = p.matcher("aaa${zzz}dds{1}");
        if(matcher.find()){
            String group = matcher.group(0);
            System.out.println(group);
        }
    }

    @Test
    public void test87(){
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = "1558519480";
        Date date = new Date(Long.parseLong(a) * 1000);
        String format = dateFormat2.format(date);
        System.out.println(format);
    }

    @Test
    public void test88(){
        String a = "C:/work_space/test/2021年上半年数据.xls";
        String b = "C:/work_space/test/ces.xlsx";
        String c = "C:/work_space/test/测试.doc";
        String d = "C:/work_space/test/测试.docx";
        boolean xls = b.endsWith("xls");
        System.out.println(xls);
    }




}


























