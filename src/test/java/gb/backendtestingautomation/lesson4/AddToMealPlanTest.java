package gb.backendtestingautomation.lesson4;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AddToMealPlanTest {
    ResponseSpecification responseSpecification = null;
    private final String API_KEY = "1609841987ad4551bde6e848a855d05f";
    private final String BASE_URL = "https://api.spoonacular.com/mealplanner/bulat-bliznyuk/items";

    private String getResourceAsString(String resource) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        assert inputStream != null;
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes);
    }

    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .log(LogDetail.ALL)
                .expectBody("status", Matchers.is("success"))
                .build();
    }

    @Test
    void addBananaToMealPlanTest() throws IOException {
        String body = getResourceAsString("addBanana.json");
        given()
                .queryParam("username", "bulat-bliznyuk")
                .queryParam("hash", "1bfaf36e82c2fd168d4d780fcf99f2a4bc7f5aa6")
                .queryParam("apiKey", API_KEY)
                .log().all()
                .body(body)
                .log().body()
        .when()
                .post(BASE_URL)
        .then()
                .spec(responseSpecification);

    }
}
