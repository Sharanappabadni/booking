package testScripts;

import config.BasicConfig;
import config.ExtentManager;
import config.ExtentTestManager;
import io.restassured.RestAssured;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseScript extends BasicConfig {
    private static File dir;

    @BeforeClass
    public void get_started(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    @BeforeMethod()
    public void beforeEachTest() {
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);

        reponseWriter = new StringWriter();
        responseCapture = new PrintStream(new WriterOutputStream(reponseWriter), true);
    }

    @AfterMethod
    public void afterMethod(ITestResult result, Method method) throws IOException {
        if (result.isSuccess()) {
            ExtentTestManager.getTest().pass(requestWriter.toString().replaceAll("\n", "<br>") + "");
            ExtentTestManager.getTest().pass(reponseWriter.toString().replaceAll("\n", "<br>") + "");

        }
        else if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().fail(requestWriter.toString().replaceAll("\n", "<br>") + "");
            ExtentTestManager.getTest().fail(reponseWriter.toString().replaceAll("\n", "<br>") + "");
            Assert.assertTrue(false);
        }
        else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().skip(method.getName() +" Test case skip");
        }
    }

    @AfterClass
    public void flush_details_into_report(){
        ExtentManager.getInstance().flush();
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

    public static String getDate_Report() {
        DateFormat dateFromat = new SimpleDateFormat("YYYY_MM_dd_HH_mm");
        Date date = new Date();
        return dateFromat.format(date);
    }
}
