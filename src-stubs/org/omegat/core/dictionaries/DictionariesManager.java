/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2009 Alex Buloichik
               2011 Didier Briel
               2015 Aaron Madlon-Kay
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

package org.omegat.core.dictionaries;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.omegat.gui.dictionaries.IDictionaries;
import org.omegat.util.DirectoryMonitor;

/**
 * Stub class
 * 
 */
public class DictionariesManager implements DirectoryMonitor.Callback {
    public static final String IGNORE_FILE = "ignore.txt";
    public static final String DICTIONARY_SUBDIR = "dictionary";

    protected final List<IDictionaryFactory> factories = new ArrayList<IDictionaryFactory>();
    protected final Map<String, IDictionary> dictionaries = new TreeMap<String, IDictionary>();

    public DictionariesManager(final IDictionaries pane) {
    }

    public void addDictionaryFactory(IDictionaryFactory dict) {
    }

    public void removeDictionaryFactory(IDictionaryFactory factory) {
    }

    public void start(File dictDir) {
    }

    public void stop() {
    }

    public void fileChanged(File file) {
    }

    public void addIgnoreWord(final String word) {
    }
    
    public List<DictionaryEntry> findWords(Collection<String> words) {
        return null;
    }
}
