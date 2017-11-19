package top.yangyl.novel.down;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */
public class BiQuGePageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public void process(Page page) {
        List<String> all = page.getHtml().css("div.bottem2").links().all();
//        System.out.println(all.get(2));
//        page.addTargetRequest(all.get(2));
        String title = page.getHtml().xpath("//div[@class=bookname]/h1/text()").toString();
//        System.out.println(title);

        String content = page.getHtml().xpath("//div[@id=content]/text()").toString().replaceAll((char)12288+"{2,}","\r\n");

//        System.out.println(content);
        page.putField("title",title);
        page.putField("content",content);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BiQuGePageProcessor())
                .addUrl("http://www.biqudu.com/0_903/2526459.html")
                .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                .thread(1).run();
    }
}
