package gov.nih.nci.coppa.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class DataGeneratorUtilTest {

    @Test
    public void words() {
        assertEquals("a a a a a ", DataGeneratorUtil.words(10, 'a', 1));
        assertEquals("aa aa aa a", DataGeneratorUtil.words(10, 'a', 2));
        assertEquals("aaa aaa aa", DataGeneratorUtil.words(10, 'a', 3));
        assertEquals("aaaa aaaa ", DataGeneratorUtil.words(10, 'a', 4));
        assertEquals("aaaaa aaaa", DataGeneratorUtil.words(10, 'a', 5));
        assertEquals("aaaaaa aaa", DataGeneratorUtil.words(10, 'a', 6));
        assertEquals("aaaaaaa aa", DataGeneratorUtil.words(10, 'a', 7));
        assertEquals("aaaaaaaa a", DataGeneratorUtil.words(10, 'a', 8));
        assertEquals("aaaaaaaaa ", DataGeneratorUtil.words(10, 'a', 9));
        assertEquals("aaaaaaaaaa", DataGeneratorUtil.words(10, 'a', 10));
        
    }
}
