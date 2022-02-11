package gb.backendtestingautomation.lesson4;

import gb.backendtestingautomation.lesson4.dto.MealPlanResponseData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToMealPlanTest extends AbstractTest {
    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;
    private final String API_KEY = "1609841987ad4551bde6e848a855d05f";
    private final String BASE_URL = "https://api.spoonacular.com/mealplanner/bulat-bliznyuk/items";


    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .log(LogDetail.ALL)
//              .expectBody("status", Matchers.is("success")) // Это проверка без сериализации JSON
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("username", "bulat-bliznyuk")
                .addQueryParam("hash", "1bfaf36e82c2fd168d4d780fcf99f2a4bc7f5aa6")
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    void addBananaToMealPlanTest() throws IOException {
        String body = getResourceAsString("addBananaRequestBody.json");
        MealPlanResponseData responseData =
                given()
/*    Этот блок использовался, пока не сделал requestSpecification

                        .queryParam("username", "bulat-bliznyuk")
                        .queryParam("hash", "1bfaf36e82c2fd168d4d780fcf99f2a4bc7f5aa6")
                        .queryParam("apiKey", API_KEY)
                        .log().all()
*/
                        .spec(requestSpecification)
                        .body(body)
                        .log().body()
                        .when()
                        .post(BASE_URL)
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .body()
                        .as(MealPlanResponseData.class);

        assertThat(responseData.getStatus(), is("success"));
    }
    @Test
    void addByTypeIngredients() throws IOException {
        String body = getResourceAsString("addByTypeIngredientsRequestBody.json");
        MealPlanResponseData responseData =
                given()
                        .spec(requestSpecification)
                        .body(body)
                        .log().body()
                        .when()
                        .post(BASE_URL)
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .body()
                        .as(MealPlanResponseData.class);

        assertThat(responseData.getStatus(), is("success"));
    }

    @Test
    void addByMultiplyItems() throws IOException {
        String body = getResourceAsString("multiplyItemsRequestBody.json");
        MealPlanResponseData responseData =
                given()
                        .spec(requestSpecification)
                        .body(body)
                        .log().body()
                        .when()
                        .post(BASE_URL)
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .body()
                        .as(MealPlanResponseData.class);

        assertThat(responseData.getStatus(), is("success"));
    }
}

