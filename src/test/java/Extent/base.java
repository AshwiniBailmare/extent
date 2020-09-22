package Extent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import Pojo.AddPlace;
import Pojo.Location;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import resources.ReadExcel;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class base extends Reusable {
	 private static Logger log =LogManager.getLogger(base.class.getName());
		
	
	RequestSpecification resp;
	
	String NAddress = "nagpur,Maharshtra";
	int sc;
	String placeid;
	Response  rp ;
	String update;
	
	
@Test(priority=1)
	public void Post() throws IOException
	{
		
		
		AddPlace p = new AddPlace ();
		p.setAccuracy(50);
		p.setAddress(getglobal("address"));
		p.setLanguage(getglobal("language"));
		p.setPhone_number(getglobal("phoneno"));
		p.setWebsite(getglobal("website"));
		p.setName(getglobal("name"));

		List<String> mylist = new ArrayList<String>();

		mylist.add(getglobal("sh"));
		mylist.add(getglobal("shop"));

		p.setTypes(mylist);

		Location l = new Location();
		l.setLat(-38.4885758);
		l.setLng(33.5845145);
		p.setLocation(l);
				

	 resp=  given().relaxedHTTPSValidation().spec(requestSpecification().body(p));
		rp =resp.when().post("/maps/api/place/add/json").then().log().all().assertThat().extract().response();
		
		 sc=rp.getStatusCode();
		if(sc==200) {
			System.out.println("status code" + sc);
			log.info("Status code" +sc);
		}
		else
		{
			log.error("incorrect status code: " +sc);
			System.out.println("incorrect status code" + sc);
		}
		String responseString = rp.asString();
		System.out.println(responseString);
		log.info(responseString);
		

		JsonPath js1 = new JsonPath(responseString); // ---------->parse json
		placeid= js1.getString("place_id");
		System.out.println("Place_id:" +placeid);
		log.info("Placeid:" +placeid);
	}

@Test(priority=2)
		public void update() {

		//Update place
					
	 update =resp.body("{\r\n" + 
						"\"place_id\":\""+placeid+"\",\r\n" + 
						"\"address\":\""+NAddress+"\",\r\n" + 
						"\"key\":\"qaclick123\"\r\n" + 
						"}\r\n" + 
						"")
				.when().put("/maps/api/place/update/json").then().assertThat().
				body("msg",equalTo("Address successfully updated"))
				.extract().response().asString();
	
		
		System.out.println(update);
		
		log.info(update);
			String getnewaddress =resp.queryParam("place_id", placeid)
		.when().get("/maps/api/place/get/json").then().assertThat()
		.extract().response().asString();

		
		JsonPath js1 =Reusable.Rawtjson(getnewaddress);
		String Newadd=js1.getString("address");	
		System.out.println(Newadd);
		Assert.assertEquals(Newadd, NAddress);

		log.info("New address : "+Newadd);
		
		}
	


	}
	
	

