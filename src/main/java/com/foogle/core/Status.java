package com.foogle.core;

/**
 * A value object encapsulating the status of
 * an operation
 * 
 * @author Nilanjan Sarkar
 *
 */
public class Status {

	private String failureReason;
	private ProcessStatus st;
	
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public ProcessStatus getProcessStatus() {
		return st;
	}
	public void setProcessStatus(ProcessStatus st) {
		this.st = st;
	}

}
