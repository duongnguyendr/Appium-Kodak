package Android_Test_Kodak.android.kodak.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;
import com.cinatic.StringHelper;
import com.cinatic.log.Log;


// NOTE: only support one xml suite for now
public class KodakCustomizedHTMLReporter extends HTMLReporter{
	String htmlpath;
	String htmlResult ;
	String htmlResultMod ;
	File screenshotsFolder;
	ArrayList<String> screenshots;
	String htmlReportUrl;

	public void prepareReportData() {
		String suitOutput = System.getProperty("suiteOutput");
		if(suitOutput != null) {
			htmlpath = suitOutput + "/html";
		} else {
			htmlpath = "test-output/html";
		}
		
		htmlResult = htmlpath + "/suite1_test1_results.html";
		htmlResultMod = htmlpath + "/suite1_test1_results_mod.html";
		screenshotsFolder = new File(StringHelper.getSystemPath() + "/html/");
		screenshots = getPictures();
		htmlReportUrl = System.getProperty("suiteBuildUrl") + "/HTML_20Report";
	}
	
	public void generateReport(List<XmlSuite> xmlSuites,
            List<ISuite> suites,
            String outputDirectoryName) {
		super.generateReport(xmlSuites, suites, outputDirectoryName);
		prepareReportData();
		BufferedReader bufferedReader;
		BufferedWriter bufferedWriter;
		try {
			bufferedReader = new BufferedReader(new FileReader(htmlResult));
			bufferedWriter = new BufferedWriter(new FileWriter(htmlResultMod)) ;

			String lineRead;
			String lineWrite="";
			while ((lineRead = bufferedReader.readLine()) != null) {	
				if (screenshots.size()>0) {
					for (String picture : screenshots) {
						String tcName = trimFileExt(picture);
						String htmlHyperLink = String.format("<a href=\"%s\">%s</a>",htmlReportUrl + "/" + picture,tcName);
						lineWrite = lineRead.replaceAll(tcName, htmlHyperLink);
						if (!lineRead.equals(lineWrite)) {							
							screenshots.remove(picture);
							break;
						} 							
					}
					bufferedWriter.write(lineWrite);							
					bufferedWriter.newLine();
				} else {
					bufferedWriter.write(lineRead);
					bufferedWriter.newLine();
				}
			}
			bufferedWriter.write("Modified version of HTMLReporter");
			bufferedReader.close();
			bufferedWriter.close();
			
			// Once everything is complete, delete old file..
		    File oldFile = new File(htmlResult);
		    oldFile.delete();

		    // And rename tmp file's name to old file name
		    File newFile = new File(htmlResultMod);
		    newFile.renameTo(oldFile);
		    
		} catch (Exception e) {
			Log.info(String.format("catch exception: %s",e.getMessage()));
		}

		
	}
	
	public ArrayList<String> getPictures() {
		ArrayList<String> pictures = new ArrayList<String>();
		File screenshotFile[] = screenshotsFolder.listFiles();
		if (screenshotFile !=null) {
			for (File file : screenshotFile) {
				if(file.getName().contains(".png")) {
					pictures.add(file.getName());
				}
			}
		}
		return pictures;
	}
	
	public String trimFileExt(String filename) {
		return filename.substring(0, filename.length()-4);
	}

}
