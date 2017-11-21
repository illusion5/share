package top.yangyl.novel.down;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类参考JsonFilePipeline改造
 */
public class NovelPipeline2 extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());


    public NovelPipeline2(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        //没有标题的文章不添加
        String title=(String)resultItems.get("title");
        String fileName = getFileName(title);
        if(StringUtils.isBlank(fileName)){
            return;
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(path+fileName+ ".txt")));
            printWriter.write((String)resultItems.get("content"));
            printWriter.close();
        } catch (IOException e) {
            logger.warn("write file error", e);
        }
    }

    //获取文件名
    private String getFileName(String title){
        if(StringUtils.isBlank(title)){
            return "";
        }
        //转换章节名为阿拉伯数字
        String pattern=(char)32+"*第(.+)章"+(char)32+".*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(title);
        if(m.find()){
            if(m.group(1)!=null){
                try {
                    //格式化文件名
                    return String.format("%1$04d", Long.parseLong(m.group(1)));
                }catch (Exception e){
                    return String.format("%1$04d",ChinessToNum.convert(m.group(1)));
                }
            }
        }
        return "";

    }
}
