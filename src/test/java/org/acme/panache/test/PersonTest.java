package org.acme.panache.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response.Status;

import org.acme.panache.model.Person;
import org.hamcrest.BaseMatcher;
import org.hamcrest.FeatureMatcher;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PersonTest {
	
	private final Logger LOG = LoggerFactory.getLogger(PersonTest.class);
	
	@Test
	public void testPost() {
		
		Person person = new Person();
		person.name = "ibrahim";
		person.martialStatus = Person.MartialStatus.MARRIED;
		person.birth = LocalDate.of(1986, 9, 4);
		
		String payload = JsonbBuilder.create().toJson(person);
		LOG.info("Payload is {}", payload);
		
		given()
			.contentType(ContentType.JSON)
			.urlEncodingEnabled(true)
			.when().body(payload).post("/person")
			.then().statusCode(Status.CREATED.getStatusCode());
		
		given()
			.contentType(ContentType.JSON)
			.urlEncodingEnabled(true)
			.when().body(payload).put("/person")
			.then().statusCode(Status.ACCEPTED.getStatusCode());
		
		given()
			.when().get("/person")
			.then().statusCode(Status.OK.getStatusCode())
			.body("$.size()", is(1));
		
		given()
			.when()
				.get("/person/ibrahim")
			.then()
				.statusCode(Status.OK.getStatusCode());
	}
	
	@Test
	public void testDelete() {
		
		given()
			.when().delete("/person")
			.then().statusCode(Status.OK.getStatusCode());
	}
}
