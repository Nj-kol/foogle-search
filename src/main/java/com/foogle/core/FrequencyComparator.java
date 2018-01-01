package com.foogle.core;

import java.util.Comparator;

/**
 * 
 * @author Nilanjan Sarkar
 *
 */
public class FrequencyComparator implements Comparator<SearchResult> {

	@Override
	public int compare(SearchResult o1, SearchResult o2) {
		return ( o1.getFrequency() - o2.getFrequency() );
	}
}
