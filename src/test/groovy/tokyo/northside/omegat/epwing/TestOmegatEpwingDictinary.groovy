package tokyo.northside.omegat.epwing

import static org.junit.Assert.assertTrue;;

import org.junit.Test;


class TestOmegatEpwingDictinary {

    @Test
    void testIsSupportedFile() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def omegatEpwingDictionary = new OmegatEpwingDictionary();
        assertTrue(omegatEpwingDictionary.isSupportedFile(file))
    }

}
