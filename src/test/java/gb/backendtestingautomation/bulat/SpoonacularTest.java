package gb.backendtestingautomation.bulat;


import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static groovy.xml.Entity.curren;
import static groovy.xml.Entity.not;
import static io.restassured.RestAssured.*;


import static org.codehaus.groovy.runtime.DefaultGroovyMethods.hasProperty;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.subsequences;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class SpoonacularTest {

    static Map<String, String> params = new HashMap<>();
    static final String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";

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
                //        .params("query", "soup")
                        .when()
                        .get(BASE_URL)
                        .then()
                        .extract()
                        .response()
                        .jsonPath()
                        .getString("offset");
        assertTrue(offset.equals("0"));

    }

    @Test
    void soupParameterTest() {
        List<String> titles = new ArrayList<>();
        titles =
                given()
                        .params("apiKey", "1609841987ad4551bde6e848a855d05f")
                        .params("query", "soup")
                        .when()
                        .get(BASE_URL)
                        .then()
                        .extract()
                        .response()
                        .jsonPath()
                        .getList("results.title");
        //Assertions.assertEquals(titles);
   }
}
