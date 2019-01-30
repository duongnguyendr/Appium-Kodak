package com.cinatic;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cinatic.log.Log;

public class StringHelper {
	private static Logger log = LoggerFactory.getLogger(StringHelper.class);
	
	public static String cleanInvalidCharacters(String in) {
		StringBuilder out = new StringBuilder();
		char current;
		if (in == null || ("".equals(in))) {
			return "";
		}
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF)) || ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF))) {
				out.append(current);
			}

		}
		return out.toString().replaceAll("\\s", " ");
	}

	public static String randomString(String prefix, int len) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return prefix + sb.toString();
	}
	
	public static String randomNumber(String prefix, int len) {
		String AB = "012345678901234567890123456789012345678901234567890123456789";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return prefix + sb.toString();
	}

	public static String getStringByString(String input, String start, String end, boolean last) {
		int indexStart = 0;
		if (last)
			indexStart = input.lastIndexOf(start) + start.length();
		else
			indexStart = input.indexOf(start) + start.length();
		int indexEnd = input.indexOf(end, indexStart);
		return input.substring(indexStart, indexEnd);
	}
	
	public static String subStringByString(String input, String start, String end) {		
		int beginIndex = input.indexOf(start);
		int endIndex = input.indexOf(end, beginIndex);		
		return input.substring(beginIndex, endIndex);
	}
	
	public static float getBitRateAverage(String input){	
		Pattern p = Pattern.compile("((?!bitrate= ))\\d+\\.\\d+\\w+\\/.");
		java.util.regex.Matcher m = p.matcher(input);
		String bitrates = "";
		while (m.find()) {
			bitrates += m.group() + " ";
		}
				
		log.info(bitrates);
		p = Pattern.compile("\\d+\\.\\d+");
		m = p.matcher(bitrates);
		float total = 0;
		int count = 0;
		while (m.find()) {
			total += Float.parseFloat(m.group());
			count++;
		}
		return total/count;
	}
	
	public static int getP2PDuration(String input){	
		Pattern p = Pattern.compile("time \\d+");
		java.util.regex.Matcher m = p.matcher(input);
		String times = "";
		while (m.find()) {
			times += m.group() + " ";
		}
				
		log.info(times);
		p = Pattern.compile("\\d+");
		m = p.matcher(times);
		
		List<Integer> intList = new ArrayList<Integer>();
		while (m.find()) {					
			intList.add(Integer.parseInt(m.group()));
			System.out.println(m.group());
		}
		return intList.get(intList.size()-1) - intList.get(0);
	}
	
	public static String getSetupMode(String input){	
		Pattern p = Pattern.compile("\\<Setup.*session\\>");
		java.util.regex.Matcher m = p.matcher(input);
		String output = "";
		while (m.find()) {
			output = m.group();
		}				
		System.out.println(output);
		return output;
	}
	
	public static String getCamIp(String input)
	{
		Pattern p = Pattern.compile("inet addr:\\d+.\\d+.\\d+.\\d+");
		java.util.regex.Matcher m = p.matcher(input);
		String output = "";
		if (m.find()) {
			output += m.group();
		}
				
		return output.replace("inet addr:", "");
	}
	
	public static String getVersion(String input)
	{
		Pattern p = Pattern.compile("\\d+.\\d+.\\d+");
		java.util.regex.Matcher m = p.matcher(input);
		String output = "";
		if (m.find()) {
			output += m.group();
		}
				
		return output;
	}
	
	public static String getCurrentTimeStamp(){
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());		
	}
	
	public static String getCurrentTimeStamp(String format){
		return new SimpleDateFormat(format).format(new Date());		
	}
	
	public static String getTimeStamp(String format, int day){
		return new SimpleDateFormat(format).format(new Date((new Date()).getTime() + (day * 1000 * 60 * 60 * 24)));		
	}
	
	public static String getCurrentDateTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());		
	}
	
	public static String getCurrentDateTime(String format){
		return new SimpleDateFormat(format).format(new Date());		
	}
	
	public static long getDuration(String format, String dateStart, String dateEnd) throws ParseException{
		try{
			DateFormat f = new SimpleDateFormat(format);
			Date date1 = f.parse(dateStart);
			Date date2 = f.parse(dateEnd);		
			return (date2.getTime() - date1.getTime());
		}
		catch(Exception ex)
		{
			return 0;
		}		
	}
	
	public static long getDuration(String dateStart, String dateEnd) throws ParseException{
		try{
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date1 = f.parse(dateStart);
			Date date2 = f.parse(dateEnd);		
			return (date2.getTime() - date1.getTime());
		}
		catch(Exception ex)
		{
			return 0;
		}		
	}
	
	public static int convertTimeToSeconds(String time) {
		int hour = 0;
		int minute = 0;
		int second = 0;
		try {
			hour = Integer.parseInt(time.split(":")[0]);
			minute = Integer.parseInt(time.split(":")[1]);
			second = Integer.parseInt(time.split(":")[2]);
		} catch (Exception ex) {
			return 0;
		}
		return hour * 360 + minute * 60 + second;
	}
	
	public static String getSystemPath(){
		String path = "test-output/surefire-reports";
		if (System.getProperty("suiteOutput") != null) {
			path = System.getProperty("suiteOutput");
		}
		return path;
	}
	
	public static String getBuildPath(){
		String path = "test-output/surefire-reports/html/";
		if (System.getProperty("suiteBuildUrl") != null) {
			path = System.getProperty("suiteBuildUrl") + "HTML_20Report/";
		}
		return path;
	}
	public static String stringToHTMLString(String string) {
		 StringBuilder escapedTxt = new StringBuilder();
		    for (int i = 0; i < string.length(); i++) {
		        char tmp = string.charAt(i);
		        switch (tmp) {
		        case '<':
		            escapedTxt.append("&lt;");
		            break;
		        case '>':
		            escapedTxt.append("&gt;");
		            break;
		        case '&':
		            escapedTxt.append("&amp;");
		            break;
		        case '"':
		            escapedTxt.append("&quot;");
		            break;
		        case '\'':
		            escapedTxt.append("&#x27;");
		            break;
		        case '/':
		            escapedTxt.append("&#x2F;");
		            break;
		        default:
		            escapedTxt.append(tmp);
		        }
		    }
		    return escapedTxt.toString();
	}
	
	public static List<String> getNumberInString(String value) {
		Log.info("Get numbers in: " + value);
		Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+?");
        List<String> numbers = new ArrayList<String>();
        Matcher m = p.matcher(value);
        while (m.find()) {  
            numbers.add(m.group());
        }   
        return numbers;
	}
}