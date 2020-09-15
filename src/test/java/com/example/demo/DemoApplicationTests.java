package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());
    }

    @Test
    void contentLoads2() throws Exception {
        String s1 = "张三";
        String s2 = new String(s1.getBytes("GB2312"), "ISO-8859-1");
        ;
        System.out.println("字节类型:" + s1);
        LocalDateTime dt = LocalDateTime.now();

        System.out.println(dt.getYear());

        System.out.println(dt.getMonthValue());

        System.out.println(dt.getDayOfMonth());

        System.out.println(dt.getHour());

        System.out.println(dt.getMinute());

        System.out.println(dt.getSecond());
    }
}
