package com.cinatic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.cedarsoftware.util.io.JsonWriter;

public class HttpHelper {			
	private String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private String CONTENT_LANGUAGE = "en-US";
	
	public String httpGetRequest(String link) {
		HttpURLConnection connection = null;
		try {
			System.out.println(String.format("Send GET request with URL '%s'", link));
			// Create connection
			URL url = new URL(link);
			connection = (HttpURLConnection) url.openConnection();						
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", CONTENT_TYPE);
			connection.setRequestProperty("Content-Language", CONTENT_LANGUAGE);
			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Get Response
			InputStream is = (InputStream) connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			try{				
				System.out.println(String.format("Get response: \n%s", JsonWriter.formatJson(response.toString())));
			}
			catch(Exception e){
				System.out.println(String.format("Get response: \n%s", response.toString()));
			}
			
			return response.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public String[] httpPostRequest(String link, String urlParameters) {
		HttpURLConnection connection = null;
		String output[] = new String[2];
		try {
			System.out.println(String.format("Send POST request with URL '%s' with parameters '%s'", link, urlParameters));
			// Create connection
			URL url = new URL(link);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			if (urlParameters.contains("{")) {
				connection.setRequestProperty("Content-Type", "application/json");
			} else {
				connection.setRequestProperty("Content-Type", CONTENT_TYPE);
			}
			connection.setRequestProperty("Content-Language", CONTENT_LANGUAGE);
			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			// Get Response
			InputStream is = (InputStream) connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			// Get Header
			StringBuilder header = new StringBuilder();
			Map<String, List<String>> map = connection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				header.append(entry.getKey() + " : " + entry.getValue());
				header.append('\r');
			}

			System.out.println(String.format("Get Header: \n%s", header.toString()));
			try{				
				System.out.println(String.format("Get response: \n%s", JsonWriter.formatJson(response.toString())));
			}
			catch(Exception e){
				System.out.println(String.format("Get response: \n%s", response.toString()));
			}

			output[0] = header.toString();
			output[1] = response.toString();

			return output;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			output[1] = e.getMessage();
			return output;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}	
	
	public String uploadFW(String link, String filePath, String fileExtendsion) {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		File file = new File(filePath);
		String fileName = file.getName();
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String boundary = "*****";
		String twoHyphens = "--";
		String lineEnd = "\r\n";
		int serverResponseCode = 0;

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 2 * 1024 * 1024;

		System.out.println(String.format("Start upload: %s", fileName));
		try {
			URL url = new URL(link);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);

			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("ENCTYPE", "multipart/form-data");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(twoHyphens + boundary + lineEnd);
			out.writeBytes("Content-Disposition: form-data; file=\"" + filePath + "\"" + lineEnd + lineEnd);

			out.writeBytes(twoHyphens + boundary + lineEnd);
			out.writeBytes("Content-Disposition: form-data; submit=\"Upload\"" + lineEnd + lineEnd);

			out.writeBytes(twoHyphens + boundary + lineEnd);
			out.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\"" + fileName + "\"" + lineEnd);
			out.writeBytes("Content-Type: " + fileExtendsion + lineEnd);

			out.writeBytes(lineEnd);

			// create a buffer of maximum size
			bytesAvailable = inputStream.available();

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = inputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				out.write(buffer, 0, bufferSize);
				bytesAvailable = inputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = inputStream.read(buffer, 0, bufferSize);
			}

			// send multipart form data necesssary after file data...
			out.writeBytes(lineEnd);
			out.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = connection.getResponseCode();
			String response = connection.getResponseMessage();

			if (serverResponseCode == 200) {
				System.out.println(String.format("Upload file '%s' successfully", fileName));
			} else {
				System.out.println(String.format("Upload file '%s' failed", fileName));
				
				BufferedReader errorReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getErrorStream())));				
				System.out.println(errorReader.readLine());
			}
			System.out.println(String.format("Get response: \n%s", response));
			return response;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
