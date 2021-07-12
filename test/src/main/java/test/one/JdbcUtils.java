package test.one;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {
    /**
     * 数据库驱动类名称
     */
    private String DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    /**
     * 连接字符串
     */
    private String URLSTR = "";

    /**
     * 用户名
     */
    private String USERNAME = "";
    /**
     * 密码
     */
    private String USERPASSWORD = "";
    /**
     * 创建数据库连接对象
     */
    private Connection connnection = null;
    /**
     * 创建PreparedStatement对象
     */
    private PreparedStatement preparedStatement = null;
    /**
     * 创建CallableStatement对象
     */
    private CallableStatement callableStatement = null;
    /**
     * 创建结果集对象
     */
    private ResultSet resultSet = null;

    private JdbcUtils() {
    }

    public JdbcUtils(String UrlStr, String username, String password) {
        try {
            // 加载数据库驱动程序
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动错误");
            System.out.println(e.getMessage());
        }
        this.URLSTR = UrlStr;
        this.USERNAME = username;
        this.USERPASSWORD = password;
    }

    public JdbcUtils(String Driver, String UrlStr, String username, String password) {
        try {
            // 加载数据库驱动程序
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动错误");
            System.out.println(e.getMessage());
        }
        this.DRIVER = Driver;
        this.URLSTR = UrlStr;
        this.USERNAME = username;
        this.USERPASSWORD = password;
    }

    private static class SingletonHolder {
        private static final JdbcUtils INSTANCE = new JdbcUtils();
    }

    public static final JdbcUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 建立数据库连接
     *
     * @return 数据库连接
     */
    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            // 获取连接
            connnection = DriverManager.getConnection(URLSTR, USERNAME, USERPASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("加载驱动错误");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connnection;
    }

    /**
     * insert update delete SQL语句的执行的统一方法
     *
     * @param sql    SQL语句
     * @param params 参数数组，若没有参数则为null
     * @return 受影响的行数
     */
    public int executeUpdate(String sql, Object[] params) {
        // 受影响的行数
        int affectedLine = 0;
        try {
            // 获得连接
            commonSql(sql, params);
            // 执行
            affectedLine = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // 释放资源
            closeAll();
        }
        return affectedLine;
    }

    /**
     * SQL 查询将查询结果直接放入ResultSet中
     *
     * @param sql    SQL语句
     * @param params 参数数组，若没有参数则为null
     * @return 结果集
     */
    private ResultSet executeQueryRS(String sql, Object[] params) {
        try {
            commonSql(sql, params);
            // 执行
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }

    private void commonSql(String sql, Object[] params) throws SQLException {
        // 获得连接
        connnection = this.getConnection();
        // 调用SQL
        preparedStatement = connnection.prepareStatement(sql);
        // 参数赋值
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * SQL 查询将查询结果：一行一列
     *
     * @param sql    SQL语句
     * @param params 参数数组，若没有参数则为null
     * @return 结果集
     */
    public Object executeQuerySingle(String sql, Object[] params) {
        Object object = null;
        try {
            // 获得连接
            commonSql(sql, params);
            // 执行
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = resultSet.getObject(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAll();
        }
        return object;
    }

    /**
     * 获取结果集，并将结果放在List中
     *
     * @param sql SQL语句
     * @return List 结果集
     */
    public List<Object> excuteQuery(String sql, Object[] params) {
        // 执行SQL获得结果集
        ResultSet rs = executeQueryRS(sql, params);
        // 创建ResultSetMetaData对象
        ResultSetMetaData rsmd = null;
        // 结果集列数
        int columnCount = 0;
        try {
            rsmd = rs.getMetaData();
            // 获得结果集列数
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        // 创建List
        List<Object> list = new ArrayList<Object>();
        try {
            // 将ResultSet的结果保存到List中
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭所有资源
            closeAll();
        }

        return list;
    }

    /**
     * 存储过程带有一个输出参数的方法
     *
     * @param sql         存储过程语句
     * @param params      参数数组
     * @param outParamPos 输出参数位置
     * @param SqlType     输出参数类型
     * @return 输出参数的值
     */
    public Object excuteQuery(String sql, Object[] params, int outParamPos, int SqlType) {
        Object object = null;
        try {
            connnection = this.getConnection();
            // 调用存储过程
            callableStatement = connnection.prepareCall(sql);
            // 给参数赋值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    callableStatement.setObject(i + 1, params[i]);
                }
            }
            // 注册输出参数
            callableStatement.registerOutParameter(outParamPos, SqlType);
            // 执行
            callableStatement.execute();
            // 得到输出参数
            object = callableStatement.getObject(outParamPos);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // 释放资源
            closeAll();
        }
        return object;
    }

    /**
     * 关闭所有资源
     */
    public void closeAll() {
        // 关闭结果集对象
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        // 关闭PreparedStatement对象
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        // 关闭CallableStatement 对象
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭Connection 对象
        if (connnection != null) {
            try {
                connnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void createReocrdTable() {
        String sql = "create table cms_import_record (\n" +
                "  old_column_id int  not null,\n" +
                "  old_column_name varchar(50)  not null,\n" +
                "  column_id int  not null,\n" +
                "  column_name varchar(50) not null,\n" +
                "  success_count int  not null,\n" +
                "  fail_count int  not null," +
                "  update_date varchar(20))";

        // 获得连接
        connnection = this.getConnection();
        // 调用SQL
        try {
            preparedStatement = connnection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * 查询数据库是否有某表
     * @param tableName
     * @return
     */
    public boolean validateTable(String tableName) {
        // 获得连接
        connnection = this.getConnection();
        ResultSet tabs;
        try {
            DatabaseMetaData dbMetaData = connnection.getMetaData();
            String[] types = {"TABLE"};
            tabs = dbMetaData.getTables(null, null, tableName, types);
            if (tabs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return false;
    }




    public String getDRIVER() {
        return DRIVER;
    }

    public void setDRIVER(String DRIVER) {
        this.DRIVER = DRIVER;
    }

    public String getURLSTR() {
        return URLSTR;
    }

    public void setURLSTR(String URLSTR) {
        this.URLSTR = URLSTR;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getUSERPASSWORD() {
        return USERPASSWORD;
    }

    public void setUSERPASSWORD(String USERPASSWORD) {
        this.USERPASSWORD = USERPASSWORD;
    }
}