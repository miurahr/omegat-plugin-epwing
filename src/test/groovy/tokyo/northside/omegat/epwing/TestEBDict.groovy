package tokyo.northside.omegat.epwing

import org.testng.annotations.Test

import static org.testng.Assert.*


class TestEBDict {

    @Test
    void testEBDictConstructor() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def ebdict = new EBDict(file)
        assertNotNull(ebdict)
    }

    @Test
    void testEBDictConstructorWrongFile() {
        def file = new File("src/test/resources/data/dicts/CATALOGS")
        def ebdict = new EBDict(file)
        assertNull(ebdict)
    }

    @Test
    void testEBDictReadArticles() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def ebdict = new EBDict(file)
        def entries = ebdict.readArticles("Tokyo")
        entries.each {
            def word = it.getWord()
            assertEquals(word, "Tokyo")
        }
    }

}
