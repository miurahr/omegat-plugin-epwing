package tokyo.northside.omegat.epwing;

import io.github.eb4j.EBException;
import io.github.eb4j.SubAppendix;
import io.github.eb4j.SubBook;
import io.github.eb4j.ext.UnicodeMap;
import io.github.eb4j.util.HexUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;


public class AltCode {

    private final SubAppendix subAppendix;
    private UnicodeMap unicodeMap;

    public AltCode(final SubBook subBook) {
        String title = subBook.getTitle();
        subAppendix = subBook.getSubAppendix();
        try {
            unicodeMap = new UnicodeMap(title, new File(subBook.getBook().getPath()));
        } catch (EBException e) {
            unicodeMap = null;
        }
    }

    public String getAltCode(final int code, final boolean narrow) {
        String str = null;
        if (unicodeMap != null) {
            str = unicodeMap.get(code);
        }
        if (StringUtils.isBlank(str)) {
            if (narrow) {
                if (subAppendix != null) {
                    try {
                        str = subAppendix.getNarrowFontAlt(code);
                    } catch (EBException ignore) {
                    }
                }
                if (StringUtils.isBlank(str)) {
                    str = "[GAIJI=n" + HexUtil.toHexString(code) + "]";
                }
            } else {
                if (subAppendix != null) {
                    try {
                        str = subAppendix.getWideFontAlt(code);
                    } catch (EBException ignore) {
                    }
                }
                if (StringUtils.isBlank(str)) {
                    str = "[GAIJI=w" + HexUtil.toHexString(code) + "]";
                }
            }
        }
        return str;
    }
}
