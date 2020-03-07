package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;


public class ExtentTestManager {
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	
	private static ExtentReports report;
	
	public static synchronized void setReporter(ExtentReports report) {
		ExtentTestManager.report = report;
	}
	
	public static synchronized ExtentTest getTest() {
		return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}
	
	public static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }

    public static synchronized ExtentTest createTest(String testName, String desc) {
        ExtentTest test = report.createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        return test;
    }

}
