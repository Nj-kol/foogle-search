package com.foogle.main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.foogle.core.Index;
import com.foogle.core.ProcessStatus;
import com.foogle.core.SearchResult;
import com.foogle.core.Status;
import com.foogle.impls.FrequencyOrderedSearcher;
import com.foogle.impls.MultiThreadedIndexer;
import com.foogle.intfs.Indexer;
import com.foogle.intfs.Searcher;

/**
 * This is the client program
 * 
 * @author Nilanjan Sarkar
 */
public class SearchClient
{
	private static Index index;
	private static ExecutorService executorService;
	private static Scanner reader;

	public static void main( String args[] )
	{
		executorService = Executors.newCachedThreadPool();
		reader = new Scanner(System.in);
		greetUser();
		try
		{
			System.out.println( "Please enter the directory you want to index : " +"\n");
			createIndex(reader.nextLine());
			String moreSearch =null;
			do{
				System.out.println( "Enter search term : " +"\n");
				doSearch(reader.nextLine());
				System.out.println("\n");
				System.out.println( "Do you want to do another search ? Enter 'y' or 'yes' ");
				moreSearch = reader.nextLine();
			}
			while("y".equalsIgnoreCase(moreSearch) || "yes".equalsIgnoreCase(moreSearch));
		}
		catch(Exception e)
		{
			System.out.println("Ooops! Something went wrong, please try again later :( ");
		}finally
		{
			reader.close();
			executorService.shutdown();
		}
		System.out.println("");
		System.out.println("\t\tGoodbye ! Thank you for using Foogle. Have a nice day :) ");
	}

	private static void greetUser()
	{
		System.out.println( " \n");
		System.out.println( " \n");
		System.out.println( "\t\t\t\tWelcome to Foogle");
		System.out.println( " \n");
	}	

	/**
	 * Create an index for a folder. An Indexer is
	 * responsible for creating an Index.
	 */
	private static void createIndex(String dirPath)
	{
		try
		{
			Indexer indexer = new MultiThreadedIndexer(executorService);
			long startTime = System.currentTimeMillis();	
			List<Status> stats= indexer.createIndex(dirPath);
			long endTime = System.currentTimeMillis();
			System.out.println("");
			System.out.println("Indexing completed, indexing took " + (( endTime - startTime)/1000.0 ) + " seconds");
			long success = stats.stream().filter(s-> s.getProcessStatus()==ProcessStatus.SUCCESS).count();
			long failed = stats.stream().filter(s-> s.getProcessStatus()==ProcessStatus.ERROR).count();
			System.out.println("No. of files succesfully processed = "+ success);
			System.out.println("No. of files unsuccesfully processed = "+ failed);
			System.out.println("\n");
			index = indexer.getIndex();
		}
		catch(IOException e)
		{
			System.out.println("");
			System.out.println("The directory provided does not exist, or does not have read permission. Please try again. ");
			System.out.println("Please enter the directory you want to index : " +"\n");
			dirPath= reader.nextLine();
			createIndex(dirPath);
		}	
	}

	/**
	 * Carries out search on a particular index
	 * 
	 * @param term
	 */
	private static void doSearch(String term) 
	{
		//A Searcher searches on a specific index
		Searcher searcher = new FrequencyOrderedSearcher(index);
		long startTime = System.currentTimeMillis();	
		List<SearchResult> results = searcher.search(term);
		long endTime = System.currentTimeMillis();
		System.out.println("");
		System.out.println( "Search took " + (( endTime - startTime)/1000.0 ) + " seconds,"+" total "+ results.size() +" result(s) found."+"\n");
		System.out.println( "Results : "+"\n" );
		results.forEach( s -> System.out.println( s.toString() +"\n") );
	}
}