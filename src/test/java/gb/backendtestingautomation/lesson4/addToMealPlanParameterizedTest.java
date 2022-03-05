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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.util.stream.Stream;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class addToMealPlanParameterizedTest extends AbstractTest{
    private final String API_KEY = "1609841987ad4551bde6e848a855d05f";
    private final String BASE_URL = "https://api.spoonacular.com/mealplanner/bulat-bliznyuk/items";
    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;

    private static Stream<Arguments> getRightRequestBody() {
        return Stream.of(
                Arguments.of("addBananaRequestBody.json"),
                Arguments.of("addByTypeIngredientsRequestBody.json"),
                Arguments.of("multiplyItemsRequestBody.json"),
                Arguments.of("addByTypeRecipeRequestBody.json"),
                Arguments.of("addByCustomFoodRequestBody.json")
        );
    }



    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .log(LogDetail.ALL)
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("username", "bulat-bliznyuk")
                .addQueryParam("hash", "1bfaf36e82c2fd168d4d780fcf99f2a4bc7f5aa6")
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();
    }

    @ParameterizedTest
    @MethodSource("getRightRequestBody")
    void multiplyRequestTest(String resource) throws IOException {

        String body = getResourceAsString(resource);

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
