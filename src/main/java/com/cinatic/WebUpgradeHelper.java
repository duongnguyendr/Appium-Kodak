package com.cinatic;

public class WebUpgradeHelper {
	private String localIP;
	private HttpHelper http;
	
	public WebUpgradeHelper(String localIP){
		this.localIP = localIP;
		http = new HttpHelper();
	}
	
	public String upload_sig_file(String filePath) {
		return http.uploadFW("http://" + this.localIP + ":8080/cgi-bin/haserlupgrade.cgi", filePath, "sig");
	}

	public String upload_tag_file(String filePath) {
		return http.uploadFW("http://" + this.localIP + ":8080/cgi-bin/haserlupgrade.cgi", filePath, "tar.gz");
	}
	
	public String upload_fw_file(String filePath) {
		return http.uploadFW("http://" + this.localIP + ":8080/cgi-bin/haserlupgrade.cgi", filePath, "fw.pkg2");
	}
	
	public String get_percent_fw_upgrade(){
		return http.httpGetRequest("http://" + this.localIP + ":8080/cgi-bin/fullupgrade");	
	}
}
