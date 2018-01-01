package com.foogle.intfs;

import java.io.IOException;
import java.util.List;

import com.foogle.core.Index;
import com.foogle.core.Status;

/**
 * All implementations of this interface is responsible for creating an Index based on a
 * directory path
 * 
 * @author Nilanjan Sarkar
 *
 */
public interface Indexer {

	public List<Status> createIndex(String folderName) throws IOException;
	public Index getIndex();
}
