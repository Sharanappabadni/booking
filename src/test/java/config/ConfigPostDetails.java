package config;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class ConfigPostDetails {

    public static Object booking() {
        JSONObject sub = new JSONObject();
        JSONObject booking = new JSONObject();
        booking.put("firstname", "Jim");
        booking.put("lastname", "Brown");
        booking.put("totalprice", 111);
        booking.put("depositpaid", "true");
        sub.put("checkin", "2018-01-01");
        sub.put("checkout", "2019-01-01");
        booking.put("bookingdates", sub);
        booking.put("additionalneeds", "Breakfast");

        return booking;
    }

    public static Object sign_up_body_agentDetails(String scenario, String filepath){
        JSONObject signUp = new JSONObject();
        signUp.put("", "");
        signUp.put("agentMsisdn", "0807714561");
        signUp.put("agentFirstname", "Automation");
        signUp.put("agentSurname", "Test");

        return signUp;
    }

    public static Object auth_token(){
        JSONObject auth = new JSONObject();
        auth.put("username", "admin");
        auth.put("password", "password123");

        return auth;
    }
}