/**
 * 
 */
package org.kuali.ext.mm.cart.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.kuali.ext.mm.cart.service.ShopCartSuggestionService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.sys.LuceneIndexingManager;


/**
 * @author schneppd
 *
 */
public class ShopCartSuggestionServiceImpl implements ShopCartSuggestionService {
    private static final Logger LOG = Logger.getLogger(ShopCartSuggestionServiceImpl.class);

    /**
     * @see org.kuali.ext.mm.cart.service.lucene.SearchSuggestionService#performSearch(java.lang.String, java.lang.String, java.lang.Integer)
     */
    public List<String> performSearch(String searchPrefx, String searchTerm, Integer resultsLimit) {
        StandardAnalyzer analyzer = LuceneIndexingManager.ANALYZER;
        Searcher searcherObj = LuceneIndexingManager.getSearcher();
        if (searcherObj == null || LuceneIndexingManager.isRefreshInProgress() || isBlank(searchTerm) || searchTerm.trim().length() < 3) {
            LOG
                    .warn("Search skipped because index building is in progress or search term didn't meet the criteria.");
            return new ArrayList<String>();
        }
        HashSet<String> list = new HashSet<String>();
        String formtted = prepareSearchTerm(searchTerm);
        LOG.debug("Search term is " + searchPrefx + ", " + formtted);
        try {
            BooleanQuery blq = new BooleanQuery();
            if (isNotBlank(formtted)) {
                Query query = new MultiFieldQueryParser(Version.LUCENE_30, new String[] {
                        MMConstants.LuceneIndexing.CATALOG_DESC, MMConstants.LuceneIndexing.DISTRIBUTOR_NBR }, analyzer).parse(formtted);
                blq.add(query, BooleanClause.Occur.MUST);
            }
            if (isNotBlank(searchPrefx)) {
                Query preQry = new TermQuery(new Term(MMConstants.LuceneIndexing.CATALOG_ID, searchPrefx));
                blq.add(preQry, BooleanClause.Occur.MUST);

            }
            TopDocs results = searcherObj.search(blq, resultsLimit);
            for (int i = 0; i < results.scoreDocs.length && i < resultsLimit; i++) {
                Document doc = searcherObj.doc(results.scoreDocs[i].doc);
                list.add(doc.get(MMConstants.LuceneIndexing.SAVED_DESC));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(list);
        return ret;
    }

    /**
     * @param formtted
     * @return
     */
    private boolean isNotBlank(String searchTerm) {
        return !isBlank(searchTerm);
    }

    /**
     * @param searchTerm
     * @return
     */
    private boolean isBlank(String searchTerm) {
        return searchTerm == null || searchTerm.trim().length() == 0;
    }

    /**
     * @param searchTerm
     */
    private String prepareSearchTerm(String searchTerm) {
        String cleanedTerm = handleSpecialChars(searchTerm);
        String[] terms = cleanedTerm.split(" ");
        String formtted = null;
        for (String term : terms) {
            if (term == null || term.trim().length() == 0
                    || StopAnalyzer.ENGLISH_STOP_WORDS_SET.contains(term.toLowerCase())) {
                continue;
            }
            if (formtted == null) {
                formtted = "+" + term + "* ";
            }
            else {
                formtted = formtted + " +" + term + "*";
            }
        }
        return formtted.trim();
    }

    /**
     * @param searchTerm
     * @return
     */
    protected String handleSpecialChars(String searchTerm) {
        int pos = 0;
        char[] tmp = new char[searchTerm.length() * 2];
        char[] chars = searchTerm.toCharArray();
        for (char c : chars) {
            if (c == '.' || c == ',' || c == '/' || c == '`' || c == '@' || c == '#' || c == '$'
                    || c == '%' || c == '_' || c == '=' || c == ';' || c == '<' || c == '>') {
                // ignore
                continue;
            }
            if (c == '+' || c == '-' || c == '&' || c == '|' || c == '!' || c == '(' || c == ')'
                    || c == '{' || c == '}' || c == '[' || c == ']' || c == '^' || c == '"'
                    || c == '~' || c == '*' || c == '?' || c == ':' || c == '\\') {
                tmp[pos++] = '\\';
            }
            tmp[pos++] = c;
        }
        String cleanedTerm = new String(tmp, 0, pos);
        return cleanedTerm;
    }
    
}
