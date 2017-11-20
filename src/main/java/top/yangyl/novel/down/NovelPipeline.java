package top.yangyl.novel.down;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/***
 * 解析完后的处理类
 * 该类作用不大 可以把代码放进 BiQuGePageProcessor.process里
 */
public class NovelPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        //放入输出队列
        FileWriterUtil.putContent((String) resultItems.get("content"));
        if (resultItems.get("end") != null) {
            FileWriterUtil.putContent((String) resultItems.get("end"));
        }
    }
}