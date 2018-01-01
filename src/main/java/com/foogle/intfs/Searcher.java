
package com.foogle.intfs;

import java.util.List;
import com.foogle.core.SearchResult;

/** This is the API for implementing a Searcher
 * 
 * @author Nilanjan Sarkar
 */
public interface Searcher
{
  public List< SearchResult > search( String word );
}
