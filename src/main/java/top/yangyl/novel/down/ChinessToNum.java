package top.yangyl.novel.down;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChinessToNum {

    private static final Map<String,Long> number=new HashMap();
    private static final Map<String,Long> units=new LinkedHashMap<>();
    static {
        number.put("零",0L);
        number.put("一",1L);
        number.put("二",2L);
        number.put("三",3L);
        number.put("四",4L);
        number.put("五",5L);
        number.put("六",6L);
        number.put("七",7L);
        number.put("八",8L);
        number.put("九",9L);

        units.put("千",1000L);
        units.put("百",100L);
        units.put("十",10L);
    }

    /**
     * 该方法仅适用于小于一万的转换,因为小说章节最多不会超过一万章
     * @param chiness
     * @return
     */
    public static long convert1(String chiness){
        chiness=removeZero(chiness);
        long sum=0L;
        Set<String> unitSet = units.keySet();
//        System.out.println(strings);
        for (String unit:unitSet) {
            String[] split = chiness.split(unit);
            if(split.length>1){
                if("十".equals(unit) && "".equals(split[0])){
                    sum+=10L;
                }else {
                    sum+=(number.get(split[0])*units.get(unit));
                }
                chiness=split[1];
            }else {
                if( split[0].length()!=chiness.length()){
                    sum+=(number.get(split[0])*units.get(unit));
                    return sum;
                }
            }
            if("".equals(chiness)){
                return sum;
            }
        }
        sum+=(number.get(chiness));
        return sum;
    }

    public static long convert(String chiness){
        long sum=0L;
        chiness=removeZero(chiness);
        String pattern3="((.)千)?((.)百)?((.)?十)?(.*)";
        Pattern p = Pattern.compile(pattern3);
        Matcher m= p.matcher(chiness);
        if(m.find()){
//            System.out.println("Found value: " + m.group(0) );
//            System.out.println("Found value: " + m.group(1) );
//            System.out.println("Found value: " + m.group(2) );
//            System.out.println("Found value: " + m.group(3) );
//            System.out.println("Found value: " + m.group(4) );
//            System.out.println("Found value: " + m.group(5) );
//            System.out.println("Found value: " + m.group(6) );
//            System.out.println("Found value: " + m.group(7) );

            sum+=(getNum(m.group(2),false)*1000L);
            sum+=(getNum(m.group(4),false)*100L);
            sum+=(getNum(m.group(6),m.group(5)!=null)*10L);
            sum+=(getNum(m.group(7),false)*1L);

        }
//        System.out.println(sum);
        return sum;
    }


    /**
     * 去零操作
     * @param chiness
     * @return
     */
    private static String removeZero(String chiness){
        return chiness.replaceAll("零","");
    }

    private static long getNum(String chiness,boolean isTen){
        if(StringUtils.isBlank(chiness)){
            if(isTen){
                return 1L;
            }
            return 0L;
        }else{
            return number.get(chiness);
        }
    }


    public static void main(String[] args) {
        System.out.println(ChinessToNum.convert(""));

    }



}
