package Extent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.ReadExcel;

public class Reusable {


	public static RequestSpecification req ; //use this instance in entire class due to static
	public RequestSpecification requestSpecification() throws IOException
	{

		if(req==null) {
			PrintStream log = new PrintStream(new FileOutputStream("log.txt"));

			req = new RequestSpecBuilder().setBaseUri(Read())					.addQueryParam("key", "qaclick123").
					addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();


			return req;


		}

		else {
			return req;
		}
	}
	public  static String getglobal(String key) throws IOException
	{
		Properties prop = new Properties();

		FileInputStream fis = new FileInputStream("C:\\Users\\Ashwini\\eclipse-workspace\\RestReportExtent\\src\\test\\java\\Files\\global.properties");

		prop.load(fis);
		return prop.getProperty(key);

	}

	public static JsonPath Rawtjson(String response) {

		JsonPath js1 = new JsonPath(response);
		return js1;
	}


	public String Read() throws IOException 
	{			
		ReadExcel obj = new ReadExcel();
		
		return obj.read(0, 0, 0);
	

		
	}

}
