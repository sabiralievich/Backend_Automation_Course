package gb.backendtestingautomation.practice;

import gb.backendtestingautomation.practice.dto.response.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class ReqresApiTest {
    ResponseSpecification responseSpecification = null;
    final String BASE_URL = "https://reqres.in/";

    public String getResourceAsString(String resource) throws IOException {
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
                .expectContentType("application/json")
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();
    }

    @Test
    void singleUserResponseTest() {
        String request = "api/users/2";
        when()
                .get(BASE_URL + request)
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    void singleUserValidationTest() throws IOException {
        int userId = 2;
        String support = getResourceAsString("support.json");

        String request = "api/users/";
        UserData response =
                given()
                        .when()
                        .get(BASE_URL + request + userId)
                        .then()
                        .extract()
                        .body()
                        .as(UserData.class);
        assertThat(response.getData().getId(), equalTo(2));
        assertThat(response.getData().getFirstName(), equalTo("Janet"));
        assertThat(response.getData().getLastName(), equalTo("Weaver"));
        assertThat(response.getSupport().getUrl(), equalTo("https://reqres.in/#support-heading"));
        assertThat(response.getSupport().getText(), equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }

    @Test
    void getListOfUsersTest() {
        String request = "api/users?page=";
        int page = 2;
        UserListData response =
                given()
                        .when()
                        .get(BASE_URL + request + page)
                        .then()
                        .extract()
                        .body()
                        .as(UserListData.class);

        assertThat(response.getData()[0].getId(), equalTo(7));
        assertThat(response.getData()[0].getFirstName(), equalTo("Michael"));
        assertThat(response.getData()[0].getLastName(), equalTo("Lawson"));
        assertThat(response.getData().length, equalTo(response.getPerPage()));
        assertThat(response.getPage(), equalTo(page));
        assertThat(response.getSupport().getText(), equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void getListOfResourcesTest() {
        String request = "api/unknown";
        ResourceListData response =
                given()
                        .when()
                        .get(BASE_URL + request)
                        .then()
                        .extract()
                        .body()
                        .as(ResourceListData.class);

        assertThat(response.getData().length, equalTo(response.getPerPage()));
        assertThat(response.getData()[0].getPantoneValue(), equalTo("15-4020"));
        assertThat(response.getSupport().getText(), equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
        assertThat(response.getPerPage(), equalTo(6));
    }

    @Test
    void createUser() {
        String request = "api/users";
        String name = "Morpheus";
        String job = "Leader";
        UserDataCreationRequest request1 = new UserDataCreationRequest(name, job);
        System.out.println(request1.getName() + " " + request1.getJob());
        String body = "{\"name\": \"Morpheus\", \"job\": \"Leader\"}";

        String response =
                given()
                        .with()
                        .body(body)
                        .when()
                        .request("POST", BASE_URL + request)
                        .then()
                        .statusCode(201)
                        .extract().body().asPrettyString();

        System.out.println(response);
/*
        assertThat(response.getName(), equalTo(name));
        assertThat(response.getJob(), equalTo(job));
        assertThat(response.getId(), notNullValue());
        assertThat(response.getCreatedAt(), notNullValue());
*/

    }

    @Test
    void updateUser() {
        String request = "api/users/2";
        String name = "Morpheus";
        String job = "zion rezident";
        String response =
                given()
                        .with()
                        .body(new UserDataCreationRequest(name, job))
                        .when()
                        .request("PUT", BASE_URL + request)
                        .then()
                        .statusCode(200)
                        .extract().body().asPrettyString();

        System.out.println(response);
    }
}
