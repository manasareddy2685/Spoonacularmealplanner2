package Tests;
import Uiwebpages.LoginUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class PostRequest extends TestBase {
    public Response response;

        @Test
        public void newLogin() {
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername("Manasa");
            loginUser.setFirstName("Reddy");
            loginUser.setLastName("Thirlapuram");
            loginUser.setEmail("manasa@gmail.com");


            // Convert the LoginUser object to JSON string
            String requestObject = GsontoJSON.convertToJSON(loginUser);
            Response response = performPostRequest("https://api.spoonacular.com/users/connect", requestObject);
            System.out.println("jsonbody post request is " + requestObject);
            // Assert the response status code is 200
            Assert.assertEquals(response.statusCode(), 200);
            // Log the response body
            response.then().log().body();

        }
        public Response performPostRequest(String url, String requestBody) {
            Response response = given()
                    .queryParam("apiKey", "806069c53fe041df99739b623fde7789")
                    .contentType(ContentType.JSON)
                    .and()
                    .body(requestBody)
                    .when()
                    .post(url)
                    .then()
                    .extract().response();


            return response;
        }
    }