/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2012 Guido Leenders, Didier Briel
               2013 Aaron Madlon-Kay
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 **************************************************************************/

package org.omegat.core.data;

/**
 * This class is a stub to allow compilation.
 * It is based on the original OmegaT class.
 * It is not distributed.
 */
public class ProjectProperties {

	/** Returns The Glossary Files Directory */
	public String getGlossaryRoot () {
		return null;
	}

	/** Returns The Translation Memory (TMX) Files Directory */
	public String getTMRoot () {
		return null;
	}

	/**
	 * Returns The Translation Memory (TMX) Files Directory for automatically applied files.
	 */
	public String getTMAutoRoot () {
		return null;
	}

    /** Returns The Source (to be translated) Files Directory */
    public String getSourceRoot() {
        return null;
    }
	
}
