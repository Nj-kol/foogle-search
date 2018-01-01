package com.foogle.impls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import com.foogle.core.Index;
import com.foogle.core.IndexTask;
import com.foogle.core.ProcessStatus;
import com.foogle.core.Status;
import com.foogle.intfs.Indexer;

/**
 * This is the multi-threaded implementation of an Indexer.
 * Use this if the number of files to index is substantially large. For small 
 * number of files, the overhead of creating and maintaining a thread pool outweighs
 * the actual indexing time.
 * 
 * @author Nilanjan Sarkar
 *
 */
public class MultiThreadedIndexer implements Indexer {

	private Index index;
	private ExecutorService executorService;

	public MultiThreadedIndexer(ExecutorService executorService)
	{
		index = new Index();
		this.executorService = executorService;
	}

	@Override
	public List<Status> createIndex(String folderName) throws IOException 
	{

		List<Status> stats = Files.walk( Paths.get( folderName ) )
				.filter( Files::isRegularFile )
				.map( Path::toFile )
				.map(f -> executorService.submit(new IndexTask(index,f)))
                .map(f -> {
                	Status st =null;
                try{
                	st = f.get();
                }catch(InterruptedException| ExecutionException e){
                	st = new Status();
                	st.setFailureReason(e.getMessage());
                	st.setProcessStatus(ProcessStatus.ERROR);
                }
                return st;
                })
                .collect(Collectors.toList());
		return stats;
	}

	@Override
	public Index getIndex()
	{
		return index;
	}
}