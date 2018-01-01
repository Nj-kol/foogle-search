package com.foogle.impls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import com.foogle.core.Index;
import com.foogle.core.ProcessStatus;
import com.foogle.core.SearchUtils;
import com.foogle.core.Status;
import com.foogle.core.TokenInfo;
import com.foogle.intfs.Indexer;

/**
 * This is the single threaded implementation of an Indexer.
 * Use this, if the number of files to index is small.
 * 
 * @author Nilanjan Sarkar
 *
 */
public class SingleThreadIndexer implements Indexer {

	private Index index;
	private List<Status> stats;

	public SingleThreadIndexer()
	{
		index = new Index();
		stats = new ArrayList<Status>();
	}

	@Override
	public List<Status> createIndex(String folderName) throws IOException
	{
		List<File> filesInFolder = Files.walk( Paths.get( folderName ) )
				.filter( Files::isRegularFile )
				.map( Path::toFile )
				.collect( Collectors.toList() );
		
		filesInFolder.forEach( f -> extract(f));
		return stats;
	}

	private void extract(File fileName)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			String line = br.readLine();
			while ( line != null )
			{
				tokenize( line, fileName.getAbsolutePath() );
				line = br.readLine();
			}
			Status status = new Status();
			status.setProcessStatus(ProcessStatus.SUCCESS);
			stats.add(status);
		}
		catch ( IOException e )
		{
			String cause = e.getMessage();
			Status status = new Status();
			status.setProcessStatus(ProcessStatus.ERROR);
			status.setFailureReason(cause);
			stats.add(status);
		}
	}

	private void tokenize( String line, String fileName )
	{
		StringTokenizer st = new StringTokenizer( line, " " );
		while ( st.hasMoreTokens() )
		{
			String token = st.nextToken();
			if (!SearchUtils.isBlank(token))
			{
				token = preProcessToken( token );
				TokenInfo tokeninfo = index.getIndexEntry( token );
				if ( tokeninfo == null )
				{
					tokeninfo = new TokenInfo( token );
					index.addIndexEntry( token, tokeninfo );
				}
				tokeninfo.recordFrequency( fileName,line );
			}

		}
	}

	private String preProcessToken(String token)
	{
		String result = token.trim()
				.toLowerCase()
				.replace( ".", "" );
		return result;
	}

	public Index getIndex()
	{
		return index;
	}
}
