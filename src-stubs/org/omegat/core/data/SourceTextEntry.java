/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2009-2013 Alex Buloichik
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

package org.omegat.core.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
public class SourceTextEntry {

    public enum DUPLICATE {
        /** There is no entries with the same source. */
        NONE,
        /** There is entries with the same source, and this is first entry. */
        FIRST,
        /** There is entries with the same source, and this is not first entry. */
        NEXT
    };

    /** 
     * A list of duplicates of this STE. Will be non-null for the FIRST duplicate,
     * null for NONE and NEXT STEs. See {@link #getDuplicate()} for full logic.
     */
    List<SourceTextEntry> duplicates;
    
    /**
     * The first duplicate of this STE. Will be null for NONE and FIRST STEs,
     * non-null for NEXT STEs. See {@link #getDuplicate()} for full logic.
     */
    SourceTextEntry firstInstance;

    public SourceTextEntry(EntryKey key, int entryNum, String comment, String sourceTranslation, List<ProtectedPart> protectedParts) {
    }

    public EntryKey getKey() {
        return null;
    }

    public String getSrcText() {
        return null;
    }

    public String getComment() {
        return null;
    }

    public int entryNum() {
        return 0;
    }

    public DUPLICATE getDuplicate() {
        return DUPLICATE.NEXT;
    }

    public int getNumberOfDuplicates() {
        return 0;
    }
    
    public List<SourceTextEntry> getDuplicates() {
       return Collections.emptyList();
    }
    
    public String getSourceTranslation() {
        return null;
    }

    public boolean isSourceTranslationFuzzy() {
        return false;
    }

    public void setSourceTranslationFuzzy(boolean sourceTranslationFuzzy) {
    }

    public ProtectedPart[] getProtectedParts() {
        return null;
    }
}
