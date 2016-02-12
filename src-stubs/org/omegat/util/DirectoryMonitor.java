/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2009-2014 Alex Buloichik
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

package org.omegat.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * Stub class 
 * 
 */
public class DirectoryMonitor extends Thread {
    public DirectoryMonitor(final File dir, final Callback callback) {
    }

    public DirectoryMonitor(final File dir, final Callback callback, final DirectoryCallback directoryCallback) {
    }
    
    public File getDir() {
        return null;
    }

    public void fin() {
    }

    @Override
    public void run() {
    }

    public synchronized Set<File> getExistFiles() {
        return null;
    }

    public synchronized void checkChanges() {
    }

    public interface Callback {
        /**
         * Called on any file changes - created, modified, deleted.
         */
        void fileChanged(File file);
    }
    
    public interface DirectoryCallback {
        /**
         * Called once for every directory where a file was changed - created, modified, deleted.
         */
        void directoryChanged(File file);
    }
}
