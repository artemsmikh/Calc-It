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
    }
}
