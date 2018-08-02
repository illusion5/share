package top.yangyl.datasource.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DataSourceUtils {


  public static DataSource getPurchaseDataSource() {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("jdbc.properties"));
            String dbUrl = properties.getProperty("jdbc.purchase.url");
            String dbUser = properties.getProperty("jdbc.purchase.username");
            String dbPw = properties.getProperty("jdbc.purchase.password");
            String driverClassName = properties.getProperty("jdbc.purchase.driver");
            return initDataSource(driverClassName,dbUrl,dbUser,dbPw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static DataSource getUserDataSource() {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("jdbc.properties"));
            String dbUrl = properties.getProperty("jdbc.user.url");
            String dbUser = properties.getProperty("jdbc.user.username");
            String dbPw = properties.getProperty("jdbc.user.password");
            String driverClassName = properties.getProperty("jdbc.user.driver");
            return initDataSource(driverClassName,dbUrl,dbUser,dbPw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static DataSource getLocalDataSource() {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("jdbc.properties"));
            String dbUrl = properties.getProperty("jdbc.local.url");
            String dbUser = properties.getProperty("jdbc.local.username");
            String dbPw = properties.getProperty("jdbc.local.password");
            String driverClassName = properties.getProperty("jdbc.local.driver");
            return initDataSource(driverClassName,dbUrl,dbUser,dbPw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static DataSource initDataSource(String driverClassName,String dbUrl,String dbUser,String dbPw){
        HikariConfig config=new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPw);
        config.setAutoCommit(true);
        config.setTransactionIsolation("2");
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(20);
        return new HikariDataSource(config);
    }


}
