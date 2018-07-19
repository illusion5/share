package top.yangyl.log.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogTest {

    private static Logger logger= LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) {
        MDC.put("my","tes264t");
        logger.info("测试日志效果");
        MDC.remove("my");
    }
}
