package top.yangyl.novel.down;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileCutUnion {

    public static void main(String[] args) throws Exception {
        File fileDir=new File("D:\\data");
        /*String[] list = file.list();
        for (String fileName:list) {
            System.out.println(fileName);
        }*/
        File[] files = fileDir.listFiles();
        FileWriter writer=new FileWriter("D:\\data\\文件合并.txt", true);
        for (File file:files) {
            FileReader fileReader=new FileReader(file);
            char[] buf=new char[1024];
            int len;
            while ((len=fileReader.read(buf))!=-1){
                writer.write(buf,0,len);
                writer.flush();
            }
            writer.write("\n");
            writer.flush();
        }
    }
}
