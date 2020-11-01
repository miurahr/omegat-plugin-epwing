package tokyo.northside.omegat.epwing;

import io.github.eb4j.Book;
import io.github.eb4j.EBException;
import io.github.eb4j.Result;
import io.github.eb4j.Searcher;
import io.github.eb4j.SubBook;
import io.github.eb4j.hook.Hook;
import io.github.eb4j.hook.HookAdapter;
import org.omegat.core.dictionaries.DictionaryEntry;
import org.omegat.core.dictionaries.IDictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


class EBDict implements IDictionary {

    private String eBookDirectory;
    private Book eBookDictionary;
    private SubBook[] subBooks;
    private static final Logger LOG = Logger.getLogger(OmegatEpwingDictionary.class.getName());

    private static void logEBError(EBException e) {
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

    public EBDict(File catalogFile) throws Exception {
        eBookDirectory = catalogFile.getParent();
        try {
            eBookDictionary = new Book(eBookDirectory);
        } catch (EBException e) {
            logEBError(e);
            throw new Exception("EPWING: There is no supported dictionary");
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
    public List<DictionaryEntry> readArticles(String word) {
        Searcher sh;
        Result searchResult;
        Hook<String> hook;
        String article;
        List<DictionaryEntry> result = new ArrayList<>();

        for (SubBook sb : subBooks) {
            if (sb.hasExactwordSearch()) {
                try {
                    hook = new EBDictStringHook();
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

        private static final int MAX_LINES = 20;
        private StringBuffer output = new StringBuffer(2048);
        private int lineNum = 0;
        private boolean narrow = false;
        private int decType;

        public EBDictStringHook() {
            super();
        }

        /**
         * clear output line
         */
        @Override
        public void clear() {
            output.delete(0, output.length());
            lineNum = 0;
        }

        /*
         * get result string
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
            if (lineNum >= MAX_LINES) {
                return false;
            }
            return true;
        }

        /**
         * Append article text.
         *
         * @param text
         */
        @Override
        public void append(String text) {
            if (narrow) {
                output.append(convertZen2Han(text));
            } else {
                output.append(text);
            }
        }

        /**
         * Append GAIJI text(bitmap)
         *
         * @param code gaiji code referenced to bitmap gliff image
         */
        @Override
        public void append(int code) {
            // FIXME: implement me.
        }

        /**
         * begin roman alphabet
         */
        @Override
        public void beginNarrow() {
            narrow = true;
        }

        /**
         * end roman alphabet
         */
        @Override
        public void endNarrow() {
            narrow = false;
        }

        /**
         * begin subscript
         */
        @Override
        public void beginSubscript() {
            output.append("<sub>");
        }

        /**
         * end subscript
         */
        @Override
        public void endSubscript() {
            output.append("</sub>");
        }

        /**
         * begin super script
         */
        @Override
        public void beginSuperscript() {
            output.append("<sup>");
        }

        /**
         * end super script
         */
        @Override
        public void endSuperscript() {
            output.append("</sup>");
        }

        /**
         * set indent of line head
         *
         * @param len
         */
        @Override
        public void setIndent(int len) {
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
         * set no break
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
         * insert em tag
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
         * insert decoretion
         *
         * @param type decoration type #BOLD #ITALIC
         */
        @Override
        public void beginDecoration(int type) {
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

        /**
         * convert Zenkaku alphabet to Hankaku
         *
         * convert (\uFF01 - \uFF5E) to (\u0021- \u007E) and \u3000 to \u0020
         *
         * @param text
         * @return String converted
         */
        public String convertZen2Han(String text) {
            StringBuilder result = new StringBuilder(text.length());
            for (int i = 0; i < text.length(); i++) {
                int cp = text.codePointAt(i);
                if (0xFF00 < cp && cp < 0xFF5F) {
                    result.append((char) (cp - 0xFEE0));
                } else if (cp == 0x3000) {
                    result.append("\u0020");
                } else {
                    result.append(cp);
                }
            }
            return result.toString();
        }
    }
}
