package tokyo.northside.omegat.epwing

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

import static org.junit.Assert.assertTrue;;

import org.junit.Test;


@SuppressFBWarnings(value = "EI_EXPOSE_REP")
class TestOmegatEpwingDictinary {

    @Test
    void testIsSupportedFile() {
        def file = new File("src/test/resources/data/dicts/epwing/CATALOGS")
        def omegatEpwingDictionary = new OmegatEpwingDictionary();
        assertTrue(omegatEpwingDictionary.isSupportedFile(file))
    }

}
