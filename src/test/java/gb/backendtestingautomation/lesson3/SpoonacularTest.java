package gb.backendtestingautomation.lesson3;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
 class SpoonacularTest {

    static Map<String, String> params = new HashMap<>();
    static final String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";

    private String getResourceAsString(String resource) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        assert inputStream != null;
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes);
    }

    @BeforeAll
    static void setUp() {
        params.put("Authorization", "1609841987ad4551bde6e848a855d05f");
    }

    @Test
    void getAccountInfoTest() {
        given().
                params("apiKey", "1609841987ad4551bde6e848a855d05f").
                when().
                get(BASE_URL).
                then().
                statusCode(200);
    }

    @Test
    void offsetTest() {
        String offset =
                given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .when()
                        .get(BASE_URL)
                        .then()
                        .extract()
                        .response()
                        .jsonPath()
                        .getString("offset");
        Assertions.assertEquals("0", offset);

    }

    @Test
    void soupParameterTest() throws IOException, JSONException {
        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .params("query", "soup")
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

                String expected = getResourceAsString("soupTest/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );

   }

   @Test
    void intoleranceParameterTest() throws IOException, JSONException {
        //?intolerances=seafood&number=3
       String actual =
               RestAssured.given()
                       .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                       .params("intolerances", "seafood")
                       .params("number", 3)
                       .expect()
                       .log()
                       .all()
                       .when()
                       .get(BASE_URL)
                       .body().asPrettyString();

       String expected = getResourceAsString("intoleranceTest/expected.json");
       JSONAssert.assertEquals(
               expected,
               actual,
               JSONCompareMode.NON_EXTENSIBLE
       );
   }
    @Test
    void nordicPieSearchTest() throws IOException, JSONException {
        //query=pie&cousine=nordic&sort=popularity&number=3
        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .params("query", "pie")
                        .params("cousine", "nordic")
                        .params("sort", "popularity")
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("nordicPieSearchTest/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    void excludeAllButJapaneeseTest() throws IOException, JSONException {
        String[] cousines = new String[]{"African","American","British","Cajun","Caribbean","Chinese","Eastern European","European","French","German","Greek","Indian","Irish","Italian","Jewish","Korean","Latin American","Mediterranean","Mexican","Middle Eastern","Nordic","Southern","Spanish","Thai","Vietnamese"};

        //?query=soup&excludeCuisine=excludeCusine=African,American,British,Cajun,Caribbean,Chinese,Eastern European,European,French,German,Greek,Indian,Irish,Italian,Jewish,Korean,Latin American,Mediterranean,Mexican,Middle Eastern,Nordic,Southern,Spanish,Thai,Vietnamese&number=3
        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .params("query", "soup")
                        .param("excludeCousine", cousines)
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("excludeAllButJapaneese/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    void dietPaleoTest() throws IOException, JSONException {
        ////diet=paleo

        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .param("diet", "paleo")
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("dietPaleoTest/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }


    @Test
    void excludeMilkTest() throws IOException, JSONException {
        //?excludeIngredient=milk&number=3

        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .param("excludeIngredient", "milk")
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("excludeMilk/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    void searchSaladTest() throws IOException, JSONException {
        //?type=salad&number=3

        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .param("type", "salad")
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("searchSalad/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    void threeMinutesReadyTest() throws IOException, JSONException {
        //?maxReadyTime=3&number=3

        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .param("maxReadyTime", 3)
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("maxReadyTime/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }

    @Test
    void minAlcoholTest() throws IOException, JSONException {
        //?minAlcohol=40&number=3

        String actual =
                RestAssured.given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .param("minAlcohol", 40)
                        .params("number", 3)
                        .expect()
                        .log()
                        .all()
                        .when()
                        .get(BASE_URL)
                        .body().asPrettyString();

        String expected = getResourceAsString("minAlcohol/expected.json");
        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );
    }
}
