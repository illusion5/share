package top.yangyl.datasource.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private Logger logger= LoggerFactory.getLogger(DBUtils.class);

    public static int execute(DataSource ds,String sql, List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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


    public static List<Map<String, Object>> query(DataSource ds, String sql, List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    map.put(columnLabel, resultSet.getObject(columnLabel));
                }
                results.add(map);
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Closer.closeQuietly(resultSet, preparedStatement, connection);
        }
    }



}
