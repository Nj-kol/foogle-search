package com.foogle.core;

import java.util.List;

/** 
 *  This class contains the results of the search and 
 *  other meta-data
 *  
 * @author Nilanjan Sarkar
 */
public class SearchResult
{
	private String fileName;
	private List<String> lines;
	private int frequency;

	public String getFileName()
	{
		return this.fileName;
	}

	public void setFileName( String fileName )
	{
		this.fileName = fileName;
	}

	public List< String > getLines()
	{
		return this.lines;
	}

	public void setLines( List< String > lines )
	{
		this.lines = lines;
	}

	public int getFrequency()
	{
		return this.frequency;
	}

	public void setFrequency( int frequency )
	{
		this.frequency = frequency;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "===============================================================================" );
		sb.append( "\n" );
		sb.append( "\n" );
		sb.append( "File Name : " + getFileName() );
		sb.append( "\n" );
		sb.append( "\n" );
		sb.append( "Number of occurences in this file : " + getFrequency() );
		sb.append( "\n" );
		sb.append( "\n" );
		sb.append( "Lines containing this word : " );
		sb.append( "\n" );
		sb.append( "\n" );
		lines.forEach(s -> {sb.append(s); sb.append( "\n" );});
		sb.append( "\n" );
		sb.append( "===============================================================================" );
		return sb.toString();
	}
}