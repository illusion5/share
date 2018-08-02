package top.yangyl.datasource.test;

import com.alibaba.fastjson.JSON;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InitOldData {

    private static String sql="SELECT supplier_id FROM purchase_supplier_project GROUP BY supplier_id";
    private static String userSql="select COMPANY_ID,LOGIN_NAME FROM t_reg_user WHERE COMPANY_ID in (";
    private static String updateSql="update purchase_supplier_project set supplier_login_name=? where supplier_id=?";

    private static DataSource purchaseDataSource=DataSourceUtils.getPurchaseDataSource();

    private static DataSource userDataSource=DataSourceUtils.getUserDataSource();

    public static void main(String[] args) {

        List<Map<String, Object>> query = DBUtils.query(purchaseDataSource, sql, null);
        StringBuilder sb=new StringBuilder();
        query.forEach(map->sb.append(map.get("supplier_id")).append(","));
        userSql=userSql+sb.toString().substring(0,sb.length()-1)+")";
        List<Map<String, Object>> userList= DBUtils.query(userDataSource, userSql, null);
        System.out.println(JSON.toJSONString(userList));
        userList.forEach(user->{
            List list=new ArrayList();
            list.add(user.get("LOGIN_NAME"));
            list.add(user.get("COMPANY_ID"));
            System.out.println(DBUtils.execute(purchaseDataSource,updateSql, list));
        });
    }



}
