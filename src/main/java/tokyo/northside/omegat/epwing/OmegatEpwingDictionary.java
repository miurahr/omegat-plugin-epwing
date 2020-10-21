/**************************************************************************
EPWING dictionary access plugin for OmegaT CAT tool(http://www.omegat.org/)

 Copyright (C) 2015,2016,2020 Hiroshi Miura

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

package tokyo.northside.omegat.epwing;

import java.io.File;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.dictionaries.IDictionary;
import org.omegat.core.dictionaries.IDictionaryFactory;
import org.omegat.core.events.IApplicationEventListener;

/**
 * EPWING dictionary access class
 *
 * @author Hiroshi Miura
 */
public class OmegatEpwingDictionary implements IDictionaryFactory {

    public static void loadPlugins() {
        CoreEvents.registerApplicationEventListener(new EBDictApplicationEventListener());
    }

    public static void unloadPlugins() {
    }

    @Override
    public boolean isSupportedFile(File file) {
        return file.getPath().toUpperCase().endsWith("CATALOGS");
    }

    @Override
    public IDictionary loadDict(File file) throws Exception {
        return new EBDict(file);
    }

    static class EBDictApplicationEventListener implements IApplicationEventListener {
        @Override
        public void onApplicationStartup() {
            Core.getDictionaries().addDictionaryFactory(new OmegatEpwingDictionary());
        }

        @Override
        public void onApplicationShutdown() {
        }
    }

}
