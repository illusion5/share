package top.yangyl.novel.down;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 文件输出类
 */
public class FileWriterUtil {

    //输出队列
    private static final BlockingQueue<String> queue=new LinkedBlockingDeque(20);

    //文件名
    private String fileName;

    public FileWriterUtil(String fileName) throws Exception {
        this.fileName=fileName;
        File file=new File(fileName);
        //校验文件目录和文件是否存在 不存在则创建
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
    }


    public void startWriter(){
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            while (true){
                String msg = queue.take();
                if("end".equals(msg)){
                    break;
                }
                writer.write(msg+System.getProperty("line.separator"));
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //放入队列方法
    public static void putContent(final String msg){
            try {
                boolean offer = queue.offer(msg, 1, TimeUnit.MINUTES);
                if(!offer){
                    throw new RuntimeException("approvalLogUtil队列已满");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}
