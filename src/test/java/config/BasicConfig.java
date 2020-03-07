package config;

import com.aventstack.extentreports.ExtentReports;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.PrintStream;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;

public class BasicConfig {
    public ExtentReports report;

    public static StringWriter requestWriter;
    public static PrintStream requestCapture;

    public static StringWriter reponseWriter;
    public static PrintStream responseCapture;

    public static Response post(String endpoint, Object body, int statusCode){
        Response resp = given()
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .body(body)
                .when().log().all()
                .post(endpoint);
        resp.then().log().all();
        basic_validation(resp, statusCode);
        return resp;
    }

    public static Response put(String endpoint, Object body,String token , int statusCode){
        Response resp = given()
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Cookie", "token="+token)
                .body(body)
                .when().log().all()
                .put(endpoint);
        resp.then().log().all();
        basic_validation(resp, statusCode);
        return resp;
    }

    public static Response get(String endpoint, int statusCode){
        Response resp = given()
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(endpoint);
        resp.then().log().all();
        basic_validation(resp, statusCode);
        return resp;
    }

    public static Response delete(String endpoint, String token, int statusCode){
        Response resp = given()
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .header("Cookie", "token="+token)
                .when().log().all()
                .delete(endpoint);
        resp.then().log().all();
        basic_validation(resp, statusCode);
        return resp;
    }


    public static void basic_validation(Response resp, int statusCode) {
        Assert.assertEquals(resp.statusCode(), statusCode);
    }

}
