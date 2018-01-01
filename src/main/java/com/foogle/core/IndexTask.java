package com.foogle.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

public class IndexTask implements Callable<Status> {

	private Index index;
	private File fileName;

	public IndexTask(Index index,File fileName){
		this.index=index;
		this.fileName=fileName;
	}

	@Override
	public Status call() throws Exception {
		Status status= new Status();
		try{
			indexFile(fileName);
			status.setProcessStatus(ProcessStatus.SUCCESS);
		}catch(IOException e){
			String cause = e.getMessage();
			status.setProcessStatus(ProcessStatus.ERROR);
			status.setFailureReason(cause);
		}
		return status;
	}

	private void indexFile( File fileName ) throws FileNotFoundException, IOException
	{
		try (BufferedReader br = new BufferedReader( new FileReader( fileName ) ))
		{
			String line = br.readLine();
			while ( line != null )
			{
				tokenize(line,fileName.getAbsolutePath() );
				line = br.readLine();
			}
		}
	}

	private void tokenize( String line, String fileName )
	{
		StringTokenizer st = new StringTokenizer( line, " " );
		while ( st.hasMoreTokens() )
		{
			String token = st.nextToken();
			if ( token != null || !"".equals( token ) )
			{
				token = preProcessToken( token );
			}
			TokenInfo tokeninfo = index.getIndexEntry( token );
			if ( tokeninfo == null )
			{
				tokeninfo = new TokenInfo( token );
				index.addIndexEntry( token, tokeninfo );
			}
			tokeninfo.recordFrequency( fileName,line );
		}
	}

	private String preProcessToken( String token )
	{
		String result = token.trim()
				.toLowerCase()
				.replace( ".", "" );
		return result;
	}
}
