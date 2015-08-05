package OlaTest.OlaRestApiTest;

import static org.testng.AssertJUnit.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.*;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;



import com.jayway.restassured.response.Response;
//import org.junit.Test;


import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.assertThat;

public class RestCountriesApiTest {

	public static Response response;
	public static String jsonAsString;

	@BeforeClass
	public static void setupURL() {
		// here we setup the default URL and API base path to use throughout the tests
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
	//	RestAssured.basePath = "/rest/v1/name";
	}
	
	@Test(priority = 2)
	public void getCountryInfo() throws JSONException, InterruptedException {

	    String body = get("/users").then().extract().body().asString();		
		
		System.out.println(body);

	}
	
	@Test(priority = 1) public void
    extract_single_path_works_after_multiple_body_validations() {
        ArrayList<String> path = get("/users").then().assertThat().statusCode(200).and().extract().path("name");
        
        for (String name : path) {
				System.out.println(name);	
		}

      //  System.out.println(path);
    }
	
	/*	
	
	@Test
	public void testGetSingleUserProgrammatic() {
	  Response res = get("/service/single-user");
	  assertEquals(200, res.getStatusCode());
	  String json = res.asString();
	  JsonPath jp = new JsonPath(json);
	  assertEquals("test@hascode.com", jp.get("email"));
	  assertEquals("Tim", jp.get("firstName"));
	  assertEquals("Testerman", jp.get("lastName"));
	  assertEquals("1", jp.get("id"));
	}
	
*/

}
