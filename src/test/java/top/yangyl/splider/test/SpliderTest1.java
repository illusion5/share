package top.yangyl.splider.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SpliderTest1 {

    @Test
    public void test01() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.15 Safari/537.36");
        CloseableHttpResponse response = null;
        FileOutputStream fileOutputStream=null;
        try {
            response = httpClient.execute(httpGet);
            InputStream inputStream = response.getEntity().getContent();
            fileOutputStream=new FileOutputStream("D:\\data\\baidu.txt");
            byte[] bytes=new byte[1024];
            int len=0;
            while ((len=inputStream.read(bytes))!=-1){
                fileOutputStream.write(bytes,0,len);
            }
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            safeClose(fileOutputStream);
            safeClose(response);
            safeClose(httpClient);
        }
    }

    public static void safeClose(Object obj){
        if(obj!=null){
            Class<?> objClass = obj.getClass();
            try {
                Method closeMethod = objClass.getMethod("close");
                closeMethod.setAccessible(true);
                closeMethod.invoke(obj);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Test
    public void test02() throws IOException {
        CloseableHttpClient httpClient=HttpClients.createDefault(); // 创建httpClient实例
        HttpGet httpGet=new HttpGet("http://www.taobao.com"); // 创建httpget实例
        HttpHost proxy=new HttpHost("47.94.23.128", 8888);
        RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response=httpClient.execute(httpGet); // 执行http get请求
        HttpEntity entity=response.getEntity(); // 获取返回实体
        System.out.println("网页内容："+EntityUtils.toString(entity, "utf-8")); // 获取网页内容
        response.close(); // response关闭
        httpClient.close(); // httpClient关闭
    }
}
