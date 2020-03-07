package testScripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import config.ConfigPostDetails;
import config.ExtentManager;
import config.ExtentTestManager;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestScript extends BaseScript{

    String token = "";
    String id = "";

    @Test(alwaysRun = true, priority = 0)
    public void check_authentication(Method method){
        ExtentManager.createInstance(getDateFolder() + "/" + getDate_Report() + ".html");
        ExtentTestManager.setReporter(ExtentManager.getInstance());
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(getDateFolder() + "/" + getDate_Report());
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        ExtentTestManager.createTest( method.getName());
        Response resp = post("/auth", ConfigPostDetails.auth_token(), 200);
        resp.then().log().all();
        DocumentContext doc = JsonPath.parse(resp.asString());
        token = doc.read("token");
    }

    @Test(priority = 1)
    public void create_new_booking(Method method){
        ExtentTestManager.createTest( method.getName());
        Response resp = post("booking", ConfigPostDetails.booking(), 200);
        resp.then().log().all();
        DocumentContext doc = JsonPath.parse(resp.asString());
        id = doc.read("bookingid")+"";
    }

    @Test(priority = 2)
    public void get_booking_details(Method method){
        ExtentTestManager.createTest( method.getName());
        Response resp = get("booking", 200);
        resp.then().log().all();
    }

    @Test(priority = 3)
    public void put_booking_details(Method method){
        ExtentTestManager.createTest( method.getName());
        Response resp = put("booking/"+id, ConfigPostDetails.booking(), token, 200);
        resp.then().log().all();
    }

    @Test(priority = 4)
    public void delet_booking_details(Method method){
        ExtentTestManager.createTest( method.getName());
        Response resp = delete("booking/"+id, token, 201);
        resp.then().log().all();
    }

    @Test(priority = 5)
    public void check_booking_details(Method method){
        ExtentTestManager.createTest( method.getName());
        Response resp = get("booking/"+id, 404);
        resp.then().log().all();
    }
}
