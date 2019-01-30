package com.cinatic.object;

public class CodeMessage {
	private int code;
	private String messsage;
	private String detail;
	private String starttime;
	private long duration;
	private String summary;
	
	public CodeMessage(int code, String message, String detail){
		this.setCode(code);
		this.setMesssage(message);
		this.setDetail(detail);
	}
	
	public CodeMessage(int code, String message, String summary, String detail){
		this.setCode(code);
		this.setMesssage(message);
		this.setSummary(summary);
		this.setDetail(detail);
	}
	
	public CodeMessage(int code, String message, String detail, long duration, String starttime){
		this.setCode(code);
		this.setMesssage(message);
		this.setDetail(detail);
		this.setStarttime(starttime);
		this.setDuration(duration);		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
