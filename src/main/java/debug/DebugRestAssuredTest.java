package debug;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DebugRestAssuredTest {

    @Test
    public void directPostTest() {

        String json = """
        {
          "firstname":"Jim",
          "lastname":"Brown",
          "totalprice":111,
          "depositpaid":true,
          "bookingdates":{
            "checkin":"2018-01-01",
            "checkout":"2019-01-01"
          },
          "additionalneeds":"Breakfast"
        }
        """;

        Response response =
                given()
                        .baseUri("https://restful-booker.herokuapp.com")
                        .contentType(ContentType.JSON)
                        .body(json)
                .when()
                        .post("/booking");

        System.out.println(response.getStatusCode());
        System.out.println(response.asPrettyString());
    }
}