package com.foogle.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class capture the meta-data of an individual token
 * 
 * @author Nilajan Sarkar
 */
public class TokenInfo
{
	private Map<String,FileEntry> fileData  = new ConcurrentHashMap<String,FileEntry>();
	private Integer totalOccurences;
	private String tokenName;
	
	public TokenInfo( String token )
	{
		this.tokenName = token;
		this.totalOccurences = 0;
	}

	public List<String> getLinesForFile(String fileName) {
		FileEntry en = fileData.get(fileName);
		return en.getLines();
	}

	public void recordFrequency(String fileName,String line)
	{
		totalOccurences = totalOccurences + 1;
		FileEntry en = fileData.get(fileName);
		if(null==en){
			en = new FileEntry();
			en.incrementFrequency();
			en.addLine(line);
			fileData.put(fileName, en);
		}else{
			en.incrementFrequency();
			en.addLine(line);
		}
	}

	public int getTotalFrequency()
	{
		return totalOccurences;
	}

	public Set<String> getFileNames()
	{
		return fileData.keySet();
	}

	public int getFrequencyInAFile( String fileName )
	{
		FileEntry en = fileData.get(fileName);
		return en.getFrequency();
	}

	public String getTokenName()
	{
		return this.tokenName;
	}

	private class FileEntry {

		private int frequency;
		private List<String> lines;

		public FileEntry(){
			frequency = 0;
			lines = new ArrayList<String>();
		}
		public int getFrequency() {
			return frequency;
		}
		public void incrementFrequency() {
			frequency = frequency+1;
		}
		public List<String> getLines() {
			return lines;
		}
		public void addLine(String line) {
			if(!lines.contains(line)){
				lines.add(line);
			}
		}
	}
}