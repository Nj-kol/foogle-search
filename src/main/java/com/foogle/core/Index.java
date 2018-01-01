package com.foogle.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents an in-memory index
 * 
 * @author Nilanjan Sarkar
 */
public class Index
{
  private Map< String, TokenInfo > index;
  
  public Index()
  {
    index = new ConcurrentHashMap< String, TokenInfo >();
  }

  public void addIndexEntry( String token, TokenInfo tk )
  {
    index.put( token, tk );
  }

  public TokenInfo getIndexEntry( String token )
  {
    return index.get( token );
  }
}