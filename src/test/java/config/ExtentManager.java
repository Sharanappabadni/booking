package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
	static ExtentReports report;
	private static File dir;

	public static synchronized ExtentReports getInstance() {
		return report;
	}

	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		report = new ExtentReports();
		report.attachReporter(htmlReporter);

		return report;
	}

	public static String getDate() {
		DateFormat dateFromat = new SimpleDateFormat("YYYY_MM_dd_HH_mm");
		Date date = new Date();
		return dateFromat.format(date);
	}

	public static File getDateFolder() {
		DateFormat dateFromat = new SimpleDateFormat("YYYY_MM_dd");
		Date date = new Date();
		String dateString= dateFromat.format(date);
		dir = new File("./report/"+dateString);
		if(!dir.exists())
			dir.mkdir();
		return dir;
	}
	
	public String create_date(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
		simpleDateFormat = new SimpleDateFormat("YYYY");
		int year =Integer.parseInt(simpleDateFormat.format(date))+2;
		simpleDateFormat = new SimpleDateFormat("MM");
		String month = simpleDateFormat.format(date);
	    simpleDateFormat = new SimpleDateFormat("dd");
	    String day = simpleDateFormat.format(date);
		return year+"-"+month+"-"+day;
	}


}
