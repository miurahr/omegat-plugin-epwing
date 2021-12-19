package tokyo.northside.omegat.epwing;

import io.github.eb4j.Book;
import io.github.eb4j.EBException;
import io.github.eb4j.Result;
import io.github.eb4j.Searcher;
import io.github.eb4j.SubBook;
import io.github.eb4j.hook.Hook;
import org.omegat.core.dictionaries.DictionaryEntry;
import org.omegat.core.dictionaries.IDictionary;
import org.omegat.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EBDict implements IDictionary {

    private final SubBook[] subBooks;

    public EBDict(final File catalogFile) throws Exception {
        Book eBookDictionary;
        String eBookDirectory = catalogFile.getParent();
        String appendixDirectory;
        if (new File(eBookDirectory, "appendix").isDirectory()) {
            appendixDirectory = new File(eBookDirectory, "appendix").getPath();
        } else {
            appendixDirectory = eBookDirectory;
        }
        try {
            // try dictionary and appendix first.
            eBookDictionary = new Book(eBookDirectory, appendixDirectory);
            Log.log("Load dictionary with appendix.");
        } catch (EBException ignore) {
            // There may be no appendix, try again with dictionary only.
            try {
                eBookDictionary = new Book(eBookDirectory);
            } catch (EBException e) {
                Utils.logEBError(e);
                throw new Exception("EPWING: There is no supported dictionary");
            }
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
        String heading;
        String article;
        Set<String> headings = new HashSet<>();
        List<DictionaryEntry> result = new ArrayList<>();

        for (SubBook sb : subBooks) {
            try {
                hook = new EBDictStringHook(sb);
                if (sb.hasExactwordSearch()) {
                    sh = sb.searchExactword(word);
                } else {
                    continue;
                }
                while ((searchResult = sh.getNextResult()) != null) {
                    heading = searchResult.getHeading(hook);
                    if (headings.contains(heading)) {
                        continue;
                    }
                    headings.add(heading);
                    article = searchResult.getText(hook);
                    result.add(new DictionaryEntry(heading, article));
                }
            } catch (EBException e) {
                Utils.logEBError(e);
            }
        }
        return result;
    }

    /**
     * Read article's text. Matching is predictive, so e.g. supplying "term"
     * will return articles for "term", "terminology", "termite", etc. The
     * default implementation simply calls {@link #readArticles(String)} for
     * backwards compatibility.
     *
     * @param word The word to look up in the dictionary
     * @return List of entries. May be empty, but cannot be null.
     */
    @Override
    public List<DictionaryEntry> readArticlesPredictive(final String word) throws Exception {
        Searcher sh;
        Result searchResult;
        Hook<String> hook;
        String heading;
        String article;
        Set<String> headings = new HashSet<>();
        List<DictionaryEntry> result = new ArrayList<>();

        for (SubBook sb : subBooks) {
            try {
                hook = new EBDictStringHook(sb);
                if (sb.hasWordSearch()) {
                    sh = sb.searchWord(word);
                } else {
                    continue;
                }
                while ((searchResult = sh.getNextResult()) != null) {
                    heading = searchResult.getHeading(hook);
                    if (headings.contains(heading)) {
                        continue;
                    }
                    headings.add(heading);
                    article = searchResult.getText(hook);
                    result.add(new DictionaryEntry(heading, article));
                }
            } catch (EBException e) {
                Utils.logEBError(e);
            }
        }
        return result;
    }
}
