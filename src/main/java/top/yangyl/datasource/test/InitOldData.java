package top.yangyl.datasource.test;

import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InitOldData {

    private static String sql="SELECT supplier_id FROM purchase_supplier_project GROUP BY supplier_id";
    private static String userSql="select COMPANY_ID,LOGIN_NAME FROM t_reg_user WHERE COMPANY_ID=?";
    private static String updateSql="update purchase_supplier_project set supplier_login_name=? where supplier_id=?";

    private static DataSource purchaseDataSource=DataSourceUtils.getPurchaseDataSource();

    private static DataSource userDataSource=DataSourceUtils.getUserDataSource();

    public static void main(String[] args) {

        List<Map<String, Object>> query = DBUtils.query(purchaseDataSource, sql, null);
        for (Map map:query) {
            List list=new ArrayList();
            list.add(map.get("supplier_id"));
            List<Map<String, Object>> result= DBUtils.query(userDataSource, userSql, list);
            if(CollectionUtils.isEmpty(result)){
                continue;
            }
            list.remove(0);
            list.add(result.get(0).get("LOGIN_NAME"));
            list.add(result.get(0).get("COMPANY_ID"));
            System.out.println(DBUtils.execute(purchaseDataSource,updateSql, list));
        }




    }



}
