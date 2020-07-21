package stepDefinitions;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;

//import java.io.FileNotFoundException;
import java.io.IOException;

//import java.util.ArrayList;
//import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import io.restassured.RestAssured;
//import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
//import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
//import pojo.AddPlace;
//import pojo.Location;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefinition extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data= new TestDataBuild();
	static String place_id;
	//getJsonPath;
	//JsonPath js;

	
	@Given("Add Place Payload {string} {string} {string}")
	public void add_Place_Payload(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions	
			
		res=given().spec(requestSpecification())
		.body(data.addPlacePayload(name, language, address));
						
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
		//constructor will be called with value of resource which you pass
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resspec = new ResponseSpecBuilder()
				.expectStatusCode(200).expectContentType(ContentType.JSON).build();	
		
		if(method.equalsIgnoreCase("POST"))	
			response = res.when().post(resourceAPI.getResource())
			.then().spec(resspec).extract().response();
		else if(method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource())
			.then().spec(resspec).extract().response();
		
		
		String responseString=response.asString();
		System.out.println(responseString);
		
	}

	@Then("the API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		
		assertEquals(response.getStatusCode(), 200);
	
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
	    // Write code here that turns the phrase above into concrete actions
			
		assertEquals(getJsonPath(response,keyValue), ExpectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    // requestSpec
		place_id = getJsonPath(response, "place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}
	
	@Given("DeletePlaceAPI Payload")
	public void deleteplaceapi_Payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    
		res = given().spec(requestSpecification()).body(data.DeletePlacePayload(place_id));
	}


}
