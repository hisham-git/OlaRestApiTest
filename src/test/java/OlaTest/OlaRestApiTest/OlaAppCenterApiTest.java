package OlaTest.OlaRestApiTest;

import static org.testng.AssertJUnit.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.*;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class OlaAppCenterApiTest {

	public static Response response;
	public static String jsonAsString;

	public String userAccountID;
	public String definitionCode;
	public String userInstanceID;
	public String pageID;

	@BeforeClass
	public void setUp() {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = "https://10.0.0.107:8443";
		RestAssured.basePath = "/api/submit/ola/useraccount/";
		RestAssured.authentication = basic("joeAdmin","123");
	}

	@Test(priority = 1)
	public void getUserInfo() throws JSONException, InterruptedException {

		// Initializing payload or API body
		String APIBody = "{\"Header\":{\"protocol\":\"https\",\"port\":\"443\",\"host\":\"jrm.ola.campusops.net\",\"client_id\":\"jrm\",\"UserID\":\"shuvo@yahoo.com\"},\"Params\":{}}";

		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body
		builder.setBody(APIBody);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");

		RequestSpecification requestSpec = builder.build();

		String body = 
				given().spec(requestSpec).
        		when().post("/getUserInfo").
				then().extract().body().asString();		
		
		System.out.println("**********Response from getUserInfo**********");
		System.out.println(body);
		System.out.println();

	    int userID = 		
	    		given().spec(requestSpec).
        		when().post("/getUserInfo").
        		then().assertThat().statusCode(200).and().extract().path("Data.ID");		        
	    
	    userAccountID = Integer.toString(userID);

	        /*for (String name : path) {
					System.out.println(name);	
			}*/
		
	}
	

	@Test(priority = 2)
	public void findUserInstances() throws JSONException, InterruptedException {
		if (null == userAccountID)
			throw new RuntimeException();

		// Initializing payload or API body
		String APIBody = "{\"Header\": {\"protocol\": \"https\", \"port\": \"443\",\"host\": \"jrm.ola.campusops.net\",\"client_id\": \"jrm\"},\"Params\": {\"UserAccountID\":"
				+ userAccountID + "}}";

		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body
		builder.setBody(APIBody);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");

		RequestSpecification requestSpec = builder.build();
		
		String body = 
				given().spec(requestSpec).
        		when().post("/findUserInstances").
				then().extract().body().asString();		
		
		System.out.println("**********Response from findUserInstances**********");
		System.out.println(body);
		System.out.println();

		ArrayList<String> definitionCodeList = 		
	    		given().spec(requestSpec).
        		when().post("/findUserInstances").
        		then().assertThat().statusCode(200).and().extract().path("Data.DefinitionCode");		        
	   
	    System.out.println(definitionCodeList);
	        for (String name : definitionCodeList) {
	        	if (name.equalsIgnoreCase("HarvardOLATest003"))
	        		definitionCode = name;	
			}

	}

	
	@Test(priority = 3)
	public void startInstance() throws JSONException, InterruptedException {

		if (null == definitionCode)
			throw new RuntimeException();


		// Initializing payload or API body
		String APIBody = "{\"Header\":{\"protocol\":\"https\",\"port\":\"443\",\"host\":\"jrm.ola.campusops.net\",\"client_id\":\"jrm\"},\"Params\":{\"UserAccountID\":715,\"DefinitionCode\":\""
				+ definitionCode + " \"}}";

		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body
		builder.setBody(APIBody);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");

		RequestSpecification requestSpec = builder.build();

		String body = 
				given().spec(requestSpec).
        		when().post("/startInstance").
				then().extract().body().asString();		
		
		
		System.out.println("**********Response from startInstance**********");
		System.out.println(body);
		System.out.println();

	    int instanceID = 		
	    		given().spec(requestSpec).
        		when().post("/startInstance").
        		then().assertThat().statusCode(200).and().extract().path("Data.UserInstanceID");
		
	    userInstanceID = Integer.toString(instanceID);
	    
	}

	@Test(priority = 4)
	public void findUserInstanceSummary() throws JSONException, InterruptedException {

		if (null == userInstanceID)
			throw new RuntimeException();

		// Initializing payload or API body
		String APIBody = "{\"Header\": {\"protocol\": \"https\", \"port\": \"443\",\"host\": \"jrm.ola.campusops.net\",\"client_id\": \"jrm\"},\"Params\": {\"UserInstanceID\":"
				+ userInstanceID + "}}";

		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

		// Setting API's body
		builder.setBody(APIBody);

		// Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");

		RequestSpecification requestSpec = builder.build();
		
		String body = 
				given().spec(requestSpec).
        		when().post("/findUserInstanceSummary").
				then().extract().body().asString();		
		
		System.out.println("**********Response from startInstance**********");
		System.out.println(body);
		System.out.println();

		ArrayList<String> pageIDList = 		
	    		given().spec(requestSpec).
        		when().post("/findUserInstanceSummary").
        		then().assertThat().statusCode(200).and().extract().path("Data.Sections.PageID");
	    	       
	        for (String idList : pageIDList) {
	        	if (idList.equalsIgnoreCase("PID0"))
	        		pageID = idList;	
			}

	    
		// Asserting that result of "PageID" is "PID0",
		Assert.assertEquals(pageID, "PID0");
	}

}
