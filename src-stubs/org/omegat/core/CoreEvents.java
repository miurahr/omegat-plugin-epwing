/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2008 Alex Buloichik
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

package org.omegat.core;

import java.awt.Font;

import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IApplicationEventListener;
import org.omegat.core.events.IEditorEventListener;
import org.omegat.core.events.IEntryEventListener;
import org.omegat.core.events.IFontChangedEventListener;
import org.omegat.core.events.IProjectEventListener;

/**
 * stub class
 * 
 */
public class CoreEvents {

    /** Register listener. */
    public static void registerProjectChangeListener(final IProjectEventListener listener) {
    }

    /** Unregister listener. */
    public static void unregisterProjectChangeListener(final IProjectEventListener listener) {
    }

    /** Register listener. */
    public static void registerApplicationEventListener(final IApplicationEventListener listener) {
    }

    /** Unregister listener. */
    public static void unregisterApplicationEventListener(final IApplicationEventListener listener) {
    }

    /** Register listener. */
    public static void registerEntryEventListener(final IEntryEventListener listener) {
    }

    /** Unregister listener. */
    public static void unregisterEntryEventListener(final IEntryEventListener listener) {
    }

    /** Register listener. */
    public static void registerFontChangedEventListener(final IFontChangedEventListener listener) {
    }

    /** Unregister listener. */
    public static void unregisterFontChangedEventListener(final IFontChangedEventListener listener) {
    }

    /** Register listener. */
    public static void registerEditorEventListener(final IEditorEventListener listener) {
    }

    /** Unregister listener. */
    public static void unregisterEditorEventListener(final IEditorEventListener listener) {
    }

    /** Fire event. */
    public static void fireProjectChange(final IProjectEventListener.PROJECT_CHANGE_TYPE eventType) {
    }

    /** Fire event. */
    public static void fireApplicationStartup() {
    }

    /** Fire event. */
    public static void fireApplicationShutdown() {
    }

    /** Fire event. */
    public static void fireEntryNewFile(final String activeFileName) {
    }

    /** Fire event. */
    public static void fireEntryActivated(final SourceTextEntry newEntry) {
    }

    /** Fire event. */
    public static void fireFontChanged(final Font newFont) {
    }

    /** Fire event. */
    public static void fireEditorNewWord(final String newWord) {
    }
}
