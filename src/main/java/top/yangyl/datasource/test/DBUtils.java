package top.yangyl.datasource.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DBUtils {

    private Logger logger= LoggerFactory.getLogger(DBUtils.class);

    private static String dbUrl;
    private static String dbUser;
    private static String dbPw;
    private static DataSource dataSource;
    private static String driverClassName;

    static {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("jdbc.properties"));
            dbUrl=properties.getProperty("jdbc.url");
            dbUser=properties.getProperty("jdbc.username");
            dbPw=properties.getProperty("jdbc.password");
            driverClassName=properties.getProperty("jdbc.driver");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        synchronized (DBUtils.class){
            if(dataSource==null){
                initDataSource();
            }
            return dataSource;
        }
    }

    private static void initDataSource(){
        HikariConfig config=new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPw);
        config.setAutoCommit(true);
        config.setTransactionIsolation("2");
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(20);
        dataSource=new HikariDataSource(config);
    }

    public static int execute(String sql, List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DataSource ds=getDataSource();
        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            int i = preparedStatement.executeUpdate();
            connection.commit();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            Closer.closeQuietly(resultSet, preparedStatement, connection);
        }
    }



}
