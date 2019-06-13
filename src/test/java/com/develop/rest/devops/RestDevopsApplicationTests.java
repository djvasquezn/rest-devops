package com.develop.rest.devops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestDevopsApplicationTests {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private static String postRequest = "{\n" +
	        "  \"message\": \"This is a test\",\n" +
	        "  \"to\": \"Juan Perez\",\n" +
	        "  \"from\": \"Rita Asturia\",\n" +
	        "  \"timeToLifeSec\": 45 \n" +
	        "}";
	
//	@Test
    public void resquestPostTest() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.given().urlEncodingEnabled(true)
            .header("Content-Type", "application/json")
            .header("X-Parse-REST-API-Key", "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c")
            .body(postRequest)
            .post("/message/send")
            .then().statusCode(200).assertThat().body("message", equalTo("Hello Juan Perez your message will be send"));
    }
	
//    @Test
    public void resquestGetTest() {
        ResponseEntity<String> entity = this.restTemplate
                .getForEntity("/message/send?message=This is a test&to=Juan Perez&from=Rita Asturia&timeToLifeSec=45", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("ERROR");
    }
    
    @Test
    public void resquestGetTestAuthentication() {
        ResponseEntity<String> entity = this.restTemplate
                .getForEntity("/message/send?message=This is a test&to=Juan Perez&from=Rita Asturia&timeToLifeSec=45", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
