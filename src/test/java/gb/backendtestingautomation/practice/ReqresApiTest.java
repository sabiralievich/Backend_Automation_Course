package gb.backendtestingautomation.practice;

import gb.backendtestingautomation.practice.dto.response.ResourceListData;
import gb.backendtestingautomation.practice.dto.response.UserData;
import gb.backendtestingautomation.practice.dto.response.UserListData;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
}
