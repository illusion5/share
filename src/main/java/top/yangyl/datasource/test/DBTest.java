package top.yangyl.datasource.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(15);
        String sql="INSERT INTO purchase_project_bargain (bargain_count,create_time,supplier_project_item_id) VALUES (\n" +
                "            IFNULL((SELECT\n" +
                "\t\t\t\t\t\tMAX(b.bargain_count) count\n" +
                "\t\t\t\t\tFROM\n" +
                "\t\t\t\t\t\tpurchase_project_bargain b\n" +
                "\t\t\t\t\tGROUP BY\n" +
                "\t\t\t\t\t\tb.supplier_project_item_id\n" +
                "\t\t\t\t\tHAVING\n" +
                "\t\t\t\t\t\tb.supplier_project_item_id = ?),0)+1,\n" +
                "            now(),\n" +
                "            ?\n" +
                "        )";
        List<Object> param=new ArrayList<>();
        param.add(123456789);
        param.add(123456789);
        for (int i=0;i<15;i++){
            executor.execute(getTask(sql, param));

        }
    }

    private static Runnable getTask(final String sql, final List<Object> param){
        return new Runnable() {
            @Override
            public void run() {
                int i = DBUtils.execute(sql, param);
                System.out.println(i);
            }
        };
    }
}
