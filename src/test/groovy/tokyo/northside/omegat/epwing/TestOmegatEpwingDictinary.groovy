package tokyo.northside.omegat.epwing

import org.testng.annotations.Test

import static org.testng.Assert.*


class TestOmegatEpwingDictinary {

    @Test
    void testIsSupportedFile() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def omegatEpwingDictionary = new OmegatEpwingDictionary();
        assertTrue(omegatEpwingDictionary.isSupportedFile(file))
    }

}
