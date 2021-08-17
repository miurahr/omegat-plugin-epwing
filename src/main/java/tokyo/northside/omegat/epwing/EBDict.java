package tokyo.northside.omegat.epwing;

import io.github.eb4j.Book;
import io.github.eb4j.EBException;
import io.github.eb4j.ExtFont;
import io.github.eb4j.Result;
import io.github.eb4j.Searcher;
import io.github.eb4j.SubAppendix;
import io.github.eb4j.SubBook;
import io.github.eb4j.ext.UnicodeMap;
import io.github.eb4j.hook.Hook;
import io.github.eb4j.hook.HookAdapter;
import io.github.eb4j.util.HexUtil;
import org.apache.commons.lang.StringUtils;
import org.omegat.core.dictionaries.DictionaryEntry;
import org.omegat.core.dictionaries.IDictionary;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EBDict implements IDictionary {

    private final String eBookDirectory;
    private Book eBookDictionary;
    private final SubBook[] subBooks;
    private static final Logger LOG = Logger.getLogger(OmegatEpwingDictionary.class.getName());

    private static void logEBError(final EBException e) {
        switch (e.getErrorCode()) {
            case EBException.CANT_READ_DIR:
                LOG.log(Level.WARNING, "EPWING error: cannot read directory:" + e.getMessage());
                break;
            case EBException.DIR_NOT_FOUND:
                LOG.log(Level.WARNING, "EPWING error: cannot found directory:" + e.getMessage());
            default:
                LOG.log(Level.WARNING, "EPWING error: " + e.getMessage());
                break;
        }
    }

    public EBDict(final File catalogFile) throws Exception {
        eBookDirectory = catalogFile.getParent();
        try {
            eBookDictionary = new Book(eBookDirectory, eBookDirectory);
        } catch (EBException ignore) {
            // There may be no appendix
            try {
                eBookDictionary = new Book(eBookDirectory);
            } catch (EBException e) {
                logEBError(e);
                throw new Exception("EPWING: There is no supported dictionary");
            }
        }
        final int bookType = eBookDictionary.getBookType();
        if (bookType != Book.DISC_EPWING) {
            throw new Exception("EPWING: Invalid type of dictionary");
        }
        subBooks = eBookDictionary.getSubBooks();

    }

    /*
     * (non-Javadoc)
     *
     * @see org.omegat.core.dictionaries.IDictionary#readArticle(java.lang.
     * String, java.lang.Object)
     *
     * Returns not the raw text, but the formatted article ready for
     * upstream use (\n replaced with <br>, etc.
     */
    @Override
    public List<DictionaryEntry> readArticles(final String word) {
        Searcher sh;
        Result searchResult;
        Hook<String> hook;
        String article;
        List<DictionaryEntry> result = new ArrayList<>();

        for (SubBook sb : subBooks) {
            if (sb.hasExactwordSearch()) {
                try {
                    hook = new EBDictStringHook(sb);
                    sh = sb.searchExactword(word);
                    while ((searchResult = sh.getNextResult()) != null) {
                        article = searchResult.getText(hook);
                        result.add(new DictionaryEntry(word, article));
                    }
                } catch (EBException e) {
                    logEBError(e);
                }
            }
        }

        return result;
    }

    public static class EBDictStringHook extends HookAdapter<String> {

        private final int maxlines;
        private final StringBuffer output = new StringBuffer(16384);
        private int lineNum = 0;
        private boolean narrow = false;
        private int decType;
        private final SubAppendix subAppendix;
        private final ExtFont extFont;
        private final Base64.Encoder base64Encoder;
        private UnicodeMap unicodeMap;

        public EBDictStringHook(final SubBook sb) {
            this(sb, 500);
        }

        public EBDictStringHook(final SubBook sb, final int lines) {
            super();
            subAppendix = sb.getSubAppendix();
            extFont = sb.getFont();
            maxlines = lines;
            base64Encoder = Base64.getEncoder();
            String title = sb.getTitle();
            try {
                unicodeMap = new UnicodeMap(title, new File(sb.getBook().getPath()));
            } catch (EBException e) {
                unicodeMap = null;
                logEBError(e);
            }
        }

        /**
         * clear output line.
         */
        @Override
        public void clear() {
            output.delete(0, output.length());
            lineNum = 0;
        }

        /*
         * get result string.
         */
        @Override
        public String getObject() {
            return output.toString();
        }

        /*
         * Can accept more input?
         */
        @Override
        public boolean isMoreInput() {
            return lineNum < maxlines;
        }

        /**
         * append character.
         *
         * @param ch character
         */
        @Override
        public void append(final char ch) {
            append(Character.toString(ch));
        }

        /**
         * Append article text.
         *
         * @param text string to append
         */
        @Override
        public void append(final String text) {
            if (narrow) {
                output.append(convertZen2Han(text));
            } else {
                output.append(text);
            }
        }

        /**
         * Append GAIJI text(bitmap).
         *
         * @param code gaiji code referenced to bitmap griff image
         */
        @Override
        public void append(final int code) {
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
            output.append(str);
        }

        /**
         * begin roman alphabet.
         */
        @Override
        public void beginNarrow() {
            narrow = true;
        }

        /**
         * end roman alphabet.
         */
        @Override
        public void endNarrow() {
            narrow = false;
        }

        /**
         * begin subscript.
         */
        @Override
        public void beginSubscript() {
            output.append("<sub>");
        }

        /**
         * end subscript.
         */
        @Override
        public void endSubscript() {
            output.append("</sub>");
        }

        /**
         * begin super script.
         */
        @Override
        public void beginSuperscript() {
            output.append("<sup>");
        }

        /**
         * end super script.
         */
        @Override
        public void endSuperscript() {
            output.append("</sup>");
        }

        /**
         * set indent of line head.
         *
         * @param len size of indent
         */
        @Override
        public void setIndent(final int len) {
            for (int i = 0; i < len; i++) {
                output.append("&nbsp;");
            }
        }

        /**
         * insert new line.
         */
        @Override
        public void newLine() {
            output.append("<br>");
            lineNum++;
        }

        /**
         * set no break.
         */
        @Override
        public void beginNoNewLine() {
            // FIXME: implement me.
        }

        @Override
        public void endNoNewLine() {
            // FIXME
        }

        /**
         * insert em tag.
         */
        @Override
        public void beginEmphasis() {
            output.append("<em>");
        }

        @Override
        public void endEmphasis() {
            output.append("</em>");
        }

        /**
         * insert decoration.
         *
         * @param type decoration type #BOLD #ITALIC
         */
        @Override
        public void beginDecoration(final int type) {
            this.decType = type;
            switch (decType) {
                case BOLD:
                    output.append("<i>");
                    break;
                case ITALIC:
                    output.append("<b>");
                    break;
                default:
                    output.append("<u>");
                    break;
            }
        }

        @Override
        public void endDecoration() {
            switch (decType) {
                case BOLD:
                    output.append("</i>");
                    break;
                case ITALIC:
                    output.append("</b>");
                    break;
                default:
                    output.append("</u>");
                    break;
            }
        }

        @Override
        public void beginUnicode() {
        }

        @Override
        public void endUnicode() {
        }

        /**
         * Convert XBM image to lossless WebP and convert to Base64 String.
         *
         * @param data XBM data
         * @param height image height
         * @param width image width
         * @return String Base64 encoded BMP data.
         * @throws IOException when the image is broken or caused error.
         */
        public String convert2Image(final byte[] data, final int height, final int width) throws IOException {
            final BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int bitPos = 1 << ((x + y * height) & 0x07);
                    int pos = (x +  y * height) >> 3;
                    if ((data[pos] & bitPos) == 0) {
                        res.setRGB(x, y, Color.WHITE.getRGB());
                    } else {
                        res.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(res, "bmp", baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();
            return "<img src=\"data:image/bmp;base64," +
                    base64Encoder.encodeToString(bytes) +
                    "\"></img>";
        }

        /**
         * convert Zenkaku alphabet to Hankaku.
         *
         * convert (\uFF01 - \uFF5E) to (\u0021- \u007E) and \u3000 to \u0020
         *
         * @param text source text with zenkaku.
         * @return String converted
         */
        public String convertZen2Han(final String text) {
            StringBuilder result = new StringBuilder(text.length());
            for (int i = 0; i < text.length(); i++) {
                int cp = text.codePointAt(i);
                if (0xFF00 < cp && cp < 0xFF5F) {
                    result.append((char) (cp - 0xFEE0));
                } else if (cp == 0x3000) {
                    result.append("\u0020");
                } else {
                    result.appendCodePoint(cp);
                }
            }
            return result.toString();
        }
    }
}
