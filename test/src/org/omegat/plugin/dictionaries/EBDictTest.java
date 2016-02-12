/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2015,2016 Hiroshi Miura
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This file is part of OmegaT.

 OmegaT is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OmegaT is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **************************************************************************/

package org.omegat.plugin.dictionaries;

import java.io.File;
import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;
import org.omegat.core.dictionaries.DictionaryEntry;
import org.omegat.core.dictionaries.IDictionary;


/**
 *
 * @author Hiroshi Miura
 */
public class EBDictTest extends TestCase {

    private static final String dictPath = "test/data/dicts/epwing/CATALOGS";
    private static final String zippedDictPath = "test/data/dicts-zipped/epwing/CATALOGS";

    /**
     * Test of EBDict for IDictionaryFactory
     * @throws java.lang.Exception
     */
    @Test
    public void testEBDict() throws Exception {
        EBDict e = new EBDict();
        assertTrue(e.isSupportedFile(new File(dictPath)));
    }

    /**
     * Test of readArticle method
     * @throws java.lang.Exception
     */
    @Test
    public void testReadArticle() throws Exception {
        EBDict e = new EBDict();
        IDictionary dict = e.loadDict(new File(dictPath));
        String word = "Tokyo";
        List<DictionaryEntry> result = dict.readArticles(word);
        for (DictionaryEntry ent: result) {
            assertEquals("&nbsp;Tokyo<br>&nbsp;&nbsp;\u6771\u4eac<br>&nbsp;",
                   ent.getArticle());
        }
    }

    /**
     * Test of zipped dictionary
     * @throws java.lang.Exception
     */
    @Test
    public void testEBZipReadArticle() throws Exception {
        EBDict e = new EBDict();
        IDictionary dict = e.loadDict(new File(zippedDictPath));
        String word = "Tokyo";
        List<DictionaryEntry> result = dict.readArticles(word);
        for (DictionaryEntry ent: result) {
            assertEquals("&nbsp;Tokyo<br>&nbsp;&nbsp;\u6771\u4eac<br>&nbsp;",
                    ent.getArticle());
        }
    }
}
