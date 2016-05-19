package me.kantrael.calcit;

import org.junit.Test;

import me.kantrael.calcit.util.StringUtils;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {
    @Test
    public void doubleToString_isCorrect() throws Exception {
        assertEquals(StringUtils.doubleToString(232), "232");
        assertEquals(StringUtils.doubleToString(0.18), "0.18");
        assertEquals(StringUtils.doubleToString(1237875192), "1237875192");
        assertEquals(StringUtils.doubleToString(1.2345), "1.2345");
        assertEquals(StringUtils.doubleToString(-1232), "-1232");
        assertEquals(StringUtils.doubleToString(-0.056), "-0.056");
        assertEquals(StringUtils.doubleToString(-0.050000060000), "-0.05000006");
    }

    @Test
    public void stringToDouble_isCorrect() throws Exception {
        assertEquals(StringUtils.stringToDouble("232"), 232, 0);
        assertEquals(StringUtils.stringToDouble("0.18"), 0.18, 0);
        assertEquals(StringUtils.stringToDouble("1237875192"), 1237875192, 0);
        assertEquals(StringUtils.stringToDouble("1.2345"), 1.2345, 0);
        assertEquals(StringUtils.stringToDouble("-1232"), -1232, 0);
        assertEquals(StringUtils.stringToDouble("-0.056"), -0.056, 0);
        assertEquals(StringUtils.stringToDouble("-0.050000060000"), -0.05000006, 0);
    }
}
