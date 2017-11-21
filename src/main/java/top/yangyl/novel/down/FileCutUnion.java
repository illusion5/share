package top.yangyl.novel.down;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FileCutUnion {

    public static void main(String[] args) throws Exception {
        File fileDir=new File("D:\\data");
        String[] list = fileDir.list();
        for (String fileName:list) {
            System.out.println(fileName);
        }




        /*FileWriter writer=new FileWriter("D:\\data\\文件合并.txt", true);
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
        }*/
    }

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


