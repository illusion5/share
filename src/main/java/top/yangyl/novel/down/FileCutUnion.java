package top.yangyl.novel.down;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 文件合并工具类
 */
public class FileCutUnion {

    public static void main(String[] args) throws Exception {
        File fileDir=new File("D:\\data");
        File[] files = fileDir.listFiles();
        FileWriter writer=null;
        try {
            writer=new FileWriter("D:\\data\\文件合并.txt", true);
            for (File file:files) {
                FileReader fileReader=null;
                try {
                    fileReader=new FileReader(file);
                    char[] buf=new char[1024];
                    int len;
                    while ((len=fileReader.read(buf))!=-1){
                        writer.write(buf,0,len);
                        writer.flush();
                    }
                    writer.write("\n");
                    writer.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(fileReader!=null){
                        fileReader.close();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(writer!=null){
                writer.close();
            }
        }

    }

    @Test
    public void test01(){
        File fileDir=new File("D:\\data");
        File[] files = fileDir.listFiles();
        String[] list = fileDir.list();
        for (String fileName:list) {
            System.out.println(fileName);
        }
        Collections.sort(Arrays.asList(files), new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File file:files) {
            System.out.println(file.getName());
        }
    }
}


