package org.chobit.spider.tools;

import org.junit.Test;

public class ChineseNumberParserTest {


    @Test
    public void parse() {
        int d = ChineseNumberParser.parse("三十六");
        System.out.println(d);
    }

    @Test
    public void parse1() {
        int d = ChineseNumberParser.parse("十");
        System.out.println(d);
    }


}
