package tokyo.northside.omegat.epwing

import static org.junit.Assert.*;

import org.junit.Test;

class TestEBDict {

    @Test
    void testEBDictConstructor() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def ebdict = new EBDict(file)
        assertNotNull(ebdict)
    }

    @Test(expected = Exception.class)
    void testEBDictConstructorWrongFile() throws Exception {
        def file = new File("src/test/resources/data/dicts/CATALOGS")
        new EBDict(file)
    }

    @Test
    void testEBDictReadArticles() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def ebdict = new EBDict(file)
        def entries = ebdict.readArticles("Tokyo")
        for (it in entries) {
            def word = it.getWord()
            assertEquals(word, "Tokyo")
        }
    }

}
