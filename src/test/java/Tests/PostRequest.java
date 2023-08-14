package Tests;

import Uiwebpages.LoginUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PostRequest extends TestBase {
    public Response response;
    @Test
    public void newLoginTest() throws IOException {
        Properties properties = loadProperties("config.properties");
        String apiUrl = properties.getProperty("api.url");
        String apiKey = properties.getProperty("api.key");
        String browserPath = properties.getProperty("Browser");

        // Set the Chrome driver path
        System.setProperty("webdriver.chrome.driver", browserPath);

        // Prepare the request body
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("Manasa");
        loginUser.setFirstName("Reddy");
        loginUser.setLastName("Thirlapuram");
        loginUser.setEmail("manasa@gmail.com");

        String requestObject = GsontoJSON.convertToJSON(loginUser);

        // Send the POST request
        Response response = postRequest(apiUrl, requestObject, apiKey);

        // Print the response body
        System.out.println("json-body post request is " + requestObject);

        // Assert the response status code
        assertEquals(response.statusCode(), 200, "POST request should succeed.");

        // Extract and print the "username" and "spoonacularPassword" fields
        response.then().log().body();
    }



    public Response postRequest(String url, String requestObject, String apiKey) {
        Response response = given()
                .queryParam("apiKey", apiKey)
                .contentType(ContentType.JSON)
                .and()
                .body(requestObject)
                .when()
                .post(url)
                .then()
                .extract().response();

        return response;
    }
    private Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Could not find the properties file: " + fileName);
            }
            properties.load(inputStream);
        }
        return properties;
    }
}
