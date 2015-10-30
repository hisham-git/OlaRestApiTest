package OlaTest.OlaRestApiTest;

import static org.testng.AssertJUnit.assertTrue;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

//import org.junit.Test;
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
	
		
	@Test(enabled = false) public void extract_email_and_validations() {
		
        ArrayList<String> path = get("/users").then().assertThat().statusCode(200)
        		.and().body("address.street", hasItem("Kulas Light"))
        		.and().body("email", hasItems("Sincere@april.biz","Shanna@melissa.tv"))
        		.and().extract().path("email");
        
        System.out.println();
        for (String email : path) {
				Pattern p = Pattern.compile("^([A-Za-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
		        Matcher m = p.matcher(email);
		        
		        if ( m.find() ) {
		        	System.out.println(email + " => Valid email format");
		        } else {
		        	System.out.println(email + " => Invalid email format");
		        }
		}
    }
	
	@Test(enabled = false) public void extract_username_and_validations() {
        ArrayList<String> path = get("/users").then().assertThat().statusCode(200).and().extract().path("username");
       
        System.out.println();
        for (String username : path) {
				Pattern p = Pattern.compile("^[A-Za-z0-9_.-]{8,16}$");
		        Matcher m = p.matcher(username);

		        if ( m.find() ) {
		        	System.out.println(username + " => Valid username");
		        } else {
		        	System.out.println(username + " => Invalid username");
		        }
		}
    }
	
	@Test(enabled = true)
	public void getCountryInfo() throws JSONException, InterruptedException {
		
		Response resp = get("/users").then().extract().response();

		System.out.println(resp.getStatusCode());
		System.out.println("********************");
		System.out.println(resp.getHeaders());
		System.out.println("********************");
		System.out.println(resp.getCookies());
		System.out.println("********************");
		System.out.println(resp.getSessionId());
		System.out.println("********************");
		System.out.println(resp.getBody().asString());
		
		Assert.assertNotNull(resp.jsonPath().getString("address.geo.lat"), " array is null" );

	    String body = get("/users").then().extract().body().asString();
	    
	    JsonPath jsonPath = new JsonPath(body);
		System.out.println(jsonPath.getList("address.geo.lat"));
		
	//	System.out.println(body);

	}

/*	
	@Test(priority = 6)
	public void getCountryInfoWithLog() throws JSONException, InterruptedException {
		System.out.println("**********Request Logging**********");
		given().log().headers();
		
		
		System.out.println("**********Response Logging**********");
		get("/users").then().log().headers();

	}
*/
	
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
