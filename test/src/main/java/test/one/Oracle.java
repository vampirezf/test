package test.one;

import java.text.SimpleDateFormat;
import java.util.List;


public class Oracle {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static Boolean syncFlag;
    private static JdbcUtils jdbcUtils;

//    private JdbcUtils jdbcUtils = null;
//    private  Boolean syncFlag = Boolean.FALSE;

    private static JdbcUtils getJdbc() {
        synchronized (syncFlag) {
            if (!syncFlag.booleanValue()) {
                synchronized (syncFlag) {
                    syncFlag = Boolean.TRUE;
                    //jdbcUtils = new JdbcUtils("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@112.31.193.41:1521:ORCL", "BDC_GXJH", "BDC_GXJH");
                    jdbcUtils = new JdbcUtils("mysql.jdbc.Driver", "jdbc:mysql://172.23.253.132:3306/sz12345", "root", "SZ12345Gepoint");
                }
            }
        }
        System.out.println("112.31.193.41 - jdbcUtils:连接成功...");
        return jdbcUtils;
    }

    public static void main(String[] args) {
//        String bdcqzh = "皖（2019）";
//        String sql = "select count(*) As num from bdc_zfgg b where 1=1 ";
//        String newbdcqzh = "%"+bdcqzh+"%";
//        sql = sql + " and b.bdcqzh like '" + newbdcqzh + "'";
//        sql = sql + " order by b.sjscsj DESC ";
//        System.out.println("【废弃公告】根据条件获取数据sql正在查询..." + sql);
//        List list = getJdbc().excuteQuery(sql,null);
//        List voList = new ArrayList();
//        if ((null != list) && (list.size() > 0)) {
//            int i = 0;
//            for (int l = list.size(); i < l; i++) {
//                Map map = (HashMap) list.get(i);
//                AbandonNoticeVO abandonNoticeVO = new AbandonNoticeVO();
//                abandonNoticeVO.setNum((String) map.get("NUM"));
//                voList.add(abandonNoticeVO);
//            }
//        }
        String sql = "SELECT rowguid,cguid,rqsttitle,rqsttime,cstatusname,rqstperson,number,password FROM sz12345_and_szxx where datasource = '1' and webuserguid = '901' ORDER BY rqsttime desc";
        List list = getJdbc().excuteQuery(sql,null);
        if ((null != list) && (list.size() > 0)) {
            System.out.println(list.size());
        }

        /**
         *
         *  private String url = "jdbc:mysql://172.23.253.132:3306/sz12345";
            private String username = "root";
            private String password = "SZ12345Gepoint!";
         */
    }



}
