package com.cinatic.object;

public class Event {
	private String event_id;
	private String event_type;
	private String device_id;			
	private String created_date;
	private String snapshot;
	private String file;
	private String file_type;
	private String file_created_date;
	private String file_size;
	
	public Event() {

	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	public String getCreated_date() {
		return created_date;
	}
	
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_created_date() {
		return file_created_date;
	}

	public void setFile_created_date(String file_created_date) {
		this.file_created_date = file_created_date;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
}
