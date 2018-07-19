package top.yangyl.novel.down;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 爬虫处理及解析类
 */
public class BiQuGePageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(200).setCharset("GBK").setTimeOut(50000);

    private String replaceStr = "\n" + (char) 12288 + (char) 12288;//替换后字符串

    @Override
    public void process(Page page) {
        List<String> all = page.getHtml().css("div.bottem2").links().all();//获取链接
//        System.out.println(all);

        if (all.get(2).equals(all.get(3))) {//如果下一章链接内容 和目录链接相同 说明到最后一章了
            page.putField("end", "end");//设置结束标记
        } else {
            page.addTargetRequest(all.get(3));//添加下一章的链接到处理队列
        }
        //解析文章标题
        String title = page.getHtml().xpath("//div[@class=bookname]/h1/text()").toString();
//        System.out.println(title);
        page.putField("title",title);

        //解析文档内容
        String content = page.getHtml().xpath("//div[@id=content]/text()").toString();
        content = content.replaceAll((char) 12288 + "{2,}", replaceStr) //(char)12288 全角空格 两个以上全角空格替换为换行符加两个全角空格
                .replaceAll((char) 160 + "{2,}", replaceStr); // (char)160 半角空格 两个以上半角空格替换为换行符加两个全角空格
//        System.out.println(content);

        //设置要处理的文件内容
        page.putField("content", title + replaceStr + content);
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) throws Exception {
//        //指定输出的文件地址
//        final FileWriterUtil writer = new FileWriterUtil("D:\\data\\test13.txt");
//        //启动输出线程
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                writer.startWriter();
//            }
//        }).start();

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置代理ip
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
//                new Proxy("221.233.62.43",808)
//                ,new Proxy("125.124.161.221",808)));

        httpClientDownloader.setThread(30);
        //启动爬虫
        Spider.create(new BiQuGePageProcessor())
                .addUrl("http://www.bequge.com/8_8707/10181794.html")
                .addPipeline(new NovelPipeline2("D:\\data"))
                .thread(10).run();//启动多个线程的话，章节顺序可能会错
    }

}
