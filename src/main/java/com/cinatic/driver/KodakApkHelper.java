package com.cinatic.driver;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class KodakApkHelper {
	private static String apkName = "KodakSmartHome.apk";
	private static String apkBuildPrefix = "Kodak_V";
	private static String searchLocation=System.getenv("CINATIC_AUTOMATION_RES") + File.separatorChar + "app";
	
	public static String getLatestApkVerByName() {
		File apkLocation = new File(searchLocation);
		FilenameFilter filterKodak = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.contains(apkBuildPrefix)) {
					return true;
				}
				return false;
			}
		};
		String[] apkFiles = apkLocation.list(filterKodak);		
		Arrays.sort(apkFiles);
		
		return searchLocation + File.separatorChar + apkFiles[apkFiles.length-1];
	}
	
	public String getApkName() {
		return apkName;
	}
}
