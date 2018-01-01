package com.foogle.impls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.foogle.core.FrequencyComparator;
import com.foogle.core.Index;
import com.foogle.core.SearchResult;
import com.foogle.core.TokenInfo;
import com.foogle.intfs.Searcher;


/**
 * This implementation of Searcher orders the result
 * based on the term frequency in a document. The results
 * are returned in the order of descending frequency.
 * 
 * @author Nilanjan Sarkar
 */
public class FrequencyOrderedSearcher implements Searcher
{
  private Index index;

  public FrequencyOrderedSearcher( Index i )
  {
    this.index = i;
  }

  /**
   * Carries out the search on a specific index, and
   * returns a list of search results.
   * Returns an empty list if no result is found
   */
  @Override
  public List<SearchResult> search( String word )
  {
    String searchTerm = preProcessToken( word );
    List< SearchResult > res = new ArrayList< SearchResult >();
    TokenInfo ti = index.getIndexEntry(searchTerm);
    if ( null == ti )
    {
    	return res;
    }
    else
    {
      Set<String> result = ti.getFileNames();
      result.forEach( s -> 
      {
        SearchResult searchRes = new SearchResult();
        searchRes.setFileName( s );
        searchRes.setFrequency( ti.getFrequencyInAFile(s) );
        searchRes.setLines( ti.getLinesForFile(s) );
        res.add( searchRes );
      } );
    }
    Comparator<SearchResult> desc = Collections.reverseOrder(new FrequencyComparator());
    Collections.sort(res, desc);
    return res;
  }

  /**
   * Does some pre-processing on the token. Here we just trim
   * the white spaces and remove trailing '.'
   */
  private String preProcessToken( String token )
  {
    String result = token.trim()
                         .toLowerCase()
                         .replace( ".", "" );
    return result;
  }
}