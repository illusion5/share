package top.yangyl.baidu.push;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *  百度搜索自动推送代码
 */
public class InitativeBaiduPush {

    //你的域名
    private static String site="yangyl.top";

    //你的token
    private static String token="EaMe5PbDDrgNngjb";

    //百度推送的地址
    private static String url="http://data.zz.baidu.com/urls?site="+site+"&token="+token;


    public static void main(String[] args) throws IOException {
        String urls = getUrls();//获取提交的url
        if("".equals(urls.trim())){
            System.out.println("待提交url为空或文件读取失败");
            return;
        }

        //使用httpClient发送post请求
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            //请求配置
            httpClient = HttpClients.createDefault();
            HttpPost httpPost=new HttpPost(url);
            httpPost.setHeader("Content-Type","text/plain");//可以注释到
            StringEntity entity = new StringEntity(urls, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            printResult(response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 从文件中获取需要提交的url
     * @return
     */
    private static String getUrls(){
        StringBuilder sb=new StringBuilder();
        try {
            InputStream stream = InitativeBaiduPush.class.getClassLoader().getResourceAsStream("push-url.txt");
            BufferedReader br=new BufferedReader(new InputStreamReader(stream));
            int len;
            char[] cbuf=new char[1024];
            while ((len=br.read(cbuf))!=-1){
                sb.append(cbuf,0,len);
            }

            //下面这种方法也行
//            String s;
//            while ((s=br.readLine())!=null){
//                sb.append(s).append("\n");
//            }
//            System.out.println(sb.toString().length());
//            return sb.toString().replaceAll("\\n+$","");//去掉最后的回车符，该行可以注释到
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 打印响应结果到控制台
     * @param response
     * @throws Exception
     */
    private static void printResult(CloseableHttpResponse response) throws Exception {
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
//        System.out.println(response.getStatusLine().getReasonPhrase());
        if(code==200){
            System.out.println("主动推送成功");
        }else {
            System.out.println("主动推送失败");
        }
        if(response.getEntity()!=null){
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        }
    }


    @Test
    public void testGetUrl(){
//        System.out.println(getUrls().length());
        System.out.println(getUrls());
    }


}
