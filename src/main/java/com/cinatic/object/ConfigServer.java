package com.cinatic.object;

public class ConfigServer {
	public String server_url;
	public String device_id;
	
	public ConfigServer(String server) {
		if(server.equals("hk")) {
			server_url = "abc";
			device_id = "aaa";
		}
	}
}
